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
                for (int i = 1; i <= tasks.size(); i++) {
                    System.out.println(i + ". " + tasks.get(i - 1));
                }
                System.out.println("____________________________________________________________");
                System.out.print("\n");
                break;

            case "mark":
                int taskNo = Integer.parseInt(parameters[1]);
                if (taskNo >= 1 && taskNo <= tasks.size()) {
                    Task selectedTask = tasks.get(taskNo - 1);
                    selectedTask.markAsDone();

                    System.out.println("____________________________________________________________");
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(selectedTask);
                    System.out.println("____________________________________________________________");
                    System.out.print("\n");
                }
                break;

            case "unmark":
                taskNo = Integer.parseInt(parameters[1]);
                if (taskNo >= 1 && taskNo <= tasks.size()) {
                    Task selectedTask = tasks.get(taskNo - 1);
                    selectedTask.markAsNotDone();

                    System.out.println("____________________________________________________________");
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(selectedTask);
                    System.out.println("____________________________________________________________");
                    System.out.print("\n");
                }
                break;

            default:
                Task newTask = new Task(userInput);
                tasks.add(newTask);

                System.out.println("____________________________________________________________");
                System.out.println("added: " + userInput);
                System.out.println("____________________________________________________________");
                System.out.print("\n");
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
