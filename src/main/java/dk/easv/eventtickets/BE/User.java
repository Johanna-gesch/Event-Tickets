package dk.easv.eventtickets.BE;


public class User {
    private int id;
    private String FName, LName;
    private String Email;
    private String type;
    private String username;
    private String passwordHash;
    //private String phoneNumber;
    private String imagePath;

    //image skal ikke væk fra constructoren
    public User(int id, String username, String passwordHash, String FName, String LName, String email, String type) {
        this.id = id;
        this.FName = FName;
        this.LName= LName;
        this.Email = Email;
        this.type = type;
        this.imagePath = imagePath;
        this.username = username;
        this.passwordHash = passwordHash;
        //this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type){
        this.type = type;
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

    /**
    public String getPhoneNumber() {
        return phoneNumber;
    }


    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
     */


    public String getImagePath() {
        return imagePath;
    }


}
