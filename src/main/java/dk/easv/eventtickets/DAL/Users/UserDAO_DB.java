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
        SELECT u.UserID, u.Username, u.FName, u.LName, u.Email, u.PasswordHash, r.RoleName
        FROM dbo.Users u
        INNER JOIN UserRoles ur ON u.UserID = ur.UserID
        INNER JOIN Roles r ON ur.RoleID = r.RoleID
        ORDER BY u.LName, u.FName
    """;

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            User currentUser = null;
            int previousUserId = -1;

            while (rs.next()) {
                int userId = rs.getInt("UserID");


                if (userId != previousUserId) {
                    currentUser = new User();
                    currentUser.setId(userId);
                    currentUser.setUsername(rs.getString("Username"));
                    currentUser.setFName(rs.getString("FName"));
                    currentUser.setLName(rs.getString("LName"));
                    currentUser.setEmail(rs.getString("Email"));
                    currentUser.setPasswordHash(rs.getString("PasswordHash"));

                    allUsers.add(currentUser);
                    previousUserId = userId;
                }

                // Add role from db to Enum
                String roleName = rs.getString("RoleName");
                if (roleName != null && currentUser != null) {
                    try {
                        // Converts "Admin" → UserRole.ADMIN
                        UserRole role = UserRole.valueOf(roleName.toUpperCase().replace(" ", "_"));
                        currentUser.addRole(role);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Unknown name in db: " + roleName);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Could not get all users", e);
        }

        return allUsers;
    }

    @Override
    public User createUser(User newUser) throws Exception {
        String sql = """
        INSERT INTO dbo.Users 
        (Username, PasswordHash, FName, LName, Email)
        VALUES (?, ?, ?, ?, ?)
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

                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        userId = rs.getInt(1);
                    } else {
                        throw new SQLException("Ingen UserID genereret");
                    }
                }
            }

            // Tilføj alle roller
            if (newUser.getRoles() != null) {
                for (UserRole role : newUser.getRoles()) {
                    addRoleToUser(conn, userId, role);
                }
            }

            conn.commit();

            // Returner brugeren med roller
            User created = new User();
            created.setId(userId);
            created.setUsername(newUser.getUsername());
            created.setPasswordHash(null);   // sikkerhed
            created.setFName(newUser.getFName());
            created.setLName(newUser.getLName());
            created.setEmail(newUser.getEmail());

            for (UserRole role : newUser.getRoles()) {
                created.addRole(role);
            }

            return created;
        } catch (Exception e) {
            throw new Exception("Cant create User", e);
        }
    }

    @Override
    public void updateUser(User user) throws Exception {

    }

    @Override
    public void deleteUser(User user) throws Exception {
        String deleteUserSql = "DELETE FROM dbo.Users WHERE UserID =?; ";
        String deleteUserRolesSql = "DELETE FROM dbo.UserRoles WHERE UserId = ?;";

        try (Connection conn = databaseConnector.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement roleStmt = conn.prepareStatement(deleteUserRolesSql);
                 PreparedStatement userStmt = conn.prepareStatement(deleteUserSql)) {

                roleStmt.setInt(1, user.getId());
                roleStmt.executeUpdate();

                userStmt.setInt(1, user.getId());
                int rowsAffected = userStmt.executeUpdate();

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


    private void addRoleToUser(Connection conn, int userId, UserRole role) throws SQLException {
        String sql = "INSERT INTO UserRoles (UserID, RoleID) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, getRoleId(role));   // se nedenfor
            stmt.executeUpdate();
        }
    }

    private int getRoleId(UserRole role) {
        return switch (role) {
            case ADMIN -> 1;
            case EVENT_COORDINATOR -> 2;
        };
    }
}
