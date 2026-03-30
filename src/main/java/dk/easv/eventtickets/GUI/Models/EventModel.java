package dk.easv.eventtickets.GUI.Models;

import dk.easv.eventtickets.BE.Event;
import dk.easv.eventtickets.BE.User;
import dk.easv.eventtickets.BLL.EventManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EventModel {
    private EventManager eMan;
    private ObservableList<Event> eventsToBeViewed;

    public EventModel() throws Exception {
        eMan = new EventManager();
        eventsToBeViewed = FXCollections.observableArrayList();
        eventsToBeViewed.addAll(eMan.getAllEvents());
    }

    public ObservableList<Event> getEventsToBeViewed() {
        return eventsToBeViewed;
    }
    public void deleteEvent(Event deleteEvent) throws Exception {
        eMan.deleteEvent(deleteEvent);

    }
}
