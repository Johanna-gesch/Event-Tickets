package dk.easv.eventtickets.DAL.Events;

import dk.easv.eventtickets.BE.Event;
import dk.easv.eventtickets.BE.User;

import java.util.List;

public interface IEventDataAccess {
    List<Event> getAllEvents() throws Exception;

    Event createUser(Event event) throws Exception;

    void updateEvent(Event event) throws Exception;

    void deleteEvent(Event event) throws Exception;

    List<Event> getUsersForEvent(Event event) throws Exception;

    void removeCoordinator(Event event, int userId) throws Exception;

    List<User> getCoordinatorsForEvent(int eventId) throws Exception;

    void updateCoordinators(int eventId, int userId) throws Exception;

}
