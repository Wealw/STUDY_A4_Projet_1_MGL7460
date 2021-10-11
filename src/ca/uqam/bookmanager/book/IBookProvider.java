package ca.uqam.bookmanager.book;

public interface IBookProvider {
    
    Book createBook(String title, String author, String description, int isbn, int quantity);
    Book[] readAllBook();
    Book readBook(int id);
    void updateBook(int id, String title, String author, String description, int isbn, int quantity);
    void deleteBook(int id);
    Book[] searchBookByTitle(String title);
    Book[] searchBookByAuthor(String author);
    Book[] searchBookByDescription(String description);
    Book[] searchBookByIsbn(int isbn);
    
}
