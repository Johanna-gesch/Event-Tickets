package dk.easv.eventtickets.BLL;

import dk.easv.eventtickets.BE.User;
import dk.easv.eventtickets.DAL.Users.IUserDataAccess;
import dk.easv.eventtickets.DAL.Users.UserDAO_DB;

import java.io.IOException;
import java.util.List;

public class UserManager {
    private final IUserDataAccess userDao;

    public UserManager() throws Exception {
        userDao = new UserDAO_DB();
    }

    public List<User> getAllUsers() throws Exception{
        return userDao.getAllUsers();
    }

    public User createUser (User user) throws Exception {
        return userDao.createUser(user);
    }
    public void deleteUser (User user) throws Exception {
        userDao.deleteUser(user);
    }
}
