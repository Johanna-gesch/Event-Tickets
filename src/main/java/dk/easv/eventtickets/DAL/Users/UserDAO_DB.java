package dk.easv.eventtickets.DAL.Users;

import dk.easv.eventtickets.BE.User;
import dk.easv.eventtickets.DAL.DBConnector;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO_DB implements IUserDataAccess{
    private final DBConnector databaseConnector;

    public UserDAO_DB() throws IOException {
        databaseConnector = new DBConnector();
    }

    @Override
    public List<User> getAllUsers() throws Exception {
        ArrayList<User> allUsers = new ArrayList<>();

        String sql = "SELECT * FROM dbo.Users";
        return null;
    }

    @Override
    public User createUser(User newUser) throws Exception {
        String sql = "INSERT INTO dbo.Users (Username, PasswordHash, FName, LName, Email,  type) VALUES (?, ?, ?, ?, ?, ?);";

        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //Bind parameters
            stmt.setString(1, newUser.getUsername());
            stmt.setString(2, newUser.getPasswordHash());
            stmt.setString(3, newUser.getFName());
            stmt.setString(4, newUser.getLName());
            stmt.setString(5, newUser.getEmail());
            stmt.setString(6, newUser.getType());

            //Run the SQL statement
            stmt.executeUpdate();

            //Get generated ID
            ResultSet rs = stmt.getGeneratedKeys();
            int userId = 0;
            if(rs.next()){
                userId = rs.getInt(1);
            }

            User createdUser = new User
                    (userId, newUser.getUsername(),
                            null,
                            newUser.getFName(),
                            newUser.getLName(),
                            newUser.getEmail(), newUser.getType());

            return createdUser;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Could not create User", e);
        }
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
