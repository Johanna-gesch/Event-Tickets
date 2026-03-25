package dk.easv.eventtickets.DAL.Events;

import dk.easv.eventtickets.BE.Event;

import dk.easv.eventtickets.DAL.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;


public class EventDAO_DB implements IEventDataAccess {
    private final DBConnector databaseConnector;

    public EventDAO_DB(DBConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    @Override
    public List<Event> getAllEvents() throws Exception {
        return List.of();
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

    public void removeCoordinator(Event event) throws Exception {
        String sql = "UPDATE Events SET CreatedByUserId = NULL WHERE EventId = ?;";

        try (Connection conn = databaseConnector.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, event.getEventId());
                stmt.executeUpdate();

                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        }
    }
}
