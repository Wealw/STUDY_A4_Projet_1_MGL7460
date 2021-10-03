package ca.uqam.bookmanager;

import ca.uqam.bookmanager.authentication.UserSupervisor;
import ca.uqam.bookmanager.book.BookSupervisor;
import ca.uqam.bookmanager.database.Database;
import ca.uqam.bookmanager.database.IDataSource;

class ApplicationSupervisor
{
    
    public void run()
    {
        IDataSource dataSource = new Database();
        UserSupervisor userSupervisor = new UserSupervisor(dataSource);
        BookSupervisor bookSupervisor = new BookSupervisor(dataSource);
        DisplayApplicationLogo();
        userSupervisor.AuthenticationMenu();
    }
    
    private void DisplayApplicationLogo()
    {
        System.out.println("""
                                    ____              _    __  __                                  \s
                                   |  _ \\            | |  |  \\/  |                                 \s
                                   | |_) | ___   ___ | | _| \\  / | __ _ _ __   __ _  __ _  ___ _ __\s
                                   |  _ < / _ \\ / _ \\| |/ / |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '__|
                                   | |_) | (_) | (_) |   <| |  | | (_| | | | | (_| | (_| |  __/ |  \s
                                   |____/ \\___/ \\___/|_|\\_\\_|  |_|\\__,_|_| |_|\\__,_|\\__, |\\___|_|  \s
                                                                                     __/ |         \s
                                                                                    |___/      \s""".indent(1));
        System.out.println("Welcome to book manager !");
    }
    
}
