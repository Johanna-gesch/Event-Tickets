package dk.easv.eventtickets.GUI.Models;

import dk.easv.eventtickets.BE.Event;
import dk.easv.eventtickets.BE.User;
import dk.easv.eventtickets.BLL.EventManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventModel {
    private EventManager eMan;
    private ObservableList<Event> eventsToBeViewed;
    private static EventModel instance;

    public EventModel() throws Exception {
        eMan = new EventManager();
        eventsToBeViewed = FXCollections.observableArrayList();
        eventsToBeViewed.addAll(eMan.getAllEvents());
    }

    public void reloadEvents() throws Exception {
        eventsToBeViewed.setAll(eMan.getAllEvents());
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
        eventsToBeViewed.add(newEvent);
        return eventCreated;
    }
}
