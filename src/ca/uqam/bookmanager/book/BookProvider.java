package ca.uqam.bookmanager.book;

public class BookProvider implements IBookProvider
{
    
    @Override
    public Book CreateBook(String title, String author, String description, int isbn, int quantity)
    {
        return null;
    }
    @Override
    public Book[] ReadAllBook()
    {
        // TODO : Write SLQ request
        return new Book[0];
    }
    @Override
    public Book ReadBook(int id)
    {
        // TODO : Write SLQ request
        return null;
    }
    @Override
    public void UpdateBook(String title, String author, String description, int isbn, int quantity)
    {
        // TODO : Write SLQ request
    }
    @Override
    public void DeleteBook(int id)
    {
        // TODO : Write SLQ request
    }
    @Override
    public Book[] SearchBookByTitle()
    {
        // TODO : Write SLQ request
        return new Book[0];
    }
    @Override
    public Book[] SearchBookByAuthor()
    {
        // TODO : Write SLQ request
        return new Book[0];
    }
    @Override
    public Book[] SearchBookByDescription()
    {
        // TODO : Write SLQ request
        return new Book[0];
    }
    
}
