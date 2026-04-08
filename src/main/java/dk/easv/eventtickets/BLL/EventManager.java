package dk.easv.eventtickets.BLL;

import dk.easv.eventtickets.BE.Event;
import dk.easv.eventtickets.BE.User;
import dk.easv.eventtickets.DAL.Events.EventDAO_DB;
import dk.easv.eventtickets.DAL.Events.IEventDataAccess;

import dk.easv.eventtickets.DAL.DBConnector;
import dk.easv.eventtickets.DAL.Users.IUserDataAccess;
import dk.easv.eventtickets.DAL.Users.UserDAO_DB;

import java.util.List;

public class EventManager {

    private final IUserDataAccess userDAO = new UserDAO_DB();

    private final IEventDataAccess eventDAO;

    public EventManager() throws Exception {
        eventDAO = new EventDAO_DB();
    }

    public List<Event> getAllEvents() throws Exception {
        return eventDAO.getAllEvents();
    }

    public void removeCoordinator(Event event, int UserId) throws Exception {
        eventDAO.removeCoordinator(event, UserId);
    }

    public void deleteEvent (Event event) throws Exception {
        eventDAO.deleteEvent(event);
    }

    public List<User> getAllCoordinators() throws Exception {
        return userDAO.getAllCoordinators();
    }

    public List<User> getCoordinatorsForEvent(int eventId) throws Exception {
        return eventDAO.getCoordinatorsForEvent(eventId);
    }

    public void updateEventCoordinators(int eventId, int userId) throws Exception {
        eventDAO.updateCoordinators(eventId, userId);
    }

    public Event createEvent (Event event) throws Exception {
        return eventDAO.createEvent(event);
    }

    public void updateEvent (Event event) throws Exception {
        eventDAO.updateEvent(event);
    }
}