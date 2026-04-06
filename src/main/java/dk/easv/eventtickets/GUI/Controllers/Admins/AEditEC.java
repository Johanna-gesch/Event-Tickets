package dk.easv.eventtickets.GUI.Controllers.Admins;

import dk.easv.eventtickets.BE.Event;
import dk.easv.eventtickets.BE.User;
import dk.easv.eventtickets.BLL.EventManager;
import dk.easv.eventtickets.GUI.Controllers.EventCoordinators.ETicketController;
import dk.easv.eventtickets.GUI.Models.EventModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AEditEC implements Initializable {
    @FXML
    private ListView<User> EventCoordinatorEvent;

    @FXML
    private Button BtnRemove;

    private Event event;
    private User user;
    private EventManager eventManager;
    private EventModel eventModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventCoordinatorEvent.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void setModel(EventModel eventModel) {this.eventModel = eventModel;}

    public AEditEC() throws Exception {
        eventManager = new EventManager();
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
        EventCoordinatorEvent.getScene().getWindow().hide();
    }

    @FXML
    private void onSave(ActionEvent actionEvent) {
        try {
            List<User> oldList = eventModel.getCoordinatorsForEvent(event.getId());
            List<User> newList = EventCoordinatorEvent.getSelectionModel().getSelectedItems();


            // Find coordinators to add
            for (User u : newList) {
                if (!oldList.contains(u)) {
                    eventModel.updateEventCoordinators(event.getId(), u.getId());
                }
            }

            eventModel.reloadEvents();

            EventCoordinatorEvent.getScene().getWindow().hide();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnRemoveAC(ActionEvent actionEvent) throws Exception {

        // Get selected coordinator from your ListView
        User selected = EventCoordinatorEvent.getSelectionModel().getSelectedItem();

        if (selected == null) {
            displayError(new Exception("Please select a coordinator to remove."));
            return;
        }

        // Call your BE method using its parameters: (Event event, int userId)
        eventManager.removeCoordinator(event, selected.getId());

        eventModel.reloadEvents();
    }

    private void displayError(Throwable t) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something is wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

}

