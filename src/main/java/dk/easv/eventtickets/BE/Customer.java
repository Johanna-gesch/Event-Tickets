package dk.easv.eventtickets.BE;

public class Customer {
    private int id;
    private String FName, LName;
    private String Email;
    private Role role;

    public Customer(int id, String FName, String LName, String Email, Role role){
        this.id = id;
        this.FName = FName;
        this.LName = LName;
        this.Email = Email;
        this.role = role;
    }

    public Customer(){}

    public int getId() {
        return id;
    }
    public void setRole(Role role){this.role = role;}

    public Role getRole (){return role;}

    public void setId(int id) {
        this.id = id;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
