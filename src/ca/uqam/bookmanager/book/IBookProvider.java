package ca.uqam.bookmanager.book;

public interface IBookProvider
{
    Book CreateBook(String title, String author, String description, int isbn, int quantity);
    Book[] ReadAllBook();
    Book ReadBook(int id);
    void UpdateBook(int id,String title, String author, String description, int isbn, int quantity);
    void DeleteBook(int id);
    Book[] SearchBookByTitle(String title);
    Book[] SearchBookByAuthor(String author);
    Book[] SearchBookByDescription(String description);
    Book[] SearchBookByIsbn(int isbn);
    
}
