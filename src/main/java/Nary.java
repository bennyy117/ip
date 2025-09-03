import java.util.Scanner;

public class Nary {
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
            } else {
                printLine();
                System.out.println(" " + input);
                printLine();
            }
        }
        sc.close();
    }

    private static void printLine() {
        System.out.println("____________________________________________________________");
    }
}
