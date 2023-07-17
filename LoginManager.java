import java.util.ArrayList;
import java.util.Scanner;

public class LoginManager {

    public static String user = "";
    public static String name = "";

    public static void login() {
        // Prompt the user to enter their email
        Scanner in = new Scanner(System.in);
        System.out.print("Enter your email: ");
        // Prompt the user to enter their password
        String email = in.next();
        user = email;
        System.out.print("Enter your password: ");
        String password = in.next();
        // Call the validatePassword method with the entered email and password
        validatePassword(email,password);

    }

    public static User validatePassword(String email, String password){
        String red = "\u001B[31m";
        String reset = "\u001B[0m";

        ArrayList<User> users = FileHandler.readUsersFromFile();

        User authenticatedUser = null;

        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                authenticatedUser = user;
                name = authenticatedUser.getName();
                break;
            }
        }

        if (authenticatedUser == null) {
            System.out.println(red + "Invalid email or password." + reset);
            System.out.println(" ");
            MenuLogin();
        }
        return authenticatedUser;
    }

    public static void MenuLogin(){
        System.out.println("I - Login");
        System.out.println("II - Register");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        switch (choice) {
            case 1:
                LoginManager.login();
                MenuManager.Menu();
                break;
            case 2:
                register();
                LoginManager.login();
                MenuManager.Menu();
                break;
            default:
                System.out.println(MenuManager.red +"Invalid choice. Please enter a valid option (1 or 2)."+MenuManager.stopColor);
                MenuLogin(); // Recursive call to prompt for a valid choice again.
                break;
        }
    }
    public static void register() {
        Scanner scanner = new Scanner(System.in);
        String name;
        String address;
        String mail;
        String password;

        boolean isSameMail = true;
        while (isSameMail) {
            System.out.print("Enter your Name: ");
            name = scanner.nextLine();

            System.out.print("Enter your Address: ");
            address = scanner.nextLine();

            System.out.print("Enter your Mail: ");
            mail = scanner.nextLine();

            System.out.print("Enter your Password: ");
            password = scanner.nextLine();

            ArrayList<User> users = FileHandler.readUsersFromFile();
            int count = 0;
            for (User user : users) {
                if (user.getEmail().equals(mail)) {
                    count++;
                }
            }

            if (count > 0) {
                System.out.println(MenuManager.red+"This email address is already taken! Please choose a different one."+MenuManager.stopColor);
            } else {
                isSameMail = false;
            }

            User user = new User(name,address,mail,password);
            FileHandler.writeNewRegister(user);
        }
        System.out.println(MenuManager.cyan+"You have successfully registered! Welcome to Amazon!"+MenuManager.stopColor);
    }

}