package dk.easv.eventtickets.GUI.Controllers.EventCoordinators;

import dk.easv.eventtickets.BE.Event;
import dk.easv.eventtickets.BE.User;
import dk.easv.eventtickets.BLL.EventManager;
import dk.easv.eventtickets.GUI.Models.EventModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.util.List;

public class ECEdittEvent {
    @FXML
    private ListView EventCoordinatorEvent;

    private Event event;
    private User user;
    private EventManager eventManager;

    public ECEdittEvent() throws Exception {
        eventManager = new EventManager();
    }

    @FXML
    public void initialize() {
        EventCoordinatorEvent.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }


    private void loadCoordinators() {
        try{
            List<User> all = eventManager.getAllCoordinators();
            List<User> assigned = eventManager.getCoordinatorsForEvent(event.getId());

            EventCoordinatorEvent.getItems().setAll(all);
            EventCoordinatorEvent.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            for (User u : assigned) {
                EventCoordinatorEvent.getSelectionModel().select(u);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void setUser(User user) {
        this.user = user;
    }

    public void setEvent(Event event) {
        this.event = event;
        loadCoordinators();
    }

    @FXML
    private void onAdd(ActionEvent actionEvent) {
        try {
            List<User> oldList = eventManager.getCoordinatorsForEvent(event.getId());
            List<User> newList = EventCoordinatorEvent.getSelectionModel().getSelectedItems();


            // Find coordinators to add
            for (User u : newList) {
                if (!oldList.contains(u)) {
                    eventManager.updateEventCoordinators(event.getId(), u.getId());
                }
            }

            EventModel.getInstance().reloadEvents();

            EventCoordinatorEvent.getScene().getWindow().hide();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
