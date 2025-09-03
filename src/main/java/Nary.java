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
            } else { // new task
                Task t = new Task(input);
                tasks.add(t);
                printLine();
                System.out.println(" added: " + input);
                printLine();
            }
        }

        sc.close();
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
