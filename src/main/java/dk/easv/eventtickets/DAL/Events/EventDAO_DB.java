package dk.easv.eventtickets.DAL.Events;

import dk.easv.eventtickets.BE.Event;
import dk.easv.eventtickets.BE.User;
import dk.easv.eventtickets.BE.UserRole;
import dk.easv.eventtickets.DAL.DBConnector;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventDAO_DB implements IEventDataAccess {
    private final DBConnector databaseConnector;

    public EventDAO_DB() throws IOException {
        databaseConnector = new DBConnector();
    }

    @Override
    public List<Event> getAllEvents() throws Exception {
        List<Event> allEvents = new ArrayList<>();

        // Left join so events without coordinators also show up
        String sql = """
        
                SELECT e.EventId, e.Title, e.StartDateTime, e.EndDateTime, e.Location, e.Notes,
               u.UserID, u.FName, u.LName
        FROM dbo.Events e
        LEFT JOIN dbo.EventCoordinators ec ON e.EventId = ec.EventId
        LEFT JOIN dbo.Users u ON ec.UserId = u.UserID
        ORDER BY e.EventId
        """;

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            Event currentEvent = null;
            int previousEventId = -1;

            while (rs.next()) {
                int eventId = rs.getInt("EventId");


                if (eventId != previousEventId) {
                    currentEvent = new Event();
                    currentEvent.setId(eventId);
                    currentEvent.setName(rs.getString("Title"));
                    currentEvent.setStartDateTime(rs.getObject("StartDateTime", LocalDateTime.class));
                    currentEvent.setEndDateTime(rs.getObject("EndDateTime", LocalDateTime.class));
                    currentEvent.setLocation(rs.getString("Location"));
                    currentEvent.setNotes(rs.getString("Notes"));
                    currentEvent.setCoordinators(new ArrayList<>());

                    allEvents.add(currentEvent);

                    previousEventId = eventId;
                }

                // Make list of ecs

                // Ved LEFT JOIN kan UserId være NULL hvis eventet ikke har en coordinator.
                // getInt() kan ikke returnere null og giver derfor 0 i stedet - hvilket kan give en coordinator (hvis man har startet ids fra 0 af),
                // så vi bruger wasNull() til at afgøre om værdien faktisk var NULL i joinet. (det er en sikkerhedsforanstaltning for at gøre koden mere robost

                int userId = rs.getInt("UserID");
                if (!rs.wasNull()) {
                    User ec = new User();
                    ec.setId(userId);
                    ec.setFName(rs.getString("FName"));
                    ec.setLName(rs.getString("LName"));

                    currentEvent.getCoordinators().add(ec);
                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Could not get all events", e);
        }

        return allEvents;
    }

    @Override
    public Event createEvent(Event newEvent) throws Exception {
        String sql = """
                INSERT INTO dbo.Events
                (Title, StartDateTime, EndDateTime, Location, Notes)
                VALUES (?, ?, ?, ?, ?)
                """;

        String sqlCoordinator = """
                INSERT INTO dbo.EventCoordinators (EventID, UserID)
                VALUES (?, ?)
                """;


        try (Connection conn = databaseConnector.getConnection()) {
            conn.setAutoCommit(false);

            int eventId;

            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, newEvent.getName());
                stmt.setTimestamp(2, Timestamp.valueOf (newEvent.getStartDateTime()));
                stmt.setTimestamp(3, Timestamp.valueOf (newEvent.getEndDateTime()));
                stmt.setString(4, newEvent.getLocation());
                stmt.setString(5, newEvent.getNotes());


                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        eventId = rs.getInt(1);
                    } else {
                        throw new SQLException("Intet evntId genereret");
                    }
                }
            }
            for (User coordinator : newEvent.getCoordinators()) {
                try (PreparedStatement stmt2 = conn.prepareStatement(sqlCoordinator)) {
                    stmt2.setInt(1, eventId);
                    stmt2.setInt(2, coordinator.getId());
                    stmt2.executeUpdate();
                }
            }

            conn.commit();

            Event created = new Event();
            created.setId(eventId);
            created.setName(newEvent.getName());
            created.setStartDateTime(newEvent.getStartDateTime());
            created.setEndDateTime(newEvent.getEndDateTime());
            created.setLocation(newEvent.getLocation());
            created.setNotes(newEvent.getNotes());


            return created;
        } catch (Exception e) {
            throw new Exception("Can't create Event", e);
        }
    }


    @Override
    public void updateEvent(Event event) throws Exception {
        String sql = """ 
                Update dbo.Events
                SET Title = ?, StartDateTime = ?, EndDateTime = ?, Location = ?, Notes = ? 
                Where EventID =?""";

        String deleteCoordinators = """
                DELETE FROM dbo.EventCoordinators WHERE EventID = ?""";

        String insertCordinator = """
                INSERT INTO dbo.EventCoordinators (EventID, UserID) VALUES (?,?)""";

        try (Connection conn = databaseConnector.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1,event.getName());
                stmt.setTimestamp(2,Timestamp.valueOf(event.getStartDateTime()));
                stmt.setTimestamp(3,Timestamp.valueOf(event.getEndDateTime()));
                stmt.setString(4,event.getLocation());
                stmt.setString(5, event.getNotes());
                stmt.setInt(6,event.getId());

                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(deleteCoordinators)) {
                stmt.setInt(1, event.getId());
                stmt.executeUpdate();
            }

            for (User coordinator : event.getCoordinators()){
                try(PreparedStatement stmt = conn.prepareStatement(insertCordinator)) {
                    stmt.setInt(1, event.getId());
                    stmt.setInt(2, coordinator.getId());
                    stmt.executeUpdate();
                }
            }

            conn.commit();

            } catch (Exception e) {
            throw new Exception("Can't update event", e);
        }




      

    }

    @Override
    public void deleteEvent(Event event) throws Exception {
        String deleteCoordinators = "DELETE FROM EventCoordinators WHERE EventID = ?";
        String sql = "DELETE FROM Events WHERE EventID = ?;";

        try (Connection conn = databaseConnector.getConnection()){
            conn.setAutoCommit(false);

            try (PreparedStatement stmt1 = conn.prepareStatement(deleteCoordinators)) {
                stmt1.setInt(1, event.getId());
                stmt1.executeUpdate();
            }

            try (PreparedStatement stmt2 = conn.prepareStatement(sql)){
                stmt2.setInt(1, event.getId());
                int rowsAffected = stmt2.executeUpdate();

                if (rowsAffected == 0) {
                    conn.rollback();
                    throw new Exception("Could not delete event: no event found with ID " + event.getId());
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new Exception("Could not delete event: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public List<User> getUsersForEvent(int eventId) throws Exception {
        List<User> coordinators = new ArrayList<>();

        String sql = """
        SELECT u.UserID, u.Username, u.FName, u.LName, u.Email, u.Role
        FROM Users u
        JOIN EventCoordinators ec ON u.UserID = ec.UserID
        WHERE ec.EventID = ?
        """;

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, eventId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User u = new User();
                    u.setId(rs.getInt("UserID"));
                    u.setUsername(rs.getString("Username"));
                    u.setFName(rs.getString("FName"));
                    u.setLName(rs.getString("LName"));
                    u.setEmail(rs.getString("Email"));
                    u.setRole(UserRole.valueOf(rs.getString("Role")));

                    coordinators.add(u);
                }
            }
        }

        return coordinators;
    }

    @Override
    public void removeCoordinator(Event event, int userId) throws Exception {
        String sql = "DELETE FROM EventCoordinators WHERE EventId = ? AND UserId = ?;";

        try (Connection conn = databaseConnector.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, event.getId());
                stmt.setInt(2, userId);

                stmt.executeUpdate();

                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        }
    }



    @Override
    public List<User> getCoordinatorsForEvent(int eventId) throws Exception {
        List<User> coordinatorsForEvent = new ArrayList<>();

        String sql = """
        SELECT u.UserID, u.FName, u.LName, u.Email
        FROM EventCoordinators ec
        JOIN Users u ON ec.UserID = u.UserID
        WHERE ec.EventID = ?
    """;

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, eventId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("UserID"));
                u.setFName(rs.getString("FName"));
                u.setLName(rs.getString("LName"));
                u.setEmail(rs.getString("Email"));
                u.setRole(UserRole.EVENT_COORDINATOR);
                coordinatorsForEvent.add(u);
            }
        }
        return coordinatorsForEvent;

    }

    @Override
    public void updateCoordinators(int eventId, int userId) throws Exception {
        String insertSql = "INSERT INTO EventCoordinators (EventID, UserID) VALUES (?, ?)";

        try (Connection conn = databaseConnector.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setInt(1, eventId);
                insertStmt.setInt(2, userId);
                insertStmt.addBatch();

                insertStmt.executeBatch();
            }
            conn.commit();
        }
    }
}
