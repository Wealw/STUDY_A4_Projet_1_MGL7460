package ca.uqam.bookmanager;

import ca.uqam.bookmanager.authentication.User;
import ca.uqam.bookmanager.authentication.UserSupervisor;
import ca.uqam.bookmanager.book.BookSupervisor;
import ca.uqam.bookmanager.database.Database;
import ca.uqam.bookmanager.database.IDataSource;

import java.util.Scanner;

class ApplicationSupervisor extends Supervisor
{
    public void run()
    {
        IDataSource dataSource = new Database();
        UserSupervisor userSupervisor = new UserSupervisor(dataSource);
        BookSupervisor bookSupervisor = new BookSupervisor(dataSource);
        DisplayApplicationLogo();
        User user = userSupervisor.AuthenticationMenu();
        AppAction userAction = null;
        while (userAction != AppAction.QUIT){
            DisplayAppAction();
            userAction = HandleAppAction();
            if (userAction == AppAction.USER) userSupervisor.UserMenu(user);
            if (userAction == AppAction.BOOK) bookSupervisor.BookMenu(user.getRole());
        }
    }
    
    private void DisplayApplicationLogo()
    {
        System.out.println("""
                                   \033[1;35m ____              _    __  __                                  \s
                                   |  _ \\            | |  |  \\/  |                                 \s
                                   | |_) | ___   ___ | | _| \\  / | __ _ _ __   __ _  __ _  ___ _ __\s
                                   |  _ < / _ \\ / _ \\| |/ / |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '__|
                                   | |_) | (_) | (_) |   <| |  | | (_| | | | | (_| | (_| |  __/ |  \s
                                   |____/ \\___/ \\___/|_|\\_\\_|  |_|\\__,_|_| |_|\\__,_|\\__, |\\___|_|  \s
                                                                                     __/ |         \s
                                                                                    |___/      \u001B[0m\s""".indent(1));
        System.out.println("Welcome to book manager !");
        System.out.println();
    }
    
    private void DisplayAppAction(){
        System.out.println("\033[1;34mPlease, select one of the following options :\u001B[0m");
        System.out.println("\u001B[34m(1)\u001B[0m User");
        System.out.println("\u001B[34m(2)\u001B[0m Book");
        System.out.println("\u001B[34m(0)\u001B[0m Leave application");
    }
    
    private AppAction HandleAppAction(){
        try{
            return AppAction.values()[Integer.parseInt(this.scanner.nextLine())];
        }catch (Exception e){
            return null;
        }
    }
}
