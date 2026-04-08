package dk.easv.eventtickets.GUI.Controllers.Cards;

import dk.easv.eventtickets.BE.User;

import dk.easv.eventtickets.GUI.Models.UserModel;
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

    private UserModel uMod;

    private User currentUser;

    private IUserCardListener listener;

    public void setUser(User user) {
        this.currentUser = user;

        lblName.setText(user.getFName() + " " + user.getLName());
        lblEmail.setText(user.getEmail());
        lblType.setText(user.getRoleDisplayName());

        if (user.getImagePath() != null) {
            Image image = new Image(getClass().getResource(user.getImagePath()).toExternalForm());
            imgView.setImage(image);
        }

    }

    public void setUserModel(UserModel userModel) {
        this.uMod = userModel;
    }

    @FXML
    private void onEdit(ActionEvent actionEvent) {
        if (listener != null) {
            listener.onEditUser(currentUser);
        }
    }

    @FXML
    private void onDeleteUser(ActionEvent actionEvent) {
        try {
            uMod.deleteUser(currentUser);
            uMod.getUserToBeViewed().remove(currentUser); // fjerner fra UI
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setListener(IUserCardListener listener) {
        this.listener = listener;
    }

}
