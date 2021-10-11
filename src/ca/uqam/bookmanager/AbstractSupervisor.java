package ca.uqam.bookmanager;

import java.util.Scanner;

/**
 * Abstract class that bring user input management.
 */
public abstract class AbstractSupervisor {
    
    /**
     * A scanner instance used to manage user inputs.
     */
    private final Scanner scanner;
    
    /**
     * Define the attribute of the AbstractSupervisor class.
     */
    protected AbstractSupervisor() {
        scanner = new Scanner(System.in);
    }
    
    /**
     * @return Scanner attribute
     */
    protected Scanner getScanner() {
        return scanner;
    }
}
