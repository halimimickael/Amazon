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

}





