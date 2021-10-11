package ca.uqam.bookmanager.book;

public class Book {
    
    private int    id;
    private String title;
    private String author;
    private String description;
    private int    isbn;
    private int    quantity;
    
    public Book(String title, String author, String description, int isbn, int quantity) {
        this.id = -1;
        this.title = title;
        this.author = author;
        this.description = description;
        this.isbn = isbn;
        this.quantity = quantity;
    }
    
    public Book(int id, String title, String author, String description, int isbn, int quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.isbn = isbn;
        this.quantity = quantity;
    }
    
    @Override
    public String toString() {
        String pattern = "\u001B[34mId:\u001B[0m %d \u001B[34mTitle & Author:\u001B[0m \033[3m%s\033[0m by \u001B[1m%s\u001B[0m \u001B[34mQuantity:\u001B[0m %d \u001B[34mISBN:\u001B[0m %d \u001B[34mDescription:\u001B[0m %s";
        if (this.id == -1) {
            return String.format(pattern, null, title, author, quantity, isbn, description);
        }
        return String.format(pattern, id, title, author, quantity, isbn, description);
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getIsbn() {
        return isbn;
    }
    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
