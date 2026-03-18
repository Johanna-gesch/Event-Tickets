package dk.easv.eventtickets.GUI.Controllers.Cards;

import dk.easv.eventtickets.BE.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UserCardController {

    @FXML
    private ImageView imgView;
    @FXML
    private Label lblName;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblType;

    public void setUser(User user) {
        lblName.setText(user.getFName() + " " + user.getLName());
        lblEmail.setText(user.getEmail());

        Image image = new Image(getClass().getResource(user.getImagePath()).toExternalForm());
        imgView.setImage(image);
    }

    @FXML
    private void onEdit(ActionEvent actionEvent) {
        System.out.println("you pressed the button");
    }
}
