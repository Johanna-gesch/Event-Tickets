package dk.easv.eventtickets.GUI.Controllers.Tickets;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TicketController {

    @FXML
    private Label lblTicketCostumerName;
    @FXML
    private Label lblTicketTitle;
    @FXML
    private Label lblStartDateTime, lblEndDateTime, lblTicketLocation;
    @FXML
    private Label lblTicketType;


    public void setTicketText(String fullname, String event, String location, String startDateTime, String endDateTime, String type) {
        lblTicketCostumerName.setText(fullname);
        lblTicketTitle.setText(event);
        lblTicketLocation.setText(location);
        lblStartDateTime.setText(startDateTime);
        lblEndDateTime.setText(endDateTime);
        lblTicketType.setText(type);
    }


}
