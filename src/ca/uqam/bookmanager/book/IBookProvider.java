package ca.uqam.bookmanager.book;

public interface IBookProvider
{
    // TODO : Update prototype parameter according to book property
    public void CreateBook();
    public Book[] ReadAllBook();
    public Book ReadBook(int id);
    // TODO : Update prototype parameter according to book property
    public void UpdateBook();
    public void DeleteBook();
    public Book[] SearchBookByTitle();
    public Book[] SearchBookByAuthor();
    public Book[] SearchBookByDescription();

}
