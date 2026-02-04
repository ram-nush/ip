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
        System.out.println("____________________________________________________________\n");

        String command = scanner.nextLine();
        while (!command.equals("bye")) {
            tasks.add(command);

            System.out.println("____________________________________________________________");
            System.out.println("added: " + command);
            System.out.println("____________________________________________________________\n");

            command = scanner.nextLine();
        }

        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");

        scanner.close();
    }
}
