package dk.easv.eventtickets.GUI.Controllers.Admins;

import dk.easv.eventtickets.BE.Customer;
import dk.easv.eventtickets.BE.User;
import dk.easv.eventtickets.BE.UserRole;
import dk.easv.eventtickets.GUI.Models.CustomerModel;
import dk.easv.eventtickets.GUI.Models.UserModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
    private CustomerModel customerModel;
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
        initUserModel();
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
        String fName = txtFName.getText();
        String lName = txtLName.getText();
        String email = txtEmail.getText();
        String selectedType = comboType.getValue();

        if (fName.isEmpty() || lName.isEmpty() || email.isEmpty() || selectedType == null) {
            displayError(new Exception("You must fill in all the fields"));
            return;
        }
        if ("Customer".equals(selectedType)){
            onCustomerSave(fName, lName, email);
        } else {
            onBtnAdminOrCoordinatorControllerSave(fName, lName, email, selectedType);
        }
    }

    public void onCustomerSave(String fName, String lName, String email){
        currentCustomer = new Customer();
        currentCustomer.setFName(fName);
        currentCustomer.setLName(lName);
        currentCustomer.setEmail(email);

        try{
            customerModel.createCustomer(currentCustomer);
            clearFields();
        } catch (Exception e) {
            displayError(new Exception("Could not create customer",e));
        }

    }

    public void onBtnAdminOrCoordinatorControllerSave(String fName, String lName, String email, String type){
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String hashPassword = userModel.hashPassword(password);


        if (username.isEmpty() || password.isEmpty()){
            displayError(new Exception("You must fill in all the fields"));
            return;
        }

        currentUser = new User();
        currentUser.setFName(fName);
        currentUser.setLName(lName);
        currentUser.setEmail(email);
        currentUser.setUsername(username);
        currentUser.setPasswordHash(hashPassword);

        if ("Admin".equals(type)) {
            currentUser.setRole(UserRole.ADMIN);
        } else if ("Event Coordinator".equals(type)) {
            currentUser.setRole(UserRole.EVENT_COORDINATOR);
        }


        try {
            User userCreated = userModel.createUser(currentUser);
            clearFields();
            personalInfo.setVisible(true);
            invisibleLayer.setVisible(false);
        } catch (Exception e) {
            displayError(new Exception("Could not create admin or coordinator",e));
        }
    }

    private void initUserModel() {
        try {
            userModel = new UserModel();
            customerModel = new CustomerModel();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

            e.printStackTrace();
            Platform.exit();
        }
    }

    public void onBtnCreateLogin(ActionEvent actionEvent) {

    }

    public void displayError(Throwable t) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something is wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    private void clearFields() {
        txtFName.clear();
        txtLName.clear();
        txtEmail.clear();
        txtUsername.clear();
        txtPassword.clear();
        comboType.getSelectionModel().clearSelection();
    }

}
