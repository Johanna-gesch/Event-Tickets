package dk.easv.eventtickets.DAL.Events;

import dk.easv.eventtickets.BE.Event;
import dk.easv.eventtickets.BE.User;
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

        String sql = """
        SELECT e.EventId, e.Title, e.StartDateTime, e.EndDateTime, e.Location, e.Notes
        FROM dbo.Events e
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
                    currentEvent.setEventId(eventId);
                    currentEvent.setName(rs.getString("Title"));
                    currentEvent.setStartDateTime(rs.getObject("StartDateTime", LocalDateTime.class));
                    currentEvent.setEndDateTime(rs.getObject("EndDateTime", LocalDateTime.class));
                    currentEvent.setLocation(rs.getString("Location"));
                    currentEvent.setNotes(rs.getString("Notes"));

                    allEvents.add(currentEvent);
                    previousEventId = eventId;
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

    public void removeCoordinator(Event event, int userId) throws Exception {
        String sql = "DELETE FROM EventCoordinators WHERE EventId = ? AND UserId = ?;";

        try (Connection conn = databaseConnector.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, event.getEventId());
                stmt.setInt(2, userId);

                stmt.executeUpdate();

                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
}}}
