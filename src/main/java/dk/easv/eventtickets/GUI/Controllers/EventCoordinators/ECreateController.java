package dk.easv.eventtickets.GUI.Controllers.EventCoordinators;


import dk.easv.eventtickets.BE.Event;
import dk.easv.eventtickets.GUI.Models.EventModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class ECreateController implements Initializable {
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
    private ComboBox comboExtraCoordinators;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setModel(EventModel eventModel) {
        this.eventModel = eventModel;
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

            eventModel.createEvent(event);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
