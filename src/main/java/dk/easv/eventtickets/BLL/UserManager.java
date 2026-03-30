package dk.easv.eventtickets.BLL;

import at.favre.lib.crypto.bcrypt.BCrypt;
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
    public String bcrypt(String passwordHash) {

        // Hash password using the specified cost
        int workFactor = 12;

        String bcryptHashString = BCrypt.withDefaults().hashToString(workFactor, passwordHash.toCharArray());
        // Example hash: $2a$12$US00g/uMhoSBm.HiuieBjeMtoN69SN.GE25fCpldebzkryUyopws6

        // Verify hash with original password
        BCrypt.Result result = BCrypt.verifyer().verify(passwordHash.toCharArray(), bcryptHashString);
        //System.out.println(result);
        return bcryptHashString;
    }

    public User updateUser(User currentUser) throws Exception {
        return userDao.updateUser(currentUser);
    }
}
