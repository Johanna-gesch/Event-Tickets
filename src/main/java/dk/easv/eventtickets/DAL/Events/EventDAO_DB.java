package dk.easv.eventtickets.DAL.Events;

import dk.easv.eventtickets.BE.Event;

import java.util.List;

public class EventDAO_DB implements IEventDataAccess {
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
    public List<Event> getMoviesForEvent(Event event) throws Exception {
        return List.of();
    }
}
