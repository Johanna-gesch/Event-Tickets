package dk.easv.eventtickets.GUI.Controllers.EventCoordinators;

import dk.easv.eventtickets.BE.Event;
import dk.easv.eventtickets.GUI.Controllers.Cards.EventCardController;
import dk.easv.eventtickets.GUI.Controllers.Cards.IEventCardListener;
import dk.easv.eventtickets.GUI.Controllers.SideBarController;
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

public class EDashController implements IEventCardListener {

    @FXML
    private TilePane tilePane;

    private EventModel eventModel;

    private SideBarController sidebarController;



    public void setup() {
        showEvents(eventModel.getEventsToBeViewed());
    }

    public void setSideBarController(SideBarController sidebarController) {
        this.sidebarController = sidebarController;
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
                ecc.setListener(EDashController.this);

                card.setFocusTraversable(false);

                tilePane.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setModel(EventModel eventModel) {this.eventModel = eventModel;}



    @Override
    public void onGetTickets(Event event) {
        try {
            ETicketController etc = (ETicketController) sidebarController.setView("/dk/easv/eventtickets/EventCoordinator/CreateTickets.fxml");
            etc.setModel(eventModel);
            etc.loadEventForTickets(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onUpdateEvent(Event event) {
        try{
            ECreateController ecc = (ECreateController) sidebarController.setView("/dk/easv/eventtickets/EventCoordinator/CreateEvent.fxml");
            ecc.setModel(eventModel);
            ecc.loadEventForEditing(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
