package dk.easv.eventtickets.GUI.Controllers.Model;

import dk.easv.eventtickets.BE.User;
import dk.easv.eventtickets.BLL.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserModel {
    private UserManager uMan;
    private ObservableList<User> userToBeViewed;

    public UserModel() throws Exception {
        uMan = new UserManager();
        userToBeViewed = FXCollections.observableArrayList();
        userToBeViewed.addAll(uMan.getAllUsers());
    }

    public User createUser(User newUser) throws Exception {

        User usercreated = uMan.createUser(newUser);

        userToBeViewed.add(usercreated);

        return usercreated;

    }
}
