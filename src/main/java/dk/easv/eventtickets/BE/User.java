package dk.easv.eventtickets.BE;


public class User {
    private String firstName, lastName;
    private String email;
    private String type;

    private String imagePath;

    //image skal ikke væk fra constructoren
    public User(String firstName, String lastName, String email, String type, String imagePath) {
        this.firstName = firstName;
        this.lastName= lastName;
        this.email = email;
        this.type = type;
        this.imagePath = imagePath;
    }

    public String getFName() {
        return firstName;
    }

    public void setFName(String firstName) {
        this.firstName = firstName;
    }

    public String getLName() { return lastName;}

    public void setLName(String lastName) { this.lastName = lastName;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
