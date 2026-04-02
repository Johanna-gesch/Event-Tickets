package dk.easv.eventtickets.GUI.Controllers.EventCoordinators;

import dk.easv.eventtickets.BE.Event;
import dk.easv.eventtickets.GUI.Controllers.Tickets.TicketController;
import dk.easv.eventtickets.GUI.Controllers.Tickets.VoucherController;
import dk.easv.eventtickets.GUI.Models.EventModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ETicketController implements Initializable {

    @FXML
    private TextField txtFName, txtLName, txtEmail;
    @FXML
    private ComboBox<Event> cboEventTickets, cboEventVouchers;
    @FXML
    private ComboBox<String> cboTicketType;
    @FXML
    private TextField txtNumOfTickets, txtVoucherType, txtNumOfVouchers;

    private EventModel em;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try{
            em = new EventModel();
        } catch (Exception e) {
            e.printStackTrace();
        }

        cboTicketType.getItems().addAll("V.I.P", "Refreshments included", "1st row");
        cboTicketType.setEditable(true);

        cboEventTickets.setItems(em.getEventsToBeViewed());
        cboEventVouchers.setItems(em.getEventsToBeViewed());

    }

    @FXML
    private void onPreviewTick(ActionEvent actionEvent) throws IOException {
        //if all fields are filled out
        //open preview
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/dk/easv/eventtickets/Tickets/Ticket.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        //set text in voucher
        TicketController tc = fxmlLoader.getController();
        //vc.setVoucherText(title, event);

        Stage stage = new Stage(StageStyle.UTILITY);
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

        if (!txtVoucherType.getText().isEmpty()) {

            //open preview
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/dk/easv/eventtickets/Tickets/Voucher.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            //set text in voucher
            VoucherController vc = fxmlLoader.getController();
            String[] info = getTextForVouchers();
            vc.setVoucherText(info[0], info[1]);

            Stage stage = new Stage(StageStyle.UTILITY);
            stage.setScene(scene);

            //Don't be able to open a new window before previous is closed.
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Fejl");
            alert.setHeaderText(null);
            alert.setContentText("Udfyld venligst Voucher Type");
            alert.showAndWait();
        }

    }

    @FXML
    private void onPrintVouchers(ActionEvent actionEvent) {

        if (!txtVoucherType.getText().isEmpty() && !txtNumOfVouchers.getText().isEmpty()) {

            // get number of vouchers
            int numOfVouchers = 0;
            try {
                numOfVouchers = Integer.parseInt(txtNumOfVouchers.getText());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                displayError(new Exception("Please put a number in Number of Vouchers"));
            }

            // number of vouchers that can be on an a4 page
            int vouchersPerPage = 21;

            int printed = 0;

            // print
            PrinterJob job = PrinterJob.createPrinterJob();
            if (job != null && job.showPrintDialog(null)) {

                while (printed < numOfVouchers) {
                    TilePane tp = new TilePane();
                    tp.setPrefSize(595, 842); // a4 size
                    tp.setPrefTileWidth(160);
                    tp.setPrefTileHeight(96);

                    // Fill tilepane with vouchers
                    for (int i = 0; i < vouchersPerPage && printed < numOfVouchers; i++, printed++) {
                        Parent voucher = getPrintSetupForVoucher();
                        tp.getChildren().add(voucher);
                    }

                    // Print
                    boolean success = job.printPage(tp);
                    if (!success) break;
                }

                job.endJob();
            }
        }


    }

    private String[] getTextForVouchers() {

        String title = txtVoucherType.getText();

        String event;
        if (cboEventVouchers.getValue() != null) {
            event = String.valueOf(cboEventVouchers.getValue());
        } else {
            event = "";
        }

        return new String[] {title, event};
    }

    private Parent getPrintSetupForVoucher() {
        try {
            // Load voucher fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/eventtickets/Tickets/Voucher.fxml"));
            Parent voucher = loader.load();

            // set text for vouchers
            VoucherController vc = loader.getController();
            String[] info = getTextForVouchers();
            vc.setVoucherText(info[0], info[1]);

            // Limit size on a4 paper
            voucher.setScaleX(0.32);
            voucher.setScaleY(0.32);

            return voucher;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void displayError(Throwable t) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Something is wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }



}
