package dk.easv.eventtickets.DAL.Events;

import dk.easv.eventtickets.BE.Event;
import dk.easv.eventtickets.BE.User;
import dk.easv.eventtickets.BE.UserRole;
import dk.easv.eventtickets.DAL.DBConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public Event createUser(Event event) throws Exception {
        return null;
    }

    @Override
    public void updateEvent(Event event) throws Exception {

    }

    @Override
    public void deleteEvent(Event event) throws Exception {

    }

    @Override
    public List<Event> getUsersForEvent(Event event) throws Exception {
        return List.of();
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
}
