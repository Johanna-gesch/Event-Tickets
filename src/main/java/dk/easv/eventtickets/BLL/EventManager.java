package dk.easv.eventtickets.BLL;

import dk.easv.eventtickets.BE.Event;
import dk.easv.eventtickets.BE.User;
import dk.easv.eventtickets.DAL.Events.EventDAO_DB;
import dk.easv.eventtickets.DAL.Events.IEventDataAccess;



import dk.easv.eventtickets.DAL.DBConnector;

import java.util.List;

public class EventManager {

    private final IEventDataAccess eventDAO;

    public EventManager() throws Exception {
        eventDAO = new EventDAO_DB();
    }

    public List<Event> getAllEvents() throws Exception{
        return eventDAO.getAllEvents();
    }

    public void removeCoordinator(Event event, int UserId) throws Exception {
        eventDAO.removeCoordinator(event, UserId);
    }
}