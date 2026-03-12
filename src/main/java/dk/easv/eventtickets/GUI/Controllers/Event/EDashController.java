package dk.easv.eventtickets.GUI.Controllers.Event;

import dk.easv.eventtickets.BE.Event;
import dk.easv.eventtickets.GUI.Controllers.Cards.EventCardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EDashController implements Initializable {

    @FXML
    private TilePane tilePane;

    private List<Event> events;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventMockData();
        showEvents(events);
    }

    private void eventMockData() {
        events = List.of(
                new Event("EASV Party", "3rd of April 2026", "19:00 - 02:00", "Erhvervsakademiet", "!! Sold out !!\nRemember to hold staff meeting and buy decorations.", List.of("Judy Doe")),
                new Event("Coding Workshop", "4th of May 2026", "14:00 - 17:00", "Innovatoriet", "Snacks will be provided.", List.of("Judy Doe", "Jim Doe"))
        );
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

                card.setFocusTraversable(false);

                tilePane.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
