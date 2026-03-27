package dk.easv.eventtickets.GUI.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SideBarController {
    @FXML
    private VBox rootCenter;
    @FXML
    private Label lblNavName, lblNavType;

    private String role;
    @FXML
    private Button btnSecond;

    public void setView(String fxmlPath) {
        try {

            Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
            rootCenter.getChildren().setAll(view);

        } catch (IOException e) {
            displayError(e, "Failed to set view");
        }
    }

    public void setRole(String role) {
        this.role = role;
        configureSidebar();
    }

    private void configureSidebar() {
        if ("admin".equals(role)) {
            btnSecond.setText("\uD83D\uDC64   Create User");
            lblNavType.setText("Admin");
        }
        if ("event".equals(role)) {
            btnSecond.setText("\uD83D\uDCC5   Events & Tickets");
            lblNavType.setText("Event Coordinator");
        }
    }

    @FXML
    private void onDashboard(ActionEvent actionEvent) {
        if ("admin".equals(role)) {
            setView("/dk/easv/eventtickets/Admin/AdminDash.fxml");
        }
        if ("event".equals(role)) {
            setView("/dk/easv/eventtickets/Event/EventDash.fxml");
        }
    }

    @FXML
    private void onBtnSecond(ActionEvent actionEvent) {
        if ("admin".equals(role)) {
            setView("/dk/easv/eventtickets/Admin/CreateUser.fxml");
        }
        if ("event".equals(role)) {
            setView("/dk/easv/eventtickets/Event/CreateEvent.fxml");
        }
    }

    @FXML
    private void onLogOut(ActionEvent actionEvent) {
        try {

            Parent loginView = FXMLLoader.load(getClass().getResource("/dk/easv/eventtickets/SignIn.fxml"));

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            stage.setScene(new Scene(loginView));
            stage.show();

        } catch (IOException e) {
            displayError(e, "Failed to Log Out");
            e.printStackTrace();
        }
    }

    private void displayError(Throwable t, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }
}
