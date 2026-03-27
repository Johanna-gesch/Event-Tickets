package dk.easv.eventtickets.GUI.Controllers.EventCoordinators;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ECreateController implements Initializable {

    @FXML
    private VBox vBoxTicket, vBoxVoucher;

    @FXML
    private Button btnTicket, btnVoucher;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnTicket.setDisable(true);
        btnVoucher.setDisable(false);

        btnTicket.getStyleClass().add("active");
        btnVoucher.getStyleClass().add("inactive");
    }

    @FXML
    private void onTicket(ActionEvent actionEvent) {
        vBoxTicket.setVisible(true);
        vBoxVoucher.setVisible(false);

        btnTicket.setDisable(true);
        btnVoucher.setDisable(false);

        btnTicket.getStyleClass().add("active");
        btnTicket.getStyleClass().remove("inactive");

        btnVoucher.getStyleClass().add("inactive");
        btnVoucher.getStyleClass().remove("active");
    }

    @FXML
    private void onVoucher(ActionEvent actionEvent) {

        vBoxVoucher.requestFocus();

        vBoxVoucher.setVisible(true);
        vBoxTicket.setVisible(false);

        btnTicket.setDisable(false);
        btnVoucher.setDisable(true);

        btnTicket.getStyleClass().add("inactive");
        btnTicket.getStyleClass().remove("active");

        btnVoucher.getStyleClass().add("active");
        btnVoucher.getStyleClass().remove("inactive");
    }

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

}
