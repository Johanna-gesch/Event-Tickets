package dk.easv.eventtickets.GUI.Controllers.Cards;

import dk.easv.eventtickets.BE.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;

import java.util.List;

public class EventCardController {
    @FXML
    private Label lblName;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblTime;
    @FXML
    private Label lblLocation;
    @FXML
    private Label lblNotes;
    @FXML
    private Button btnEdit;
    @FXML
    private HBox subCoordinators;
    @FXML
    private Separator separator;
    @FXML
    private HBox hBoxBtnDelete;

    public void setEvent(Event event) {
        lblName.setText(event.getName());
        lblDate.setText(event.getDate());
        lblTime.setText(event.getTime());
        lblLocation.setText(event.getLocation());
        lblNotes.setText(event.getNotes());

        setCoordinators(event.getCoordinators());
    }

    public void isEventDash(boolean visible) {
        btnEdit.setVisible(visible); // Makes it invisble but still takes up space
        btnEdit.setManaged(visible); // Removes the space it takes up

        separator.setVisible(visible);
        separator.setManaged(visible);

        hBoxBtnDelete.setVisible(visible);
        hBoxBtnDelete.setManaged(visible);
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
    private void onEdit(ActionEvent actionEvent) {
        System.out.println("You pressed the button");
    }

    @FXML
    private void onDelete(ActionEvent actionEvent) {

    }
}
