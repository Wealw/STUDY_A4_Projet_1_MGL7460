package ca.uqam.bookmanager.book;

import ca.uqam.bookmanager.authentication.UserRole;

import java.util.Scanner;

public class BookSupervisor
{
    
    private Scanner scanner = new Scanner(System.in);
    
    public void BookMenu(UserRole userRole)
    {
        BookHomeAction action = null;
        while (action != BookHomeAction.QUIT)
        {
            DisplayBookMenuOption(userRole);
            action = HandleBookMenuOption(userRole);
            if (action == BookHomeAction.DISPLAY_ALL)
                DisplayAllBook();
            if (action == BookHomeAction.SEARCH)
                SearchMenu();
            if (action == BookHomeAction.CREATE)
                CreateMenu();
            if (action == BookHomeAction.UPDATE)
                UpdateMenu();
            if (action == BookHomeAction.DELETE)
                DeleteMenu();
        }
    }
    
    private void DisplayBookMenuOption(UserRole role)
    {
        System.out.println("Please select one of the following :");
        System.out.println("(1) Display all books");
        System.out.println("(2) Search for books");
        if (role == UserRole.LIBRARIAN || role == UserRole.ADMINISTRATOR)
        {
            System.out.println("(3) Add a new book ");
            System.out.println("(4) Update a book");
            System.out.println("(5) Delete a book");
        }
        System.out.println("(0) Leave");
    }
    
    private BookHomeAction HandleBookMenuOption(UserRole role)
    {
        BookHomeAction action;
        try
        {
            action = BookHomeAction.values()[Integer.parseInt(this.scanner.nextLine())];
            if (!(role == UserRole.LIBRARIAN || role == UserRole.ADMINISTRATOR) && (action == BookHomeAction.CREATE || action == BookHomeAction.UPDATE || action == BookHomeAction.DELETE))
            {
                return null;
            }
            return action;
        }
        catch (Exception e)
        {
            return null;
        }
    }
    
    private void DisplayAllBook()
    {
    
    }
    
    private void SearchMenu()
    {
    
    }
    
    private void CreateMenu()
    {
    
    }
    
    private void UpdateMenu()
    {
    
    }
    
    private void DeleteMenu()
    {
    
    }
    
}
