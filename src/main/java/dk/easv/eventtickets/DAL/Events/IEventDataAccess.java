package dk.easv.eventtickets.DAL.Events;

import dk.easv.eventtickets.BE.Event;

import java.util.List;

public interface IEventDataAccess {
    List<Event> getAllEvents() throws Exception;

    Event createUser(Event event) throws Exception;

    void updateEvent(Event event) throws Exception;

    void deleteEvent(Event event) throws Exception;

    List<Event> getMoviesForEvent(Event event) throws Exception;

    void removeCoordinator(Event event) throws Exception;
}
