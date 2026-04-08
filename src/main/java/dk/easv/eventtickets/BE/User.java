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
    private UserRole role;

    //image skal ikke væk fra constructoren
    public User(int id, String username, String passwordHash, String FName, String LName, String email, UserRole role) {
        this.id = id;
        this.FName = FName;
        this.LName= LName;
        this.email = email;
        this.imagePath = imagePath;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public User(){}

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public boolean isAdmin() {
        return role == UserRole.ADMIN;
    }

    public boolean isEventCoordinator() {
        return role == UserRole.EVENT_COORDINATOR;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) { this.role = role;}

    public String getRoleDisplayName() {
        return role.getDisplayName();
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

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return FName + " " + LName;
    }

}
