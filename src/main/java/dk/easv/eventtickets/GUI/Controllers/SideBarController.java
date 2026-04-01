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
    private Button btnSecond, btnThird;

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
        if ("Admin".equals(role)) {
            btnSecond.setText("\uD83D\uDC64   Create User");
            btnThird.setVisible(false);
            btnThird.setManaged(false);
            lblNavType.setText("Admin");
        }
        if ("Event".equals(role)) {
            btnSecond.setText("\uD83D\uDCC5   Events & Tickets");
            btnThird.setVisible(true);
            btnThird.setManaged(true);
            lblNavType.setText("Event Coordinator");
        }
    }

    @FXML
    private void onDashboard(ActionEvent actionEvent) {
        if ("Admin".equals(role)) {
            setView("/dk/easv/eventtickets/Admin/AdminDash.fxml");
        }
        if ("Event".equals(role)) {
            setView("/dk/easv/eventtickets/EventCoordinator/EventDash.fxml");
        }
    }

    @FXML
    private void onBtnSecond(ActionEvent actionEvent) {
        if ("Admin".equals(role)) {
            setView("/dk/easv/eventtickets/Admin/CreateUser.fxml");
        }
        if ("Event".equals(role)) {
            setView("/dk/easv/eventtickets/EventCoordinator/CreateEvent.fxml");
        }
    }

    @FXML
    private void onBtnThird(ActionEvent actionEvent) {
        setView("/dk/easv/eventtickets/EventCoordinator/CreateTickets.fxml");
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
