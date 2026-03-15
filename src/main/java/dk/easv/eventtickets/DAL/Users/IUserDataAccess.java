package dk.easv.eventtickets.DAL.Users;

import dk.easv.eventtickets.BE.User;

import java.util.List;

public interface IUserDataAccess {
    List<User> getAllUsers() throws Exception;

    User createUser(User user) throws Exception;

    void updateUser(User user) throws Exception;

    void deleteUser(User user) throws Exception;

    List<User> getMoviesForGenre(User user) throws Exception;
}
