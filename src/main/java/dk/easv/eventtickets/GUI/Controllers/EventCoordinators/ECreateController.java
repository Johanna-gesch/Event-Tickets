package dk.easv.eventtickets.GUI.Controllers.EventCoordinators;


import dk.easv.eventtickets.GUI.Models.EventModel;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ECreateController implements Initializable {
    private EventModel eventModel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setModel(EventModel eventModel) {this.eventModel = eventModel;}



}
