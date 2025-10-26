package nary.ui;

import java.util.Scanner;

/**
 * Handles all user interactions for the Nary chatbot.
 * Responsible for printing messages, reading user input, and showing dividers.
 */
public class UI {

    private final Scanner sc;

    /**
     * Constructs a new UI instance.
     */
    public UI() {
        sc = new Scanner(System.in);
    }

    /**
     * Prints the welcome message when the program starts.
     */
    public void showWelcome() {
        printLine();
        System.out.println(" Hello! I'm Nary");
        System.out.println(" What can I do for you?");
        printLine();
    }

    /**
     * Prints the exit message when the program ends.
     */
    public void showExit() {
        printLine();
        System.out.println(" Bye. Hope to see you again soon!");
        printLine();
    }

    /**
     * Reads the next line of user input from the console.
     *
     * @return The input string entered by the user
     */
    public String readCommand() {
        return sc.nextLine();
    }

    /**
     * Prints a message to the console with dividers before and after.
     *
     * @param message The message to be displayed
     */
    public void showMessage(String message) {
        printLine();
        System.out.println(message);
        printLine();
    }

    /**
     * Prints a divider line to separate sections of output.
     */
    public void printLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Closes the Scanner to release resources.
     */
    public void close() {
        sc.close();
    }
}
