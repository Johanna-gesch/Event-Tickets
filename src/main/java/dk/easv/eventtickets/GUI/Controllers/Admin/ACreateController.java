package dk.easv.eventtickets.GUI.Controllers.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ACreateController implements Initializable {

    @FXML
    private ImageView imgAvatar;

    @FXML
    private Button btnAvatarBig, btnAvatarSmall;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showEmptyAvatarState(true);
    }

    private void showEmptyAvatarState(boolean empty) {
        btnAvatarBig.setVisible(empty);
        btnAvatarSmall.setVisible(!empty);
    }

    @FXML
    private void onChooseAvatar(ActionEvent actionEvent) {
        // Filechooser og setAvatar(Image) -> showEmptyAvatarState(false)
    }


}
