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

        String usersSql = "SELECT UserID, Username, FName, LName, Email FROM dbo.Users";
        String userRolesSql = "SELECT r.RoleName FROM UserRoles ur JOIN Roles r ON ur.RoleId = r.RoleId WHERE ur.UserId = ?";
        try (Connection conn = databaseConnector.getConnection()) {
            try(PreparedStatement userStmt = conn.prepareStatement(usersSql);
                ResultSet userRs = userStmt.executeQuery()){
                while (userRs.next()){
                    User user = new User();
                    user.setId(userRs.getInt("UserID"));
                    user.setUsername(userRs.getString("Username"));
                    user.setFName(userRs.getString("FName"));
                    user.setLName(userRs.getString("LName"));
                    user.setEmail(userRs.getString("Email"));

                    try(PreparedStatement roleStmt = conn.prepareStatement(userRolesSql)){
                        roleStmt.setInt(1, userRs.getInt("UserID"));

                        try(ResultSet roleRs = roleStmt.executeQuery()){
                            List<String> roles = new ArrayList<>();
                            while (roleRs.next()){
                                roles.add(roleRs.getString("RoleName"));
                            }
                            user.setRoles(roles);
                        }
                    }
                    allUsers.add(user);

                }
            }

        }catch (SQLException e){
            e.printStackTrace();
            throw new Exception("Could not get all users" + e.getMessage());
        }
        return allUsers;
    }

    @Override
    public User createUser(User newUser) throws Exception {
        String userSql = "INSERT INTO dbo.Users (Username, PasswordHash, FName, LName, Email) VALUES (?, ?, ?, ?, ?);";

        String userRolessql = "INSERT INTO dbo.UserRoles (UserId, RoleId)" + "SELECT ?, RoleId FROM Roles WHERE RoleName = ?;";

        try (Connection conn = databaseConnector.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {

                //Bind parameters
                stmt.setString(1, newUser.getUsername());
                stmt.setString(2, newUser.getPasswordHash());
                stmt.setString(3, newUser.getFName());
                stmt.setString(4, newUser.getLName());
                stmt.setString(5, newUser.getEmail());

                //Run the SQL statement
                stmt.executeUpdate();

                //Get generated ID
                ResultSet rs = stmt.getGeneratedKeys();
                int userId = 0;
                if (rs.next()) {
                    userId = rs.getInt(1);
                }

                try (PreparedStatement roleStmt = conn.prepareStatement(userRolessql)) {
                    roleStmt.setInt(1, userId);
                    roleStmt.setString(2, newUser.getRole().getRoleName());

                    int rowsAffected = roleStmt.executeUpdate();
                }

                conn.commit();

                User createdUser = new User
                        (userId, newUser.getUsername(), null, newUser.getFName(), newUser.getLName(), newUser.getEmail(), newUser.getRole());
                return createdUser;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                throw new Exception("Could not create User", e);
            }
        }
    }

    @Override
    public void updateUser(User user) throws Exception {

    }

    @Override
    public void deleteUser(User user) throws Exception {
        String DeleteUsersql = "DELETE FROM dbo.Users WHERE ID =?; ";

        try(Connection conn = databaseConnector.getConnection();
        PreparedStatement stmt = conn.prepareStatement(DeleteUsersql)){
            stmt.setInt(1,user.getId());

            stmt.executeUpdate();
        }
        catch (SQLException ex){
            throw new Exception("Could not delete user");
        }
    }

    @Override
    public List<User> getMoviesForGenre(User user) throws Exception {
        return List.of();
    }
}
