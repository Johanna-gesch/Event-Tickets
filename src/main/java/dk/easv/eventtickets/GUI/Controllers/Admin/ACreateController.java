package dk.easv.eventtickets.GUI.Controllers.Admin;

import dk.easv.eventtickets.BE.Customer;
import dk.easv.eventtickets.BE.User;
import dk.easv.eventtickets.GUI.Controllers.Model.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ACreateController implements Initializable {
    @FXML
    private TextField txtUserType;
    private UserModel userModel;
    @FXML
    private VBox personalInfo;
    @FXML
    private ComboBox <String> comboType;
    @FXML
    private VBox invisibleLayer;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtUsername;
    @FXML
    private ComboBox <String> comboboxUser;
    @FXML
    private TextField txtFName;
    @FXML
    private TextField txtLName;
    @FXML
    private TextField txtEmail;
    @FXML
    private ImageView imgAvatar;
    @FXML
    private User currentUser;
    @FXML
    private Customer currentCustomer;
    @FXML
    private Button btnAvatarBig, btnAvatarSmall;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboType.getItems().addAll("Admin", "Event Coordinator", "Customer");
        showEmptyAvatarState(true);

        //This checks whether an admin or coordinator is chosen, and shows the invisible menu accordingly.
        comboType.setOnAction(event -> {
            String selected = comboType.getSelectionModel().getSelectedItem();

            if ("Admin".equals(selected)) {
                invisibleLayer.setVisible(false);
                txtUserType.setText("Admin");

            } else if("Event Coordinator".equals(selected)) {
                invisibleLayer.setVisible(false);
                txtUserType.setText("Event Coordinator");
            }
            else {
                invisibleLayer.setVisible(true);
                txtUserType.setText("");
            }
        });

    }

    private void showEmptyAvatarState(boolean empty) {
        btnAvatarBig.setVisible(empty);
        btnAvatarSmall.setVisible(!empty);
    }

    @FXML
    private void onChooseAvatar(ActionEvent actionEvent) {
        // Filechooser og setAvatar(Image) -> showEmptyAvatarState(false)
    }


    public void onbtnSave(ActionEvent actionEvent) {
        if(invisibleLayer.isVisible()){
            onBtnAdminControllerSave();
        } else {
            onCustomerSave();

        }
    }

    public void onCustomerSave(){
        String FName = txtFName.getText();
        String LName = txtLName.getText();
        String Email = txtEmail.getText();

        currentCustomer = new Customer(-1, FName, LName, Email);
        currentCustomer.setFName(FName);
        currentCustomer.setLName(LName);
        currentCustomer.setEmail(Email);

    }

    public void onBtnAdminControllerSave(){
        String FName = txtFName.getText();
        String LName = txtLName.getText();
        String Email = txtEmail.getText();
        String type = comboType.getSelectionModel().getSelectedItem();

        currentUser = new User(-1,null, null, FName, LName, Email, type);
        currentUser.setFName(FName);
        currentUser.setLName(LName);
        currentUser.setEmail(Email);
        currentUser.setType(type);

        currentUser.setUsername(txtUsername.getText());
        currentUser.setPasswordHash(txtPassword.getText());

        try {
            userModel.createUser(currentUser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        this.currentUser = tempUser;
//        tempUser.setType(type);

        personalInfo.setVisible(true);
        invisibleLayer.setVisible(false);

        comboboxUser.getItems().add(type);

    }


    public void onBtnCreateLogin(ActionEvent actionEvent) {
        /**String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (currentUser == null){
            System.out.println("Der er ingen data");
            return;
        }
        currentUser.setUsername(username);
        currentUser.setPasswordHash(password);
        try {
            userModel.createUser(currentUser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
         **/
    }

}
