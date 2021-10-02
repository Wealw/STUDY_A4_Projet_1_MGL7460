package ca.uqam.bookmanager.book;

public class Book
{
    
    private int    id;
    private String title;
    private String author;
    private String description;
    private int    isbn;
    private int    quantity;
    
    public String ToString()
    {
        String patern = "\u001B[34mId:\u001B[0m %d \u001B[34mTitle & Author:\u001B[0m \033[3m%s\033[0m by \u001B[1m%s\u001B[0m \u001B[34mQuantity:\u001B[0m %d \u001B[34mISBN:\u001B[0m %d \u001B[34mDescription:\u001B[0m %s";
        return String.format(patern, id, title, author, quantity, isbn, description);
    }
    
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public String getAuthor()
    {
        return author;
    }
    public void setAuthor(String author)
    {
        this.author = author;
    }
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public int getIsbn()
    {
        return isbn;
    }
    public void setIsbn(int isbn)
    {
        this.isbn = isbn;
    }
    public int getQuantity()
    {
        return quantity;
    }
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }
    
}
