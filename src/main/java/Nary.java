import java.util.ArrayList;
import java.util.Scanner;

public class Nary {
    private static ArrayList<String> tasks = new ArrayList<>();

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
                printLine();
                if (tasks.isEmpty()) {
                    System.out.println(" No tasks yet!");
                } else {
                    System.out.println(" Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println(" " + (i + 1) + ". " + tasks.get(i));
                    }
                }
                printLine();
            } else { // add task
                tasks.add(input);
                printLine();
                System.out.println(" added: " + input);
                printLine();
            }
        }

        sc.close();
    }

    private static void printLine() {
        System.out.println("____________________________________________________________");
    }
}
