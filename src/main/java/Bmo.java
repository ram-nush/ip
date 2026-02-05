import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Bmo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Task> tasks = new ArrayList<Task>();

        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm BMO");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");
        System.out.print("\n");

        String userInput = scanner.nextLine();
        String[] parameters = userInput.split(" ");
        String command = parameters[0];
        while (!command.equals("bye")) {
            switch (command) {
            case "list":
                System.out.println("____________________________________________________________");
                System.out.println("Here are the tasks in your list:");
                for (int i = 1; i <= tasks.size(); i++) {
                    System.out.println(i + ". " + tasks.get(i - 1));
                }
                System.out.println("____________________________________________________________");
                System.out.print("\n");
                break;

            case "todo":
                String todoDescription = "";
                for (int i = 1; i < parameters.length; i++) {
                    todoDescription += parameters[i];
                    if (i != parameters.length - 1) {
                        todoDescription += " ";
                    }
                }
                Task todoTask = new Todo(todoDescription);
                tasks.add(todoTask);

                System.out.println("____________________________________________________________");
                System.out.println("Got it. I've added this task:");
                System.out.println(todoTask);
                System.out.println("____________________________________________________________");
                System.out.print("\n");
                break;

            case "mark":
                int markTaskNo = Integer.parseInt(parameters[1]);
                if (markTaskNo >= 1 && markTaskNo <= tasks.size()) {
                    Task markTask = tasks.get(markTaskNo - 1);
                    markTask.markAsDone();

                    System.out.println("____________________________________________________________");
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(markTask);
                    System.out.println("____________________________________________________________");
                    System.out.print("\n");
                } else {
                    System.out.println("____________________________________________________________");
                    System.out.println("No task with index " + markTaskNo + " exists!");
                    System.out.println("____________________________________________________________");
                    System.out.print("\n");
                }
                break;

            case "unmark":
                int unmarkTaskNo = Integer.parseInt(parameters[1]);
                if (unmarkTaskNo >= 1 && unmarkTaskNo <= tasks.size()) {
                    Task unmarkTask = tasks.get(unmarkTaskNo - 1);
                    unmarkTask.markAsNotDone();

                    System.out.println("____________________________________________________________");
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(unmarkTask);
                    System.out.println("____________________________________________________________");
                    System.out.print("\n");
                } else {
                    System.out.println("____________________________________________________________");
                    System.out.println("No task with index " + unmarkTaskNo + " exists!");
                    System.out.println("____________________________________________________________");
                    System.out.print("\n");
                }
                break;

            default:
                break;
            }

            userInput = scanner.nextLine();
            parameters = userInput.split(" ");
            command = parameters[0];
        }

        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");

        scanner.close();
    }
}
