package ca.uqam.bookmanager;

import java.util.Scanner;

public abstract class AbstractSupervisor {
    
    protected final Scanner scanner;
    
    protected AbstractSupervisor() {scanner = new Scanner(System.in);}
    
}
