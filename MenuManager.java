import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuManager {
    static String stopColor ="\u001b[0m";
    static String cyan = "\u001b[36m";
    static String red = "\u001B[31m";
    static String orange = "\u001B[38;5;208m";
    static String green = "\u001b[32m";
    private static Scanner scanner;


    public static void printMenu(String[] options) {
        for (String option : options) {
            System.out.println(green + option + stopColor);
        }
        System.out.print( "Choose your option : ");
    }

    public static void Menu() {
        int option = 1;
        System.out.println( "\n|---Welcome to Amazon!---|\n");
        String[] options = {"I - Search for a book",
                            "II - View user details",
                            "III - Purchase a book",
                            "IV - Logout \n"
        };
        scanner = new Scanner(System.in);

        while (option != 4) {
            printMenu(options);
            try {
                option = scanner.nextInt();
                switch (option) {
                    case 1:
                        search();
                        break;
                    case 2:
                        userDetails();
                        break;
                    case 3:
                        addBookToUserPurchase();
                        break;
                    case 4:
                        logOut();
                        break;
                    default:
                        System.out.println(MenuManager.red +"Invalid choice. Please enter a valid option (1-4)."+MenuManager.stopColor);
                        break;
                }
            } catch (Exception ex) {
                System.out.println(red + "Please enter an integer value between 1 and " + options.length);
                scanner.next();
            }
        }
    }


//When the user wants to log out the program launches a choice to log in again or register
    private static void logOut() {
        System.out.println("Thank you " +orange+ LoginManager.name + stopColor+" for your visit to Amazon");
        System.out.println();
        System.out.println("--------------------------------------------------------------");
        System.out.println();
        LoginManager.MenuLogin();
    }


    /*
     * Adds a book to the user's shopping list based on the ISBN provided.
     * If the ISBN is found in Amazon's database, the book is added and a success message is displayed.
     * Otherwise, the user is informed that the book with the ISBN provided is not available.
     * The method handles cases where the user's email already exists or is new in the shopping list.
     * Updated file content is handled with a StringBuilder.
     * Exceptions are handled if there is an error reading or writing to the file.
     */
    public static void addBookToUserPurchase() throws IOException {
        System.out.println("Enter your ISBN:");
        String isbn = scanner.next();
        String email = LoginManager.user;
        boolean isbnFound = Book.isBookFound(isbn);
        if (isbnFound) {
            System.out.println("A book with ISBN " +cyan+ isbn +stopColor+ " was added to the user " + cyan +LoginManager.name +stopColor+ " in the purchase list.");
        } else {
            System.out.println("A book with ISBN " +cyan + isbn + stopColor+ " not in the Amazon Store.");
        }

        String temp = email + "," + isbn;
        StringBuilder updatedFileContent = new StringBuilder();
        boolean emailFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(Main.PURCHASE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(email)) {
                    line += "," + isbn; // Append the new ISBN to the existing line
                    emailFound = true;
                }
                updatedFileContent.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!emailFound) {
            updatedFileContent.append(temp).append(System.lineSeparator());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Main.PURCHASE_PATH))) {
            writer.write(updatedFileContent.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // retrieve the name of the book the user wants to find. if the book is in the store then the program prints the information
    private static void searchBook() throws IOException {
        scanner = new Scanner(System.in);
        System.out.print(orange + "Enter book name: " + stopColor);
        String nameBook = scanner.nextLine();
        ArrayList<Book> bookTitle = FileHandler.readBooksFromFile();
        int count = 0;
        for (int i = 0; i < bookTitle.size(); i++) {
            if (bookTitle.get(i).getTitle().contains(nameBook)) {
                System.out.println(cyan + "TITLE: " + stopColor + bookTitle.get(i).getTitle() + cyan + " AUTHOR:" + stopColor + bookTitle.get(i).getAuthor() + cyan + " Price: " + stopColor + bookTitle.get(i).getPrice() + cyan + " ISBN: " + stopColor + bookTitle.get(i).getIsbn());
                count++;
            }
        }
        if (count == 0){
            System.out.println(red + "sorry, "+ nameBook + " available." + stopColor);
        }
    }


/*a method searchISBN searches against an isbn that the user gave it based on the books.txt file.
in case he finds an isbn compatible with what he has entered then the program prints the information of the book */
    private static void searchISBN() throws IOException {
        System.out.print(orange + "Enter ISBN: " + stopColor);
        String isbn = scanner.next();

        if (!isbn.matches("[0-9]+")) {
            System.out.println(red + "ISBN must only contain numbers." + stopColor);
            return;
        }

        if (isbn.length() < 13) {
            System.out.println(red + "ISBN must be 13 digits long or more." + stopColor);
            return;
        }
        ArrayList<Book> booklist = FileHandler.readBooksFromFile();
        boolean bookFound = false;
        for (int i = 0; i < booklist.size(); i++) {
            Book book = booklist.get(i);
            if (book.getIsbn().equals(isbn)) {
                System.out.println("Book found:");
                System.out.println(cyan + "Title: " + stopColor + book.getTitle());
                System.out.println(cyan + "Author: " + stopColor + book.getAuthor());
                System.out.println(cyan + "ISBN: " + stopColor + book.getIsbn());
                System.out.println(cyan + "Price: " + stopColor + book.getPrice());
                bookFound = true;
                break;
            }
        }
        if (!bookFound) {
            System.out.println(red + "No book found with ISBN: " + isbn+ stopColor);
        }

    }


// user details
    private static void userDetails() {
        ArrayList<User> users = FileHandler.readUsersFromFile();
        String username = LoginManager.user;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(username)) {
                System.out.println(cyan + "Name: " + stopColor + users.get(i).getName() + cyan + "\nEmail: " + stopColor + users.get(i).getEmail() + cyan + "\nAddress:" + stopColor + users.get(i).getAddress() + cyan + "\nPassword: " + stopColor + users.get(i).getPassword());
                System.out.println(cyan + "books already purchased:" + stopColor);
                booksPurchase();
            }
        }
    }


