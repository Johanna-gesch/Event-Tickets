package dk.easv.eventtickets.GUI.Controllers.Cards;

import dk.easv.eventtickets.BE.Event;
import dk.easv.eventtickets.BE.User;
import dk.easv.eventtickets.GUI.Controllers.EventCoordinators.ECEdittEvent;
import dk.easv.eventtickets.GUI.Models.EventModel;
import dk.easv.eventtickets.GUI.Controllers.Admins.AEditEC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EventCardController{

    @FXML
    private Button btnCreateTicket;
    @FXML
    private Button btnEditEC;
    @FXML
    private Label lblName;
    @FXML
    private Label lblStartDateTime;
    @FXML
    private Label lblEndDateTime;
    @FXML
    private Label lblLocation;
    @FXML
    private Label lblNotes;
    @FXML
    private Button btnEditEvent;
    @FXML
    private HBox subCoordinators;

    private Event currentEvent;

    private EventModel eMod;



    public void setEvent(Event event) {
        this.currentEvent = event;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-mm-yyyy HH:mm");

        lblName.setText(event.getName());

        lblStartDateTime.setText("Start: " + event.getStartDateTime().format(formatter));

        if (event.getEndDateTime() != null) {
            lblEndDateTime.setText("End: " + event.getEndDateTime().format(formatter));
        } else {
            lblEndDateTime.setText("");
        }

        lblLocation.setText("Location: " + event.getLocation());
        lblNotes.setText(event.getNotes());

        setCoordinators(event.getCoordinators());
    }

    public void isEventDash(boolean visible) {
        btnEditEvent.setVisible(visible); // Makes it invisble but still takes up space
        btnEditEvent.setManaged(visible); // Removes the space it takes up

        btnEditEC.setVisible(!visible);
        btnEditEC.setManaged(!visible);

        btnCreateTicket.setVisible(visible);
        btnCreateTicket.setManaged(visible);
    }

    private void setCoordinators(List<User> names) {

        // List cells are reused, so without clear, labels would be doubling for every click on the cell
        subCoordinators.getChildren().clear();

        // Only necessary unless we make the list of coordinators not null
        if (names == null || names.isEmpty()) {
            return;
        }

        for (int i = 0; i < names.size(); i++) {

            Label lblCoor = new Label(names.get(i).getFName() + " " + names.get(i).getLName());

            if (i == 0) {
                lblCoor.getStyleClass().add("coorPrimary");
            } else {
                lblCoor.getStyleClass().add("coorSecondary");
            }

            subCoordinators.getChildren().add(lblCoor);
        }
    }

    @FXML
    private void onEditEvent(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/dk/easv/eventtickets/EventCoordinator/ECEditEvent.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        ECEdittEvent controller = fxmlLoader.getController();
        controller.setEvent(currentEvent);

        Stage stage = new Stage();
        stage.setTitle("ECEditView");
        stage.setScene(scene);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void onEditEC(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/dk/easv/eventtickets/Admin/EditECEvent.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        AEditEC controller = fxmlLoader.getController();
        controller.setEvent(currentEvent);

        Stage stage = new Stage();
        stage.setTitle("ECView");
        stage.setScene(scene);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void onDeleteEvent(ActionEvent actionEvent) throws Exception {
        try {
            eMod.deleteEvent(currentEvent);
            eMod.getEventsToBeViewed().remove(currentEvent);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onCreateTicket(ActionEvent actionEvent) {

    }

    public void setEventModel(EventModel eventModel) {
        this.eMod = eventModel;
    }

}
