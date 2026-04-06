package dk.easv.eventtickets.GUI.Controllers;

import dk.easv.eventtickets.GUI.Controllers.Admins.ACreateController;
import dk.easv.eventtickets.GUI.Controllers.Admins.ADashController;
import dk.easv.eventtickets.GUI.Controllers.EventCoordinators.ECreateController;
import dk.easv.eventtickets.GUI.Controllers.EventCoordinators.EDashController;
import dk.easv.eventtickets.GUI.Controllers.EventCoordinators.ETicketController;
import dk.easv.eventtickets.GUI.Models.EventModel;
import dk.easv.eventtickets.GUI.Models.UserModel;
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
    @FXML
    private Button btnSecond, btnThird;

    private String role;
    private UserModel userModel;
    private EventModel eventModel;

    public Object setView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();

            rootCenter.getChildren().setAll(view);
            System.out.println("Loaded controller" + loader.getController());

            return loader.getController();

        } catch (IOException e) {
            e.printStackTrace();
            displayError(e, "Failed to set view");
            return null;
        }
    }

    public void setRole(String role) {
        this.role = role;
        configureSidebar();
    }

    public void setUsername(String username) {
        lblNavName.setText(username);
    }

    private void configureSidebar() {
        if ("Admin".equals(role)) {
            btnSecond.setText("\uD83D\uDC64   Create User");
            btnThird.setVisible(false);
            btnThird.setManaged(false);
            lblNavType.setText("Admin");
        }
        if ("Event".equals(role)) {
            btnSecond.setText("\uD83D\uDCC5   Create Event");
            btnThird.setVisible(true);
            btnThird.setManaged(true);
            lblNavType.setText("Event Coordinator");
        }
    }

    @FXML
    private void onDashboard(ActionEvent actionEvent) {
        if ("Admin".equals(role)) {
            ADashController adc = (ADashController) setView("/dk/easv/eventtickets/Admin/AdminDash.fxml");
            adc.setUserModel(userModel);
            adc.setEventModel(eventModel);
            adc.setup();
        }
        if ("Event".equals(role)) {
            EDashController edc = (EDashController) setView("/dk/easv/eventtickets/EventCoordinator/EventDash.fxml");
            edc.setModel(eventModel);
            edc.setup();
        }
    }

    @FXML
    private void onBtnSecond(ActionEvent actionEvent) {
        if ("Admin".equals(role)) {
            ACreateController acc = (ACreateController) setView("/dk/easv/eventtickets/Admin/CreateUser.fxml");
            acc.setModel(userModel);
        }
        if ("Event".equals(role)) {
            ECreateController ecc = (ECreateController) setView("/dk/easv/eventtickets/EventCoordinator/CreateEvent.fxml");
            ecc.setModel(eventModel);
        }
    }

    @FXML
    private void onBtnThird(ActionEvent actionEvent) {
        ETicketController etc = (ETicketController) setView("/dk/easv/eventtickets/EventCoordinator/CreateTickets.fxml");
        etc.setModel(eventModel);
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

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public void setEventModel(EventModel eventModel) {
        this.eventModel = eventModel;
    }
}
