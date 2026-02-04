import java.util.Scanner;

public class Bmo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm BMO");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________\n");

        String command = scanner.nextLine();
        while (!command.equals("bye")) {
            System.out.println("____________________________________________________________");
            System.out.println(command);
            System.out.println("____________________________________________________________\n");

            command = scanner.nextLine();
        }

        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");

        scanner.close();
    }
}
