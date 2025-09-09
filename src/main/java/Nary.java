import java.util.ArrayList;
import java.util.Scanner;

public class Nary {
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        greet();
        while (true) {
            handleInput(sc.nextLine().trim());
        }
    }

    private static void greet() {
        printLine();
        System.out.println(" Hello! I'm Nary");
        System.out.println(" What can I do for you?");
        printLine();
    }

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
                if (parts[0].trim().isEmpty() || parts.length < 2 || parts[1].trim().isEmpty()) {
                    throw new NaryException("OOPS!!! The description or deadline time cannot be empty.");
                }
                addTask(new Deadline(parts[0].trim(), parts[1].trim()));
            } else if (input.startsWith("event ")) {
                String[] parts1 = input.substring(6).split(" /from ", 2);
                if (parts1.length < 2) {
                    throw new NaryException("OOPS!!! Event must have /from and /to.");
                }
                String[] parts2 = parts1[1].split(" /to ", 2);
                if (parts2.length < 2) {
                    throw new NaryException("OOPS!!! Event must have both start and end time.");
                }
                addTask(new Event(parts1[0].trim(), parts2[0].trim(), parts2[1].trim()));
            } else {
                throw new NaryException("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        } catch (NaryException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Something went wrong! " + e.getMessage());
        }
    }

    private static void addTask(Task t) {
        tasks.add(t);
        printAdded(t);
    }

    private static void printAdded(Task t) {
        printLine();
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + t);
        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        printLine();
    }

    private static void printTasks() {
        printLine();
        if (tasks.isEmpty()) System.out.println(" No tasks yet!");
        else {
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + tasks.get(i));
            }
        }
        printLine();
    }

    private static void markTask(String input) {
        try {
            int index = Integer.parseInt(input.split(" ")[1]) - 1;
            tasks.get(index).markAsDone();
            printLine();
            System.out.println(" Nice! I've marked this task as done:");
            System.out.println("   " + tasks.get(index));
            printLine();
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println(" Invalid index for mark command!");
        }
    }

    private static void unmarkTask(String input) {
        try {
            int index = Integer.parseInt(input.split(" ")[1]) - 1;
            tasks.get(index).markAsNotDone();
            printLine();
            System.out.println(" OK, I've marked this task as not done yet:");
            System.out.println("   " + tasks.get(index));
            printLine();
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println(" Invalid index for unmark command!");
        }
    }

    private static void exit() {
        printLine();
        System.out.println(" Bye. Hope to see you again soon!");
        printLine();
        sc.close();
        System.exit(0);
    }

    private static void printLine() {
        System.out.println("____________________________________________________________");
    }
}


//cmd for I/O files:
//javac src/main/java/*.java
//java -cp src/main/java Nary < text-ui-test/input.txt > text-ui-test/output.txt