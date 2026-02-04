import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Bmo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> tasks = new ArrayList<>();

        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm BMO");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");
        System.out.print("\n");

        String command = scanner.nextLine();
        while (!command.equals("bye")) {
            if (command.equals("list")) {
                System.out.println("____________________________________________________________");
                for (int i = 1; i <= tasks.size(); i++) {
                    System.out.println(i + ". " + tasks.get(i - 1));
                }
                System.out.println("____________________________________________________________");
                System.out.print("\n");
            } else {
                tasks.add(command);

                System.out.println("____________________________________________________________");
                System.out.println("added: " + command);
                System.out.println("____________________________________________________________");
                System.out.print("\n");
            }
            command = scanner.nextLine();
        }

        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");

        scanner.close();
    }
}
