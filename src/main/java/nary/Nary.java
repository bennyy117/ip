package nary;

import nary.exception.NaryException;
import nary.storage.Storage;
import nary.task.Deadline;
import nary.task.Event;
import nary.task.Task;
import nary.task.Todo;
import nary.ui.UI;

import java.util.ArrayList;

/**
 * Main class for the Nary chatbot application.
 * Handles user input, task management, and storage operations.
 */
public class Nary {

    private static final Storage storage = new Storage("data/nary.txt");
    private static ArrayList<Task> tasks = storage.load();
    private static final UI ui = new UI();

    /**
     * Program entry point.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        ui.showWelcome();

        while (true) {
            String input = ui.readCommand().trim();
            handleInput(input);
        }
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
                handleTodo(input);
            } else if (input.startsWith("deadline ")) {
                handleDeadline(input);
            } else if (input.startsWith("event ")) {
                handleEvent(input);
            } else if (input.startsWith("delete ")) {
                deleteTask(input);
            } else if (input.startsWith("find ")) {
                findTasks(input.substring(5).trim());
            } else if (input.equals("help")) {
                showHelp();
            } else {
                throw new NaryException("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        } catch (NaryException e) {
            ui.showMessage(e.getMessage());
        } catch (Exception e) {
            ui.showMessage("Something went wrong! " + e.getMessage());
        }
    }

    //================ Task Handlers ===================

    /**
     * Handles a todo command and adds a new Todo task.
     *
     * @param input The full user input
     * @throws NaryException If the description is empty
     */
    private static void handleTodo(String input) throws NaryException {
        String desc = input.length() > 4 ? input.substring(5).trim() : "";
        if (desc.isEmpty()) {
            throw new NaryException("OOPS!!! The description of a todo cannot be empty.");
        }
        addTask(new Todo(desc));
    }

    /**
     * Handles a deadline command and adds a new Deadline task.
     *
     * @param input The full user input
     * @throws NaryException If the description or date is empty or extra details provided
     */
    private static void handleDeadline(String input) throws NaryException {
        if (input.contains("/from") || input.contains("/to")) {
            throw new NaryException("OOPS!!! More details than expected were input.");
        }
        String[] parts = input.substring(9).split(" /by ", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new NaryException("OOPS!!! The description or deadline date cannot be empty.");
        }
        addTask(new Deadline(parts[0].trim(), parts[1].trim()));
    }

    /**
     * Handles an event command and adds a new Event task.
     *
     * @param input The full user input
     * @throws NaryException If start or end dates are missing
     */
    private static void handleEvent(String input) throws NaryException {
        String[] parts1 = input.substring(6).split(" /from ", 2);
        if (parts1.length < 2) {
            throw new NaryException("OOPS!!! Event must have from and to dates.");
        }
        String[] parts2 = parts1[1].split(" /to ", 2);
        if (parts2.length < 2) {
            throw new NaryException("OOPS!!! Event must have both start and end dates.");
        }
        addTask(new Event(parts1[0].trim(), parts2[0].trim(), parts2[1].trim()));
    }

    //================ Task Operations ===================

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
     * Prints a message indicating a task was added.
     *
     * @param t The task that was added
     */
    private static void printAdded(Task t) {
        ui.showMessage("Got it. I've added this task:\n   " + t
                + "\nNow you have " + tasks.size() + " tasks in the list.");
    }

    /**
     * Prints all tasks in the task list.
     */
    private static void printTasks() {
        if (tasks.isEmpty()) {
            ui.showMessage("No tasks yet!");
        } else {
            StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                sb.append(" ").append(i + 1).append(".").append(tasks.get(i)).append("\n");
            }
            ui.showMessage(sb.toString().trim());
        }
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
            ui.showMessage("Nice! I've marked this task as done:\n   " + tasks.get(index));
        } catch (Exception e) {
            ui.showMessage("Invalid index for mark command!");
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
            ui.showMessage("OK, I've marked this task as not done yet:\n   " + tasks.get(index));
        } catch (Exception e) {
            ui.showMessage("Invalid index for unmark command!");
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
                ui.showMessage("Invalid command, please provide index to delete.");
                return;
            }
            int idx = Integer.parseInt(parts[1].trim());
            if (idx <= 0 || idx > tasks.size()) {
                ui.showMessage("Invalid task: " + idx);
                return;
            }
            Task removed = tasks.remove(idx - 1);
            saveTasks();
            ui.showMessage("I've removed this task:\n   " + removed
                    + "\nNow you have " + tasks.size() + " tasks in the list.");
        } catch (NumberFormatException e) {
            ui.showMessage("That isn't a valid number to delete.");
        }
    }

    /**
     * Finds tasks containing the specified keyword.
     *
     * @param keyword The keyword to search for
     */
    private static void findTasks(String keyword) {
        ArrayList<Task> matches = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matches.add(t);
            }
        }

        if (matches.isEmpty()) {
            ui.showMessage("No matching tasks found!");
        } else {
            StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
            for (int i = 0; i < matches.size(); i++) {
                sb.append(" ").append(i + 1).append(".").append(matches.get(i)).append("\n");
            }
            ui.showMessage(sb.toString().trim());
        }
    }

    //================ Storage ===================

    /**
     * Saves the current task list to storage.
     */
    private static void saveTasks() {
        storage.save(tasks);
    }

    //================ Exit ===================

    /**
     * Exits the program gracefully.
     */
    private static void exit() {
        ui.showExit();
        ui.close();
        System.exit(0);
    }

    //================ Help ===================

    /**
     * Displays a summary of all available commands along with examples.
     */
    private static void showHelp() {
        String helpMessage = ""
                + "Nary Command Guide:\n"
                + "1. todo TASK_NAME\n"
                + "   - Add a todo task\n"
                + "   Example: todo read book\n\n"
                + "2. deadline TASK_NAME /by DATE\n"
                + "   - Add a deadline task (DATE format: yyyy-MM-dd)\n"
                + "   Example: deadline submit report /by 2025-10-10\n\n"
                + "3. event TASK_NAME /from START_DATE /to END_DATE\n"
                + "   - Add an event task (DATE format: yyyy-MM-dd)\n"
                + "   Example: event project meeting /from 2025-10-07 /to 2025-10-08\n\n"
                + "4. list\n"
                + "   - Show all tasks\n"
                + "   Example: list\n\n"
                + "5. mark TASK_NUMBER\n"
                + "   - Mark task as done\n"
                + "   Example: mark 2\n\n"
                + "6. unmark TASK_NUMBER\n"
                + "   - Mark task as not done\n"
                + "   Example: unmark 2\n\n"
                + "7. delete TASK_NUMBER\n"
                + "   - Delete a task\n"
                + "   Example: delete 3\n\n"
                + "8. find KEYWORD\n"
                + "   - Search tasks by keyword\n"
                + "   Example: find project\n\n"
                + "9. help\n"
                + "   - Show this help message\n"
                + "   Example: help\n\n"
                + "10. bye\n"
                + "   - Exit the program\n"
                + "   Example: bye\n";
        ui.showMessage(helpMessage);
    }
}
