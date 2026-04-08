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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

public class ACreateController implements Initializable {
    @FXML
    private TextField txtUserType;
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

    private UserModel userModel;
    private CustomerModel customerModel;
    private boolean isEditMode = false;
    private File imagePath;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initUserModel();
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

    public void setModel(UserModel userModel) {this.userModel = userModel;}

    private void showEmptyAvatarState(boolean empty) {
        btnAvatarBig.setVisible(empty);
        btnAvatarSmall.setVisible(!empty);
    }

    @FXML
    private void onChooseAvatar(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Vælg et billede");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Billeder", "*.png", "*.jpg", "*.jpeg")
        );

        String userHome = System.getProperty("user.home");
        File dir = new File(userHome);

        fileChooser.setInitialDirectory(dir);

        Stage stage = (Stage) imgAvatar.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            imagePath = file;
            Image image = new Image(file.toURI().toString());
            imgAvatar.setImage(image);
            showEmptyAvatarState(false);
        }

    }

    @FXML
    private void onbtnSave(ActionEvent actionEvent) {
        String fName = txtFName.getText();
        String lName = txtLName.getText();
        String email = txtEmail.getText();
        String selectedType = comboType.getValue();

        if (fName.isEmpty() || lName.isEmpty() || email.isEmpty() || selectedType == null) {
            displayError(new Exception("You must fill in all the fields"));
            return;
        }

        if (isEditMode) {
            UserRole role = null;

            if ("Admin".equals(selectedType)) {
                role = UserRole.ADMIN;
            } else if ("Event Coordinator".equals(selectedType)) {
                role = UserRole.EVENT_COORDINATOR;
            }
            updateExistingUser(fName, lName, email, txtUsername.getText(), role);
        } else {
            if ("Customer".equals(selectedType)){
                onCustomerSave(fName, lName, email);
            } else {
                onBtnAdminOrCoordinatorControllerSave(fName, lName, email, selectedType);
            }
        }
    }

    private void onCustomerSave(String fName, String lName, String email){
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

    private void onBtnAdminOrCoordinatorControllerSave(String fName, String lName, String email, String type){
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


        if (imagePath != null) {
            try {
                File avatarDir = new File("src/main/resources/dk/easv/eventtickets/Avatars");
                if (!avatarDir.exists()) {
                    avatarDir.mkdirs();
                }

                String newFileName = fName + "_" + imagePath.getName();
                File destFile = new File(avatarDir, newFileName);

                Files.copy(
                        imagePath.toPath(),
                        destFile.toPath(),
                        StandardCopyOption.REPLACE_EXISTING
                );

                currentUser.setImagePath("/dk/easv/eventtickets/Avatars/" + newFileName);

            } catch (Exception e) {
                e.printStackTrace();
                displayError(new Exception("Couldn't save photo."));
            }
        }


        try {
            User userCreated = userModel.createUser(currentUser);
            clearFields();
            personalInfo.setVisible(true);
            showEmptyAvatarState(true);
            invisibleLayer.setVisible(true);
        } catch (Exception e) {
            displayError(new Exception("Could not create admin or coordinator",e));
        }
    }

    private void initUserModel() {
        try {
            if (userModel == null) {
                userModel = new UserModel();
            }
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
        imgAvatar.setImage(null);
    }

    public void loadUserForEditing(User user) {
        this.currentUser = user;
        this.isEditMode = true;

        txtFName.setText(user.getFName());
        txtLName.setText(user.getLName());
        txtEmail.setText(user.getEmail());
        txtUsername.setText(user.getUsername());
        comboType.getSelectionModel().select(user.getRole().getDisplayName());

        //This is to make sure the invisible layer isn't hidden when editing an admin or coordinator.
        comboType.getOnAction().handle(null);

        // Load avatar if exists
        if (user.getImagePath() != null && !user.getImagePath().isEmpty()) {
            try {
                String path = user.getImagePath();
                File file = new File("src/main/resources" + path);
                if (file.exists()) {
                    imgAvatar.setImage(new Image(file.toURI().toString()));
                }
                showEmptyAvatarState(false);
            } catch (Exception e) {
                displayError(new Exception("Kunne ikke loade avatar."));
                showEmptyAvatarState(true);
            }
        } else {
            showEmptyAvatarState(true);
        }

    }

    private void updateExistingUser(String fName, String lName, String email, String username, UserRole role) {
        try {
            currentUser.setFName(fName);
            currentUser.setLName(lName);
            currentUser.setEmail(email);
            currentUser.setUsername(username);
            currentUser.setRole(role);

            String newPassword = txtPassword.getText();
            if (!newPassword.isEmpty()) {
                currentUser.setPasswordHash(userModel.hashPassword(newPassword));
            }

            if (imagePath != null) {
                File avatarDir = new File("src/main/resources/dk/easv/eventtickets/Avatars");
                if (!avatarDir.exists()) avatarDir.mkdirs();

                String newFileName = username + "_" + imagePath.getName();
                File destFile = new File(avatarDir, newFileName);

                Files.copy(
                        imagePath.toPath(),
                        destFile.toPath(),
                        StandardCopyOption.REPLACE_EXISTING
                );

                currentUser.setImagePath("/dk/easv/eventtickets/Avatars/" + newFileName);
            }

            userModel.updateUser(currentUser);

            clearFields();
            isEditMode = false;

        } catch (Exception e) {
            displayError(new Exception("Could not update user", e));
        }
    }
}
