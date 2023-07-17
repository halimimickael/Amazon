import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileHandler {
    public static String mail = "";

    public static ArrayList<User> readUsersFromFile(String filename){

        ArrayList<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
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

     public static ArrayList<Book> getBooksPurchasedByUser(String filename) {
        ArrayList<Book> purchasedBooks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length > 1) {
                    String user = data[0];
                    mail = user;
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



