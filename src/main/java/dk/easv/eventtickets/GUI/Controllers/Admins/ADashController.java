package dk.easv.eventtickets.GUI.Controllers.Admins;

import dk.easv.eventtickets.BE.*;
import dk.easv.eventtickets.GUI.Controllers.Cards.*;
import dk.easv.eventtickets.GUI.Controllers.SideBarController;
import dk.easv.eventtickets.GUI.Models.EventModel;
import dk.easv.eventtickets.GUI.Models.UserModel;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ADashController implements IUserCardListener {
    private UserModel userModel;
    private EventModel eventModel;
    private SideBarController sidebarController;

    @FXML
    private ListView<User> lstUsers;
    @FXML
    private ListView<Event> lstEvents;
    @FXML
    private TextField txtSearchUser;
    @FXML
    private TextField txtSearchEvent;

    private FilteredList<User> filteredUsers;
    private FilteredList<Event> filteredEvents;


    public void setup(){
        handleUserCards();
        handleEventCards();

        setupUserSearch();
        setupEventSearch();
    }

    public void setSideBarController(SideBarController sidebarController) {
        this.sidebarController = sidebarController;
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

                    ucc.setListener(ADashController.this);

                    setGraphic(graphic);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void handleEventCards() {

        lstEvents.setSelectionModel(null); // Making cells non-clickable

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
                    ecc.setEventModel(eventModel);
                    ecc.isEventDash(false);
                    setGraphic(graphic);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    @Override
    public void onEditUser(User user) {
        try {
            ACreateController acc = (ACreateController) sidebarController.setView(
                    "/dk/easv/eventtickets/Admin/CreateUser.fxml"
            );
            acc.setModel(userModel);
            acc.loadUserForEditing(user);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUserModel(UserModel userModel) {this.userModel = userModel;}

    public void setEventModel(EventModel eventModel) {this.eventModel = eventModel;}

    private void setupUserSearch() {
        // Wrap the original list
        filteredUsers = new FilteredList<>(userModel.getUserToBeViewed(), u -> true);

        // Bind filtered list to ListView
        lstUsers.setItems(filteredUsers);

        // Add listener to search field
        txtSearchUser.textProperty().addListener((obs, oldValue, newValue) -> {
            String filter = newValue == null ? "" : newValue.toLowerCase().trim();

            filteredUsers.setPredicate(user -> {
                if (filter.isEmpty()) return true;

                String fullName = (user.getFName() + " " + user.getLName()).toLowerCase();
                String email = user.getEmail() != null ? user.getEmail().toLowerCase() : "";

                return fullName.contains(filter) || email.contains(filter);
            });
        });
    }

    private void setupEventSearch() {
        filteredEvents = new FilteredList<>(eventModel.getEventsToBeViewed(), e -> true);

        lstEvents.setItems(filteredEvents);

        txtSearchEvent.textProperty().addListener((obs, oldValue, newValue) -> {
            String filter = newValue == null ? "" : newValue.toLowerCase().trim();

            filteredEvents.setPredicate(event -> {
                if (filter.isEmpty()) return true;

                boolean nameMatch = event.getName().toLowerCase().contains(filter);
                boolean locationMatch = event.getLocation() != null &&
                        event.getLocation().toLowerCase().contains(filter);

                return nameMatch || locationMatch;
            });
        });
    }
}
