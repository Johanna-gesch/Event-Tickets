package dk.easv.eventtickets.BE;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    private int id;
    private String FName, LName;
    private String email;
    private String username;
    private String passwordHash;
    private String imagePath;
    //private Role role;
    private final List<UserRole> roles = new ArrayList<>();

    //image skal ikke væk fra constructoren
    public User(int id, String username, String passwordHash, String FName, String LName, String email) {
        this.id = id;
        this.FName = FName;
        this.LName= LName;
        this.email = email;
        this.imagePath = imagePath;
        this.username = username;
        this.passwordHash = passwordHash;
        //this.role = role;
    }

    public User(){}

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void addRole(UserRole role) {
        if (role != null && !roles.contains(role)) {
            roles.add(role);
        }
    }

    public boolean hasRole(UserRole role) {
        return roles.contains(role);
    }

    public boolean isAdmin() {
        return hasRole(UserRole.ADMIN);
    }

    public boolean isEventCoordinator() {
        return hasRole(UserRole.EVENT_COORDINATOR);
    }

    public List<UserRole> getRoles() {
        return Collections.unmodifiableList(roles);
    }

    public String getRoleDisplayName() {
        if (roles.isEmpty()) {
            return "Ingen rolle";
        }
        return roles.stream()
                .map(UserRole::getDisplayName)
                .reduce((a, b) -> a + ", " + b)
                .orElse("Ingen rolle");
    }


    public String getFName() {
        return FName;
    }

    public void setFName(String firstName) {
        this.FName = firstName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String lastName) {
        this.LName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String password) {
        this.passwordHash = password;
    }


    public String getImagePath() {
        return imagePath;
    }

}
