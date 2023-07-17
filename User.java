import java.util.ArrayList;

public class User {
    private String name;
    private String address;
    private String email;
    private String password;


    public User(String name, String town, String email, String password){
        this.name = name;
        this.address = town;
        this.email = email;
        this.password = password;
    }



    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }




}
