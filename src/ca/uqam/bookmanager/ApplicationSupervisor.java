package ca.uqam.bookmanager;

import ca.uqam.bookmanager.authentication.User;
import ca.uqam.bookmanager.authentication.UserSupervisor;
import ca.uqam.bookmanager.book.BookSupervisor;
import ca.uqam.bookmanager.database.Database;
import ca.uqam.bookmanager.database.IDataSource;

/**
 * Displays and handle logic behind application entry point.
 */
@SuppressWarnings ("PMD.SystemPrintln")
class ApplicationSupervisor extends AbstractSupervisor {
    
    /**
     * Datasource
     */
    private final IDataSource    dataSource;
    /**
     * User supervisor
     */
    private final UserSupervisor userSupervisor;
    /**
     * Book supervisor
     */
    private final BookSupervisor bookSupervisor;
    /**
     * User
     */
    private User user;
    
    /**
     * Define basic attribute of the application supervisor
     */
    public ApplicationSupervisor() {
        super();
        this.dataSource = new Database();
        this.userSupervisor = new UserSupervisor(dataSource);
        this.bookSupervisor = new BookSupervisor(dataSource);
    }
    
    /**
     * Run the application.
     */
    public void run() {
        displayApplicationLogo();
        user = userSupervisor.authenticationMenu();
        AppAction userAction = null;
        while (userAction != AppAction.QUIT) {
            displayAppAction();
            userAction = handleAppAction();
            if (userAction == AppAction.USER) {
                userSupervisor.userMenu(user);
            }
            if (userAction == AppAction.BOOK) {
                bookSupervisor.bookMenu(user.getRole());
            }
        }
    }
    
    /**
     * Display the app logo to the user.
     */
    private void displayApplicationLogo() {
        System.out.println(("\u001B[1;35m ____              _    __  __                                   %n" + "|  _ \\            | |  |  \\/  |                                  %n" + "| |_) | ___   ___ | | _| \\  / | __ _ _ __   __ _  __ _  ___ _ __ %n" + "|  _ < / _ \\ / _ \\| |/ / |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '__|%n" + "| |_) | (_) | (_) |   <| |  | | (_| | | | | (_| | (_| |  __/ |   %n" + "|____/ \\___/ \\___/|_|\\_\\_|  |_|\\__,_|_| |_|\\__,_|\\__, |\\___|_|   %n" + "                                                  __/ |          %n" + "                                                 |___/      \u001B[0m ").formatted().indent(1));
        System.out.println("Welcome to book manager !");
        System.out.println();
    }
    
    /**
     * Display the option avaliable to the user.
     */
    private void displayAppAction() {
        System.out.println("\033[1;34mPlease, select one of the following options :\u001B[0m");
        System.out.println("\u001B[34m(1)\u001B[0m User");
        System.out.println("\u001B[34m(2)\u001B[0m Book");
        System.out.println("\u001B[34m(0)\u001B[0m Leave application");
    }
    
    /**
     * Handle the user input after an action display.
     *
     * @return User choice or null
     */
    private AppAction handleAppAction() {
        try {
            return AppAction.values()[Integer.parseInt(this.getScanner()
                                                           .nextLine())];
        } catch (Exception e) {
            return null;
        }
    }
}
