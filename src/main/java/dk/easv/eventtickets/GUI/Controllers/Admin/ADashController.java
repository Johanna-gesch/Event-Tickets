package dk.easv.eventtickets.GUI.Controllers.Admin;

import dk.easv.eventtickets.BE.*;
import dk.easv.eventtickets.GUI.Controllers.Cards.*;
import dk.easv.eventtickets.GUI.Models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ADashController implements Initializable {
    private UserModel userModel;

    @FXML
    private ListView<User> lstUsers;
    @FXML
    private ListView<Event> lstEvents;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            userModel = new UserModel();
            handleUserCards();
            handleEventCards();

            lstUsers.setItems(userModel.getUserToBeViewed());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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
                    ucc.setUserModel(userModel);
                    setGraphic(graphic);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void handleEventCards() {

        lstUsers.setSelectionModel(null); // Making cells non-clickable

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



}
