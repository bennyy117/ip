import java.util.ArrayList;
import java.util.Scanner;

public class Nary {
    private static ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        printLine();
        System.out.println(" Hello! I'm Nary");
        System.out.println(" What can I do for you?");
        printLine();

        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine().trim();
            if (input.equals("bye")) {
                printLine();
                System.out.println(" Bye. Hope to see you again soon!");
                printLine();
                break;
            } else if (input.equals("list")) {
                printTasks();
            } else if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.split(" ")[1]) - 1;
                tasks.get(index).markAsDone();
                printLine();
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   " + tasks.get(index));
                printLine();
            } else if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.split(" ")[1]) - 1;
                tasks.get(index).markAsNotDone();
                printLine();
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   " + tasks.get(index));
                printLine();
            } else if (input.startsWith("todo ")) {
                String desc = input.substring(5);
                Task t = new Todo(desc);
                tasks.add(t);
                printAdded(t);
            } else if (input.startsWith("deadline ")) {
                String[] parts = input.substring(9).split(" /by ", 2);
                Task t = new Deadline(parts[0], parts.length > 1 ? parts[1] : "");
                tasks.add(t);
                printAdded(t);
            } else if (input.startsWith("event ")) {
                String[] parts1 = input.substring(6).split(" /from ", 2);
                String desc = parts1[0];
                String[] parts2 = parts1[1].split(" /to ", 2);
                Task t = new Event(desc, parts2[0], parts2[1]);
                tasks.add(t);
                printAdded(t);
            } else {
                Task t = new Todo(input);
                tasks.add(t);
                printAdded(t);
            }
        }

        sc.close();
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

    private static void printLine() {
        System.out.println("____________________________________________________________");
    }
}

//cmd for I/O files:
//javac src/main/java/*.java
//java -cp src/main/java Nary < text-ui-test/input.txt > text-ui-test/output.txt