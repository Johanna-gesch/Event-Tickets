package dk.easv.eventtickets.GUI.Controllers.Admin;

import dk.easv.eventtickets.BE.*;
import dk.easv.eventtickets.GUI.Controllers.Cards.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ADashController implements Initializable {

    @FXML
    private ListView<User> lstUsers;
    @FXML
    private ListView<Event> lstEvents;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userMockData();
        eventMockData();

        handleUserCards();
        handleEventCards();
    }

    private void userMockData() {
        lstUsers.getItems().addAll(
                new User("John", "Doe", "jod@email.com", "Costumer", "/dk/easv/eventtickets/Pics/user.png"),
                new User("Jane", "Doe", "jad@email.com", "Admin", "/dk/easv/eventtickets/Pics/profil1.png"),
                new User("Judy", "Doe", "jud@email.com", "Event Coordinator", "/dk/easv/eventtickets/Pics/profil1.png"),
                new User("Jim", "Doe", "jid@email.com", "Event Coordinator", "/dk/easv/eventtickets/Pics/user.png")
        );
    }

    private void eventMockData() {
        lstEvents.getItems().addAll(
                new Event("EASV Party", "3rd of April 2026", "19:00 - 02:00", "Erhvervsakademiet", "!! Sold out !!\nRemember to hold staff meeting and buy decorations.", List.of("Judy Doe")),
                new Event("Coding Workshop", "4th of May 2026", "14:00 - 17:00", "Innovatoriet", "Snacks will be provided.", List.of("Judy Doe", "Jim Doe"))
        );
    }

    private void handleUserCards() {

        lstUsers.setSelectionModel(null); // Making cells non-clickable

        lstUsers.setCellFactory(list -> new ListCell<>() { // Creates new cell

            // Fields are local because each cell need its own FXMLLoader, controller and graphic
            private FXMLLoader loader;
            private UserCardController ucc;
            private Parent graphic; // Root element from fxml

            @Override
            protected void updateItem(User user, boolean empty) {

                // The item (user) is set correctly and the cells empty state is updated
                super.updateItem(user, empty);

                // If the cell has nothing to show -> remove cell completely
                if (empty || user == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }

                try {
                    // Listview reuses cells. 1st time loader will be null - second time loader is not null, so we can skip this part
                    if (loader == null) {
                        loader = new FXMLLoader(getClass().getResource("/dk/easv/eventtickets/Cards/UserCards.fxml"));
                        graphic = loader.load();
                    }
                    ucc = loader.getController();
                    ucc.setUser(user);
                    setGraphic(graphic);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void handleEventCards() {
        lstEvents.setCellFactory(list -> new ListCell<>() {

            // Fields are local because each cell need its own FXMLLoader, controller and graphic
            private FXMLLoader loader;
            private EventCardController ecc;
            private Parent graphic; // Root element from fxml

            @Override
            protected void updateItem(Event event, boolean empty) {

                // The item (user) is set correctly and the cells empty state is updated
                super.updateItem(event, empty);

                // If the cell has nothing to show -> remove cell completely
                if (empty || event == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }

                try {
                    // Listview reuses cells. 1st time loader will be null - second time loader is not null, so we can skip this part
                    if (loader == null) {
                        loader = new FXMLLoader(getClass().getResource("/dk/easv/eventtickets/Cards/EventCards.fxml"));
                        graphic = loader.load();
                    }
                    ecc = loader.getController();
                    ecc.setEvent(event);
                    ecc.isEventDash(false);
                    setGraphic(graphic);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @FXML
    private void onAdd(ActionEvent actionEvent) {

    }

    @FXML
    private void onDelete(ActionEvent actionEvent) {

    }



}
