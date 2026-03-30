package dk.easv.eventtickets.GUI.Controllers.Admins;

import dk.easv.eventtickets.BE.Event;
import dk.easv.eventtickets.BE.User;
import dk.easv.eventtickets.BLL.EventManager;
import dk.easv.eventtickets.GUI.Models.EventModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.util.List;

public class AEditEC {
    @FXML
    private ListView<User> EventCoordinatorEvent;

    @FXML
    private Button BtnRemove;

    private Event event;
    private User user;
    private EventManager eventManager;



    public AEditEC() throws Exception {
        eventManager = new EventManager();
    }

    @FXML
    public void initialize() {
        EventCoordinatorEvent.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void setEvent(Event event) {
        this.event = event;
        loadCoordinators();
    }

    public void setUser(User user) {
        this.user = user;
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

//    @FXML
//    private void btnRemove(ActionEvent actionEvent) throws Exception {
//        eventManager.removeCoordinator(event.getId(), user.getId());
//    }

    @FXML
    private void btnRemoveAC(ActionEvent actionEvent) throws Exception {

        // Get selected coordinator from your ListView
        User selected = (User) EventCoordinatorEvent.getSelectionModel().getSelectedItem();

        if (selected == null) {
            System.out.println("No coordinator selected");
            return;
        }

        // Call your BE method using its parameters: (Event event, int userId)
        eventManager.removeCoordinator(event, selected.getId());

        EventModel.getInstance().reloadEvents();
    }

}

