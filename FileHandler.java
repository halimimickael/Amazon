import java.io.*;
import java.util.ArrayList;

public class FileHandler {


    /*
     * Reads user data from a Users.txt file and returns an ArrayList of User objects.
     * Each line in the file should have four comma-separated values: name, address, email, and password.
     * Skips lines that don't contain all four attributes.
     * Handles IOExceptions and returns the ArrayList of User objects from the file.
    */
    public static ArrayList<User> readUsersFromFile(){

        ArrayList<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(Main.USER_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length == 4 ) {
                    String name = data[0];
                    String address = data[1];
                    String email = data[2];
                    String password = data[3];

                    User user = new User(name,address,email,password);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }


    /*
     * Appends a new user registration to the user.txt file containing user data.
     * The method writes a new line in the user.txt file with the provided user's attributes:
     * name, address, email, and password, separated by commas.
     * The new line is added at the end of the file.
     * Handles IOExceptions and prints the stack trace in case of an error during the writing process.
     * user The User object representing the new user to be added to the users.txt file.
     */
    public static void writeNewRegister(User user){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(Main.USER_PATH, true));
            writer.newLine(); // add a new line before writing the new line
            writer.write(user.getName()+","+user.getAddress()+","+user.getEmail()+","+user.getPassword());
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }


    /*
     * Reads book data from a file and returns an ArrayList of Book objects.
     * The file should contain book information in a vertical bar (|) separated format.
     * Each line in the file represents a book with attributes: title, author, price, and ISBN.
     * The method parses the file and creates a new Book object for each valid line with all four attributes present.
     * It checks data length to avoid index out of range errors.
     * Handles IOExceptions and prints the stack trace in case of an error during the reading process.
     * return An ArrayList of Book objects containing the book data from the file.
     */
    public static ArrayList<Book> readBooksFromFile() {
        ArrayList<Book> books = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(Main.FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                if (data.length >= 4) { // Check data length to avoid index out of range errors
                    String title = data[0];
                    String author = data[1];
                    double price = Double.parseDouble(data[2]);
                    String ISBN = data[3];

                    Book book = new Book(title, author, price, ISBN);
                    books.add(book);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return books;
    }


    /*
     * Retrieves the books purchased by the current user from the given file.
     * Reads the file and searches for the user's purchase lines (format: "email,isbn1,isbn2,...").
     * Searches for matching Book objects and adds them to the ArrayList purchasedBooks.
     * Handles IOExceptions.
     *
     * @param filename The name of the file containing users' purchase information.
     * @return An ArrayList of Book objects representing books purchased by the current user.
     */
     public static ArrayList<Book> getBooksPurchasedByUser() {
        ArrayList<Book> purchasedBooks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(Main.PURCHASE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length > 1) {
                    String user = data[0];
                    if (user.equals(LoginManager.user)) {
                        ArrayList<Book> booksInShop = readBooksFromFile();
                        for (int i = 1; i < data.length; i++) {
                            String isbn = data[i];
                            for (Book book : booksInShop) {
                                if (book.getIsbn().equals(isbn)) {
                                    purchasedBooks.add(book);
                                }
                            }
                        }
                        break; // Exit the loop after finding the user's purchases
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return purchasedBooks;
    }



}



