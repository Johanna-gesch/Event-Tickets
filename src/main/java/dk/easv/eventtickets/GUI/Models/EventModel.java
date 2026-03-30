package dk.easv.eventtickets.GUI.Models;

import dk.easv.eventtickets.BE.Event;
import dk.easv.eventtickets.BE.User;
import dk.easv.eventtickets.BLL.EventManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class EventModel {
    private EventManager eMan;
    private ObservableList<Event> eventsToBeViewed;
    private static EventModel instance;

    public EventModel() throws Exception {
        eMan = new EventManager();
        eventsToBeViewed = FXCollections.observableArrayList();
        eventsToBeViewed.addAll(eMan.getAllEvents());
    }

    public static EventModel getInstance() throws Exception {
        if (instance == null) {
            instance = new EventModel();
        }
        return instance;
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
}
