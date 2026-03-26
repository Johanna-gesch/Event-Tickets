package dk.easv.eventtickets.GUI.Controllers.Cards;

import dk.easv.eventtickets.BE.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class EventCardController {

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


    public void setEvent(Event event) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-mm-yyyy HH:mm");

        lblName.setText(event.getName());

        lblStartDateTime.setText(event.getStartDateTime().format(formatter));
        lblEndDateTime.setText(event.getTime());
        lblLocation.setText(event.getLocation());
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

    private void setCoordinators(List<String> names) {

        // List cells are reused, so without clear, labels would be doubling for every click on the cell
        subCoordinators.getChildren().clear();

        // Only necessary unless we make the list of coordinators not null
        if (names == null || names.isEmpty()) {
            return;
        }

        for (int i = 0; i < names.size(); i++) {

            Label lblCoor = new Label(names.get(i));

            if (i == 0) {
                lblCoor.getStyleClass().add("coorPrimary");
            } else {
                lblCoor.getStyleClass().add("coorSecondary");
            }

            subCoordinators.getChildren().add(lblCoor);
        }
    }

    @FXML
    private void onEditEvent(ActionEvent actionEvent) {
        System.out.println("You pressed the button");
    }

    @FXML
    private void onEditEC(ActionEvent actionEvent) {


    }

    @FXML
    private void onDeleteEvent(ActionEvent actionEvent) {

    }

    @FXML
    private void onCreateTicket(ActionEvent actionEvent) {

    }
}
