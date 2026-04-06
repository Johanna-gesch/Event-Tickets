package dk.easv.eventtickets.GUI.Controllers.EventCoordinators;

import dk.easv.eventtickets.BE.Event;
import dk.easv.eventtickets.GUI.Controllers.Tickets.TicketController;
import dk.easv.eventtickets.GUI.Controllers.Tickets.VoucherController;
import dk.easv.eventtickets.GUI.Models.EventModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

        int numOfTickets = 1;
        if (!txtNumOfTickets.getText().isEmpty() && txtNumOfTickets.getText() != null) {
            try {
                numOfTickets = Integer.parseInt(txtNumOfTickets.getText());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                displayError(new Exception("Please put a number in Number of Tickets."));
            }
        }

        List<TilePane> pages = createAllTicketPages(numOfTickets);

        VBox container = new VBox();
        container.setAlignment(Pos.TOP_CENTER);
        container.setPrefWidth(595);
        container.getChildren().addAll(pages);
        container.setSpacing(50);

        openPreviewInScrollPane(container);

    }

    // ny kommentar
    @FXML
    private void onSend(ActionEvent actionEvent) {

    }

    @FXML
    private void onPrintTickets(ActionEvent actionEvent) {

        int numOfTickets = 1;
        if (!txtNumOfTickets.getText().isEmpty() && txtNumOfTickets.getText() != null) {
            try {
                numOfTickets = Integer.parseInt(txtNumOfTickets.getText());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                displayError(new Exception("Please put a number in Number of Tickets."));
            }
        }

        PrinterJob job = PrinterJob.createPrinterJob();

        if (job != null && job.showPrintDialog(null)) {

            List<TilePane> pages = createAllTicketPages(numOfTickets);

            for (TilePane page : pages) {
                boolean success = job.printPage(page);
                if (!success) break;
            }

            job.endJob();
        }

    }

    @FXML
    private void onPreviewVouc(ActionEvent actionEvent) throws IOException {

        int numOfVouchers = 1;
        if (!txtNumOfVouchers.getText().isEmpty() && txtNumOfVouchers.getText() != null) {
            try {
                numOfVouchers = Integer.parseInt(txtNumOfVouchers.getText());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                displayError(new Exception("Please put a number in Number of Vouchers."));
            }
        }

        List<TilePane> pages = createAllVoucherPages(numOfVouchers);

        VBox container = new VBox();
        container.setAlignment(Pos.TOP_CENTER);
        container.setPrefWidth(595);
        container.getChildren().addAll(pages);
        container.setSpacing(50);

        openPreviewInScrollPane(container);

    }

    @FXML
    private void onPrintVouchers(ActionEvent actionEvent) {

        int numOfVouchers = 1;
        if (!txtNumOfVouchers.getText().isEmpty() && txtNumOfVouchers.getText() != null) {
            try {
                numOfVouchers = Integer.parseInt(txtNumOfVouchers.getText());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                displayError(new Exception("Please put a number in Number of Vouchers."));
            }
        }

        PrinterJob job = PrinterJob.createPrinterJob();

        if (job != null && job.showPrintDialog(null)) {

            List<TilePane> pages = createAllVoucherPages(numOfVouchers);

            for (TilePane page : pages) {
                boolean success = job.printPage(page);
                if (!success) break;
            }

            job.endJob();
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

    private String[] getTextForTickets() {

        String fullname = txtFName.getText() + " " + txtLName.getText();
        String event = String.valueOf(cboEventTickets.getValue());
        String location = cboEventTickets.getValue().getLocation();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String startDateTime = "Start: " + cboEventTickets.getValue().getStartDateTime().format(formatter);

        String endDateTime;
        if (cboEventTickets.getValue().getEndDateTime() != null) {
            endDateTime = "End: " + cboEventTickets.getValue().getEndDateTime().format(formatter);
        } else {
            endDateTime = "";
        }

        String type = cboTicketType.getValue();

        return new String[] {fullname, event, startDateTime, endDateTime, location, type};
    }

    private Parent createSingleVoucher() {

        if (!txtVoucherType.getText().isEmpty() && !txtNumOfVouchers.getText().isEmpty()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/eventtickets/Tickets/Voucher.fxml"));
                Parent voucher = loader.load();

                VoucherController vc = loader.getController();
                String[] info = getTextForVouchers();
                vc.setVoucherText(info[0], info[1]);

                voucher.setScaleX(0.32);
                voucher.setScaleY(0.32);

                return voucher;

            } catch (Exception e) {
                e.printStackTrace();
                displayError(new Exception("Error creating voucher"));
            }
        } else {
            displayError(new Exception("Please fill out all required fields marked with *"));
        }
        return null;
    }

    private TilePane createVoucherPage(int numberOfVouchers) {

        int vouchersPerPage = 21;

        TilePane tp = new TilePane();
        tp.setMinSize(TilePane.USE_PREF_SIZE, TilePane.USE_PREF_SIZE);
        tp.setMaxSize(TilePane.USE_PREF_SIZE, TilePane.USE_PREF_SIZE);
        tp.setPrefColumns(3);
        tp.setPrefRows(7);
        tp.setPrefTileWidth(160);
        tp.setPrefTileHeight(96);

        int count = Math.min(numberOfVouchers, vouchersPerPage);

        for (int i = 0; i < count; i++) {
            tp.getChildren().add(createSingleVoucher());
        }

        return tp;
    }

    private List<TilePane> createAllVoucherPages(int totalVouchers) {

        int vouchersPerPage = 21;
        int created = 0;

        List<TilePane> pages = new ArrayList<>();

        while (created < totalVouchers) {

            int remaining = totalVouchers - created;

            TilePane page = createVoucherPage(remaining);
            pages.add(page);

            created += Math.min(remaining, vouchersPerPage);
        }

        return pages;
    }

    private Parent createSingleTicket() {

        if (
                !txtFName.getText().isEmpty() &&
                !txtLName.getText().isEmpty() &&
                cboEventTickets.getValue() != null &&
                cboTicketType.getValue() != null
        ) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/eventtickets/Tickets/Ticket.fxml"));
                Parent ticket = loader.load();

                TicketController tc = loader.getController();
                String[] info = getTextForTickets();
                tc.setTicketText(info[0], info[1], info[2], info[3], info[4], info[5]);

                ticket.setScaleX(0.65);
                ticket.setScaleY(0.65);

                return ticket;

            } catch (Exception e) {
                e.printStackTrace();
                displayError(new Exception("Error creating ticket"));
            }

        } else {
            displayError(new Exception("Please fill out all required fields marked with *"));
        }
        return null;
    }

    private TilePane createTicketPage(int numberOfTickets) {

        int ticketsPerPage = 4;

        TilePane tp = new TilePane();
        tp.setMinSize(TilePane.USE_PREF_SIZE, TilePane.USE_PREF_SIZE);
        tp.setMaxSize(TilePane.USE_PREF_SIZE, TilePane.USE_PREF_SIZE);
        tp.setPrefColumns(2);
        tp.setPrefRows(2);
        tp.setPrefTileWidth(195);
        tp.setPrefTileHeight(357.5);

        int count = Math.min(numberOfTickets, ticketsPerPage);

        for (int i = 0; i < count; i++) {
            tp.getChildren().add(createSingleTicket());
        }

        return tp;
    }

    private List<TilePane> createAllTicketPages(int totalTickets) {

        int ticketsPerPage = 4;
        int created = 0;

        List<TilePane> pages = new ArrayList<>();

        while (created < totalTickets) {

            int remaining = totalTickets - created;

            TilePane page = createTicketPage(remaining);
            pages.add(page);

            created += Math.min(remaining, ticketsPerPage);
        }

        return pages;
    }

    private void openPreviewInScrollPane(VBox vb) {
        ScrollPane sp = new ScrollPane();
        sp.setContent(vb);
        vb.setFillWidth(true);
        sp.setPrefSize(595, 842);

        Scene scene = new Scene(sp);

        Stage stage = new Stage(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    private void sendMailWithAttachment(String toEmail, File pdfFile) {
        String fromEmail = "dinmail@gmail.com"; // Afsender
        String password = "ditAppPassword"; // Gmail kræver app-password

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromEmail));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            msg.setSubject("Your Event Tickets");

            // Body
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Hello,\n\nPlease find your tickets attached.\n\nBest regards.");

            // Vedhæft PDF
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(pdfFile);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            msg.setContent(multipart);

            // Send mail direkte
            Transport.send(msg);

            System.out.println("Mail sent successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            displayError(new Exception("Failed to send email"));
        }
    }


    private void displayError(Throwable t) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Something is wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }



}
