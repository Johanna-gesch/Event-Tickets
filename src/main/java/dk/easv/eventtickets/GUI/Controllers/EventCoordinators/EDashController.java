package dk.easv.eventtickets.GUI.Controllers.EventCoordinators;

import dk.easv.eventtickets.BE.Event;
import dk.easv.eventtickets.GUI.Controllers.Cards.EventCardController;
import dk.easv.eventtickets.GUI.Models.EventModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EDashController implements Initializable {

    @FXML
    private TilePane tilePane;

    private EventModel eventModel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    public void setup() {
        showEvents(eventModel.getEventsToBeViewed());
    }

    private void showEvents(List<Event> events) {

        // Tiles are reused, so without clear, they would be doubling for every click on the cell
        tilePane.getChildren().clear();

        tilePane.setPrefTileWidth(400);

        for (Event ev : events) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/eventtickets/Cards/EventCards.fxml"));
                Parent card = loader.load(); // Loading the root element from the fxml file - in this case the VBox

                EventCardController ecc = loader.getController();
                ecc.setEvent(ev);
                ecc.isEventDash(true);
                ecc.setEventModel(eventModel);

                card.setFocusTraversable(false);

                tilePane.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
