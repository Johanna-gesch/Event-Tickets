package dk.easv.eventtickets.GUI.Models;

import dk.easv.eventtickets.BE.Event;
import dk.easv.eventtickets.BE.User;
import dk.easv.eventtickets.BLL.EventManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.util.List;

public class EventModel {
    private EventManager eMan;
    private ObservableList<Event> eventsToBeViewed;
    private ObservableList<User> allCoordinators;
    private static EventModel instance;

    public EventModel() throws Exception {
        eMan = new EventManager();
        eventsToBeViewed = FXCollections.observableArrayList();
        allCoordinators = FXCollections.observableArrayList();

        allCoordinators.addAll(eMan.getAllCoordinators());

        eventsToBeViewed.addAll(eMan.getAllEvents());
    }

    public void reloadEvents() throws Exception {
        eventsToBeViewed.setAll(eMan.getAllEvents());
    }

    public ObservableList<User> getAllCoordinators() {
        return allCoordinators;
    }

    public ObservableList<Event> getEventsToBeViewed() {
        return eventsToBeViewed;
    }

    public void deleteEvent(Event deleteEvent) throws Exception {
        eMan.deleteEvent(deleteEvent);

    }

    public List<User> getCoordinatorsForEvent(int id) throws Exception {
        return eMan.getCoordinatorsForEvent(id);
    }

    public void updateEventCoordinators(int id, int userId) throws Exception {
        eMan.updateEventCoordinators(id, userId);
    }

    public Event createEvent(Event newEvent) throws Exception {
        Event eventCreated = eMan.createEvent(newEvent);
        List<User> coordinators = eMan.getCoordinatorsForEvent(eventCreated.getId());
        eventCreated.setCoordinators(coordinators);

        eventsToBeViewed.add(eventCreated);
        return eventCreated;
    }

    public void updateEvent(Event event) throws Exception {
        eMan.updateEvent(event);
    }


}
