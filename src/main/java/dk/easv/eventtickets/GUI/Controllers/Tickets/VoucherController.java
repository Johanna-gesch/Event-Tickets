package dk.easv.eventtickets.GUI.Controllers.Tickets;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class VoucherController {

    @FXML
    private Label lblVoucherTitle, lblVoucherEvent;

    public void setVoucherText(String title, String Event) {
        lblVoucherTitle.setText(title);
        lblVoucherEvent.setText(Event);
    }

}
