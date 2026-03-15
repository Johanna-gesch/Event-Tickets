package dk.easv.eventtickets.DAL.Users;

import dk.easv.eventtickets.BE.User;

import java.util.List;

public class UserDAO_DB implements IUserDataAccess{
    @Override
    public List<User> getAllUsers() throws Exception {
        return List.of();
    }

    @Override
    public User createUser(User user) throws Exception {
        return null;
    }

    @Override
    public void updateUser(User user) throws Exception {

    }

    @Override
    public void deleteUser(User user) throws Exception {

    }

    @Override
    public List<User> getMoviesForGenre(User user) throws Exception {
        return List.of();
    }
}