//this method retrieves all the isbn codes that the user has purchased according to the purchase.txt file
    public static void booksPurchase() {
        ArrayList<Book> purchasedBooks = FileHandler.getBooksPurchasedByUser();
        for (int i = 0; i < purchasedBooks.size(); i++) {
            Book book = purchasedBooks.get(i);
            System.out.println(book.getTitle());
        }
    }


    public MenuManager() {
        LoginManager.MenuLogin();
    }


//prints the "search" menu and sends us to the methods according to the user's choice
    public static void search() throws IOException {

        int subOption;
        do {
            System.out.println(orange + "How do you want to search for your book?");
            System.out.println();
            System.out.println(green + "I - By Name"+stopColor);
            System.out.println(green + "II - By ISBN"+stopColor);
            System.out.println(green + "III - All books in Amazon Store"+stopColor);
            try {
                subOption = scanner.nextInt();
                switch (subOption) {
                    case 1:
                        searchBook();
                        break;
                    case 2:
                        searchISBN();
                        break;
                    case 3:
                        allBooks();
                        break;
                    default:
                        System.out.println(red + "Invalid choice. Please enter a valid option (1-3)."+stopColor);
                }
            } catch (Exception ex) {
                System.out.println(red + "Please enter an integer value between 1 and 3"+stopColor);
                scanner.next();
                subOption = 0;
            }
        } while (subOption < 1 || subOption > 3);
    }


    //method that prints all the books
    private static void allBooks() {
        ArrayList<Book> allBooks = FileHandler.readBooksFromFile();
        for(int i = 0; i < allBooks.size(); i++) {
            System.out.println(cyan + "TITLE: " + stopColor + allBooks.get(i).getTitle() + cyan + " AUTHOR:" + stopColor + allBooks.get(i).getAuthor() + cyan + " Price: " + stopColor + allBooks.get(i).getPrice() + cyan + " ISBN: " + stopColor + allBooks.get(i).getIsbn());
        }
    }

}






