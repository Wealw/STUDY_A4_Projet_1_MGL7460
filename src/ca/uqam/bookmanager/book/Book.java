package ca.uqam.bookmanager.book;

/**
 * Book class.
 */
public class Book {
    
    /**
     * Book id in database.
     */
    private int    id;
    /**
     * Book title.
     */
    private String title;
    /**
     * Book author.
     */
    private String author;
    /**
     * Book description.
     */
    private String description;
    /**
     * Book ISBN.
     */
    private int    isbn;
    /**
     * Quantity of book.
     */
    private int    quantity;
    
    /**
     * @param title       Book title
     * @param author      Book author
     * @param description Book description
     * @param isbn        Book ISBN
     * @param quantity    Quantity of book
     */
    public Book(final String title, final String author, final String description, final int isbn, final int quantity) {
        this.id = -1;
        this.title = title;
        this.author = author;
        this.description = description;
        this.isbn = isbn;
        this.quantity = quantity;
    }
    
    /**
     * @param id          Book id in database
     * @param title       Book title
     * @param author      Book author
     * @param description Book description
     * @param isbn        Book ISBN
     * @param quantity    Quantity of book
     */
    public Book(final int id, final String title, final String author, final String description, final int isbn, final int quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.isbn = isbn;
        this.quantity = quantity;
    }
    
    /**
     * @return String representing the instance of Book class
     */
    @Override
    public String toString() {
        String pattern = "\u001B[34mId:\u001B[0m %d \u001B[34mTitle & Author:\u001B[0m \033[3m%s\033[0m by \u001B[1m%s\u001B[0m \u001B[34mQuantity:\u001B[0m %d \u001B[34mISBN:\u001B[0m %d \u001B[34mDescription:\u001B[0m %s";
        if (this.id == -1) {
            return String.format(pattern, null, title, author, quantity, isbn, description);
        }
        return String.format(pattern, id, title, author, quantity, isbn, description);
    }
    
    /**
     * @return Book id
     */
    public int getId() {
        return id;
    }
    
    /**
     * @param id Book id
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * @return Book title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * @param title Book title
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * @return Book author
     */
    public String getAuthor() {
        return author;
    }
    
    /**
     * @param author Book author
     */
    public void setAuthor(String author) {
        this.author = author;
    }
    
    /**
     * @return Book description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * @param description Book description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * @return Book ISBN
     */
    public int getIsbn() {
        return isbn;
    }
    
    /**
     * @param isbn Book ISBN
     */
    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }
    
    /**
     * @return Quantity of book
     */
    public int getQuantity() {
        return quantity;
    }
    
    /**
     * @param quantity Quantity of book
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
