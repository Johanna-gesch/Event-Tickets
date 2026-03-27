package dk.easv.eventtickets.GUI.Controllers.Cards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EditECCard {
    @FXML
    private Label TitleEmployee;
    @FXML
    private Label EmployeeName;

    @FXML
    private void RemoveBTN(ActionEvent actionEvent) {
        System.out.println("Remove");
    }
}
