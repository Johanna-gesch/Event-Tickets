package dk.easv.eventtickets.GUI.Controllers;

import dk.easv.eventtickets.BE.User;
import dk.easv.eventtickets.BE.UserRole;
import dk.easv.eventtickets.GUI.Controllers.Admins.ADashController;
import dk.easv.eventtickets.GUI.Controllers.EventCoordinators.EDashController;
import dk.easv.eventtickets.GUI.Models.EventModel;
import dk.easv.eventtickets.GUI.Models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SignInController implements Initializable {
    @FXML
    private TextField txtUsername, txtPassword;
    @FXML
    private Button btnSignIn;

    private UserModel userModel;
    private EventModel eventModel;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSignIn.setDefaultButton(true);
        try {
            userModel = new UserModel();
            eventModel = new EventModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onSignIn(ActionEvent actionEvent) {

        try {
            // Load root layout
            FXMLLoader rootLoader = new FXMLLoader(getClass().getResource("/dk/easv/eventtickets/SideBar.fxml"));
            Parent root = rootLoader.load();
            SideBarController sbmc = rootLoader.getController();

            sbmc.setUserModel(userModel);
            sbmc.setEventModel(eventModel);

            // Load center content

            String password = txtPassword.getText();

            User userLoggingIn = null;

            List<User> allUsers = userModel.getUserToBeViewed();

            for (User u : allUsers) {
                if (u.getUsername().equals(txtUsername.getText().trim())) {
                    userLoggingIn = u;
                }
            }

            if (userLoggingIn != null) {

                boolean valid = userModel.verifyPassword(password, userLoggingIn.getPasswordHash());

                if (valid) {

                    if (userLoggingIn.getRole() == UserRole.EVENT_COORDINATOR) {
                        EDashController edc = (EDashController) sbmc.setView("/dk/easv/eventtickets/EventCoordinator/EventDash.fxml");
                        edc.setModel(eventModel);
                        eventModel.setCurrentUser(userLoggingIn);
                        edc.setSideBarController(sbmc);

                        edc.setup();
                        sbmc.setRole("Event");
                        sbmc.setUsername(userLoggingIn.getFName() + " " + userLoggingIn.getLName());
                    }
                    if (userLoggingIn.getRole() == UserRole.ADMIN) {
                        ADashController adc = (ADashController) sbmc.setView("/dk/easv/eventtickets/Admin/AdminDash.fxml");
                        adc.setUserModel(userModel);
                        adc.setEventModel(eventModel);
                        adc.setSideBarController(sbmc);
                        adc.setup();
                        sbmc.setRole("Admin");
                        sbmc.setUsername(userLoggingIn.getFName() + " " + userLoggingIn.getLName());
                    }

                } else {
                    displayError(new Exception("Invalid username or password"), "");
                    return;
                }
            } else {
                displayError(new Exception("User doesn't exist"), "");
                return;
            }


            // 4) switch scene in same window
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            displayError(e, "Failed to load FXML");
        }

    }

    private void displayError(Throwable t, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

}





