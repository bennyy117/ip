package nary;

import nary.exception.NaryException;
import nary.task.Deadline;
import nary.task.Event;
import nary.task.Task;
import nary.task.Todo;
import nary.storage.Storage;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class for the Nary chatbot application.
 * Handles user input, task management, and storage operations.
 */
public class Nary {
    private static final Storage storage = new Storage("data/nary.txt");
    private static ArrayList<Task> tasks = storage.load();
    private static final Scanner sc = new Scanner(System.in);

    /**
     * Program entry point.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        greet();
        while (true) {
            handleInput(sc.nextLine().trim());
        }
    }

    /** Prints the welcome message. */
    private static void greet() {
        printLine();
        System.out.println(" Hello! I'm Nary");
        System.out.println(" What can I do for you?");
        printLine();
    }

    /**
     * Handles user input and executes the corresponding commands.
     *
     * @param input The user input string
     */
    private static void handleInput(String input) {
        try {
            if (input.equals("bye")) {
                exit();
            } else if (input.equals("list")) {
                printTasks();
            } else if (input.startsWith("mark ")) {
                markTask(input);
            } else if (input.startsWith("unmark ")) {
                unmarkTask(input);
            } else if (input.startsWith("todo")) {
                String desc = input.length() > 4 ? input.substring(5).trim() : "";
                if (desc.isEmpty()) {
                    throw new NaryException("OOPS!!! The description of a todo cannot be empty.");
                }
                addTask(new Todo(desc));
            } else if (input.startsWith("deadline ")) {
                String[] parts = input.substring(9).split(" /by ", 2);
                if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                    throw new NaryException("OOPS!!! The description or deadline date cannot be empty.");
                }
                addTask(new Deadline(parts[0].trim(), parts[1].trim())); // expects yyyy-MM-dd
            } else if (input.startsWith("event ")) {
                String[] parts1 = input.substring(6).split(" /from ", 2);
                if (parts1.length < 2) {
                    throw new NaryException("OOPS!!! Event must have from and to dates.");
                }
                String[] parts2 = parts1[1].split(" /to ", 2);
                if (parts2.length < 2) {
                    throw new NaryException("OOPS!!! Event must have both start and end dates.");
                }
                addTask(new Event(parts1[0].trim(), parts2[0].trim(), parts2[1].trim())); // expects yyyy-MM-dd
            } else if (input.startsWith("delete ")) {
                deleteTask(input);
            } else if (input.startsWith("find ")) {
                String keyword = input.substring(5).trim();
                if (keyword.isEmpty()) {
                    throw new NaryException("OOPS!!! The keyword to find cannot be empty.");
                }
                findTasks(keyword);
            } else {
                throw new NaryException("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        } catch (NaryException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Something went wrong! " + e.getMessage());
        }
    }

    /** Saves current task list to storage. */
    private static void saveTasks() {
        storage.save(tasks);
    }

    /**
     * Adds a task to the task list and saves it.
     *
     * @param t The task to add
     */
    private static void addTask(Task t) {
        tasks.add(t);
        printAdded(t);
        saveTasks();
    }

    /**
     * Prints a message indicating the task was added.
     *
     * @param t The task that was added
     */
    private static void printAdded(Task t) {
        printLine();
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + t);
        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        printLine();
    }

    /** Prints all tasks in the task list. */
    private static void printTasks() {
        printLine();
        if (tasks.isEmpty()) {
            System.out.println(" No tasks yet!");
        } else {
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + tasks.get(i));
            }
        }
        printLine();
    }

    /**
     * Marks a task as done based on user input.
     *
     * @param input The user input containing the index to mark
     */
    private static void markTask(String input) {
        try {
            int index = Integer.parseInt(input.split(" ")[1]) - 1;
            tasks.get(index).markAsDone();
            saveTasks();
            printLine();
            System.out.println(" Nice! I've marked this task as done:");
            System.out.println("   " + tasks.get(index));
            printLine();
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println(" Invalid index for mark command!");
        }
    }

    /**
     * Marks a task as not done based on user input.
     *
     * @param input The user input containing the index to unmark
     */
    private static void unmarkTask(String input) {
        try {
            int index = Integer.parseInt(input.split(" ")[1]) - 1;
            tasks.get(index).markAsNotDone();
            saveTasks();
            printLine();
            System.out.println(" OK, I've marked this task as not done yet:");
            System.out.println("   " + tasks.get(index));
            printLine();
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println(" Invalid index for unmark command!");
        }
    }

    /**
     * Deletes a task based on user input.
     *
     * @param input The user input containing the index to delete
     */
    private static void deleteTask(String input) {
        try {
            String[] parts = input.split(" ", 2);
            if (parts.length < 2 || parts[1].trim().isEmpty()) {
                System.out.println(" Invalid command, please provide index to delete.");
                return;
            }
            int idx = Integer.parseInt(parts[1].trim());
            if (idx <= 0 || idx > tasks.size()) {
                System.out.println(" Invalid task: " + idx);
                return;
            }
            Task removed = tasks.remove(idx - 1);
            saveTasks();
            printLine();
            System.out.println(" I've removed this task:");
            System.out.println("   " + removed);
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            printLine();

        } catch (NumberFormatException e) {
            System.out.println(" That isn't a valid number to delete.");
        }
    }

    /**
     * Finds tasks containing the specified keyword.
     *
     * @param keyword The keyword to search for
     */
    private static void findTasks(String keyword) {
        printLine();
        ArrayList<Task> matches = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matches.add(t);
            }
        }
        if (matches.isEmpty()) {
            System.out.println(" No matching tasks found!");
        } else {
            System.out.println(" Here are the matching tasks in your list:");
            for (int i = 0; i < matches.size(); i++) {
                System.out.println(" " + (i + 1) + "." + matches.get(i));
            }
        }
        printLine();
    }

    /** Exits the program gracefully. */
    private static void exit() {
        printLine();
        System.out.println(" Bye. Hope to see you again soon!");
        printLine();
        sc.close();
        System.exit(0);
    }

    /** Prints a divider line. */
    private static void printLine() {
        System.out.println("____________________________________________________________");
    }
}
