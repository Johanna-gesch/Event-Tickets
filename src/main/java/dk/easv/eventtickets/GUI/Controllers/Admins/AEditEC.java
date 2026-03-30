package dk.easv.eventtickets.GUI.Controllers.Admins;

import dk.easv.eventtickets.BE.Event;
import dk.easv.eventtickets.BE.User;
import dk.easv.eventtickets.BLL.EventManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.util.List;

public class AEditEC {
    @FXML
    private ListView EventCoordinatorEvent;

    private Event event;
    private EventManager eventManager;

    public AEditEC() throws Exception {
        eventManager = new EventManager();
    }

    public void setEvent(Event event) {
        this.event = event;
        loadCoordinators();
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

    @FXML
    private void onCancel(ActionEvent actionEvent) {
    }

    @FXML
    private void onSave(ActionEvent actionEvent) {
        try {
            List<User> selected = EventCoordinatorEvent.getSelectionModel().getSelectedItems();
            eventManager.updateEventCoordinators(event.getId(), selected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
