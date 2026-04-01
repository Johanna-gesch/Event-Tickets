package dk.easv.eventtickets.GUI.Controllers.EventCoordinators;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ETicketController {
    @FXML
    private TextField txtFName, txtLName, txtEmail;
    @FXML
    private ComboBox cboEventTickets, cboTicketType;
    @FXML
    private TextField txtNumOfTickets, txtVoucherType, txtNumOfVouchers;
    @FXML
    private ComboBox cboEventVouchers;

    @FXML
    private void onPreviewTick(ActionEvent actionEvent) throws IOException {
        //if all fields are filled out

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/dk/easv/eventtickets/Tickets/Ticket.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage(StageStyle.UTILITY);

        stage.setTitle("Ticket Preview");
        stage.setScene(scene);

        //Don't be able to open a new window before previous is closed.
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void onSend(ActionEvent actionEvent) {
    }

    @FXML
    private void onPrintTickets(ActionEvent actionEvent) {
    }

    @FXML
    private void onPreviewVouc(ActionEvent actionEvent) throws IOException {
        // If all necessary fields are filled out

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/dk/easv/eventtickets/Tickets/Voucher.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage(StageStyle.UTILITY);

        stage.setTitle("Voucher Preview");
        stage.setScene(scene);

        //Don't be able to open a new window before previous is closed.
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void onPrintVouchers(ActionEvent actionEvent) {
    }
}
