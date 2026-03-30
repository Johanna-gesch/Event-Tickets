package dk.easv.eventtickets.DAL.Users;

import dk.easv.eventtickets.BE.User;
import dk.easv.eventtickets.BE.UserRole;
import dk.easv.eventtickets.DAL.DBConnector;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO_DB implements IUserDataAccess {
    private final DBConnector databaseConnector;

    public UserDAO_DB() throws IOException {
        databaseConnector = new DBConnector();
    }

    @Override
    public List<User> getAllUsers() throws Exception {
        List<User> allUsers = new ArrayList<>();

        String sql = """
        SELECT UserID, Username, FName, LName, Email, PasswordHash, Role
        FROM dbo.Users
        ORDER BY LName, FName
        """;

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                User user = new User();
                user.setId(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setFName(rs.getString("FName"));
                user.setLName(rs.getString("LName"));
                user.setEmail(rs.getString("Email"));
                user.setPasswordHash(rs.getString("PasswordHash"));
                user.setRole(UserRole.valueOf(rs.getString("Role").toUpperCase().replace(" ", "_")));

                allUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Could not get all Users", e);
        }

        return allUsers;
    }


    @Override
    public User createUser(User newUser) throws Exception {
        String sql = """
                INSERT INTO dbo.Users
                (Username, PasswordHash, FName, LName, Email, Role)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = databaseConnector.getConnection()) {
            conn.setAutoCommit(false);

            int userId;

            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, newUser.getUsername());
                stmt.setString(2, newUser.getPasswordHash());
                stmt.setString(3, newUser.getFName());
                stmt.setString(4, newUser.getLName());
                stmt.setString(5, newUser.getEmail());

                stmt.setString(6, String.valueOf(newUser.getRole()));

                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        userId = rs.getInt(1);
                    } else {
                        throw new SQLException("Ingen UserID genereret");
                    }
                }
            }

            conn.commit();

            User created = new User();
            created.setId(userId);
            created.setUsername(newUser.getUsername());
            created.setPasswordHash(null);   // sikkerhed
            created.setFName(newUser.getFName());
            created.setLName(newUser.getLName());
            created.setEmail(newUser.getEmail());
            created.setRole(newUser.getRole());

            return created;

        } catch (Exception e) {
            throw new Exception("Can't create User", e);
        }
    }


    @Override
    public void updateUser(User user) throws Exception {


    }

    @Override
    public void deleteUser(User user) throws Exception {
        String sql = "DELETE FROM dbo.Users WHERE UserID = ?;";

        try (Connection conn = databaseConnector.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, user.getId());
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected == 0) {
                    conn.rollback();
                    throw new Exception("Could not delete user: no user found with ID " + user.getId());
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new Exception("Could not delete user: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public List<User> getMoviesForGenre(User user) throws Exception {
        return List.of();
    }

    public List<User> getAllCoordinators() throws Exception {
        List<User> allCoordinators = new ArrayList<>();

        String sql = "SELECT UserID, FName, LName, Email FROM Users WHERE Role = 'Event Coordinator'";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("UserID"));
                u.setFName(rs.getString("FName"));
                u.setLName(rs.getString("LName"));
                u.setEmail(rs.getString("Email"));
                u.setRole(UserRole.EVENT_COORDINATOR);
                allCoordinators.add(u);
            }
        }
        return allCoordinators;
    }

}
