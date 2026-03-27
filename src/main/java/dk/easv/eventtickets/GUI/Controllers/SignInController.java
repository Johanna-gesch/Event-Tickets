package dk.easv.eventtickets.GUI.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SignInController {
    @FXML
    private TextField txtUsername, txtPassword;


    @FXML
    private void onSignIn(ActionEvent actionEvent) {

        try {
            // Load root layout
            FXMLLoader rootLoader = new FXMLLoader(getClass().getResource("/dk/easv/eventtickets/SideBar.fxml"));
            Parent root = rootLoader.load();
            SideBarController sbmc = rootLoader.getController();

            // Load center content
            if ("admin".equalsIgnoreCase(txtUsername.getText())) {
                sbmc.setView("/dk/easv/eventtickets/Admin/AdminDash.fxml");
                sbmc.setRole("admin");
            }
            if ("event".equalsIgnoreCase(txtUsername.getText())) {
                sbmc.setView("/dk/easv/eventtickets/Event/EventDash.fxml");
                sbmc.setRole("event");
            }

            // 4) switch scene in same window
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
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





