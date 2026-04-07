package dk.easv.eventtickets.GUI.Controllers.EventCoordinators;


import dk.easv.eventtickets.BE.Event;
import dk.easv.eventtickets.BE.User;
import dk.easv.eventtickets.GUI.Controllers.SideBarController;
import dk.easv.eventtickets.GUI.Models.EventModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class ECreateController implements Initializable {
    @FXML
    private ListView <User> lstSelectedCoordinator;
    @FXML
    private ObservableList<User> selectedCoordinators = FXCollections.observableArrayList();
    private EventModel eventModel;
    @FXML
    private TextField txtNotes;
    @FXML
    private TextField txtLocation;
    @FXML
    private TextField txtEndTime;
    @FXML
    private TextField txtEndDate;
    @FXML
    private TextField txtStartTime;
    @FXML
    private TextField txtStartDate;
    @FXML
    private TextField txtName;
    @FXML
    private ComboBox <User> comboExtraCoordinators;

    private SideBarController sideBarController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lstSelectedCoordinator.setItems(selectedCoordinators);

        comboExtraCoordinators.setCellFactory(param -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                setText(empty || user == null ? "" : user.getFName()); // eller user.getFullName()
            }
        });


        comboExtraCoordinators.setButtonCell(new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                setText(empty || user == null ? "" : user.getFName());
            }
        });
    }
    public void setup(){
        System.out.println("Hello");
    }

    public void setModel(EventModel eventModel) {
        this.eventModel = eventModel;
        comboExtraCoordinators.setItems(eventModel.getAllCoordinators());
    }

    public void setSideBarController(SideBarController sideBarController) {
        this.sideBarController = sideBarController;
    }


    @FXML
    private void onBtnSave(ActionEvent actionEvent) {
        String name = txtName.getText();
        String location = txtLocation.getText();
        String notes = txtNotes.getText();

        try {
            LocalDate startDate = LocalDate.parse(txtStartDate.getText());
            LocalDate endDate = LocalDate.parse(txtEndDate.getText());

            LocalTime startTime = LocalTime.parse(txtStartTime.getText());
            LocalTime endTime = LocalTime.parse(txtEndTime.getText());

            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

            Event event = new Event(name, startDateTime, endDateTime, location, notes);

            event.setCoordinators(selectedCoordinators);

            eventModel.createEvent(event);
            clearFields();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onbtnAddCoordinator(ActionEvent actionEvent) {
        User selected = comboExtraCoordinators.getValue();
        if (selected != null && !selectedCoordinators.contains(selected)) {
            selectedCoordinators.add(selected);
        }
    }

    private void clearFields() {
        txtName.clear();
        txtLocation.clear();
        txtNotes.clear();
        txtStartDate.clear();
        txtStartTime.clear();
        txtEndDate.clear();
        txtEndTime.clear();
        selectedCoordinators.clear();
        comboExtraCoordinators.getSelectionModel().clearSelection();
    }
}
