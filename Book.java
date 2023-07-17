import java.util.ArrayList;

class Book {
    private String title;
    private String author;
    private double price;
    private String isbn;

    public Book(String title, String author, double price, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }


    public String getAuthor() {
        return author;
    }


    public double getPrice() {
        return price;
    }


    public String getIsbn() {
        return isbn;
    }

    public static boolean isBookFound(String isbn){
        ArrayList<Book> books = FileHandler.readBooksFromFile();
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getIsbn().equals(isbn)) return true;
        }
        return false;
    }
}





