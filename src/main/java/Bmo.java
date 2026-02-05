import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Bmo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pattern numberMatch = Pattern.compile("\\d+");
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
                    todoDescription += " ";
                }
                
                if (todoDescription.isEmpty()) {
                    // handle error 1: todo description is empty
                    System.out.println("____________________________________________________________");
                    System.out.println("OOPS!!! The description of a todo cannot be empty.");
                    System.out.println("To fix: Add a description after todo");
                    System.out.println("____________________________________________________________");
                    System.out.print("\n");
                    break;
                }
                
                Task todoTask = new Todo(todoDescription);
                tasks.add(todoTask);

                System.out.println("____________________________________________________________");
                System.out.println("Got it. I've added this task:");
                System.out.println(todoTask);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                System.out.println("____________________________________________________________");
                System.out.print("\n");
                break;

            case "deadline":
                String deadlineDescription = "";
                String deadlineBy = "";
                boolean hasReachedBy = false;
                for (int i = 1; i < parameters.length; i++) {
                    if (parameters[i].equals("/by")) {
                        hasReachedBy = true;
                        continue;
                    }

                    if (hasReachedBy) {
                        deadlineBy += parameters[i];
                        deadlineBy += " ";
                    } else {
                        deadlineDescription += parameters[i];
                        deadlineDescription += " ";
                    }
                }

                if (deadlineDescription.isEmpty()) {
                    // handle error 2a: deadline description is empty
                    System.out.println("____________________________________________________________");
                    System.out.println("OOPS!!! The description of a deadline cannot be empty.");
                    System.out.println("To fix: Add a description after deadline");
                    System.out.println("____________________________________________________________");
                    System.out.print("\n");
                    break;
                } else if (deadlineBy.isEmpty()) {
                    // handle error 2b: deadline by is empty
                    System.out.println("____________________________________________________________");
                    System.out.println("OOPS!!! The due of a deadline cannot be empty.");
                    System.out.println("To fix: Add a due after /by option");
                    System.out.println("____________________________________________________________");
                    System.out.print("\n");
                    break;
                }
                
                Task deadlineTask = new Deadline(deadlineDescription, deadlineBy);
                tasks.add(deadlineTask);

                System.out.println("____________________________________________________________");
                System.out.println("Got it. I've added this task:");
                System.out.println(deadlineTask);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                System.out.println("____________________________________________________________");
                System.out.print("\n");
                break;

            case "event":
                String eventDescription = "";
                String eventFrom = "";
                String eventTo = "";
                boolean hasReachedFrom = false;
                boolean hasReachedTo = false;
                for (int i = 1; i < parameters.length; i++) {
                    if (parameters[i].equals("/from")) {
                        hasReachedFrom = true;
                        continue;
                    }

                    if (parameters[i].equals("/to")) {
                        hasReachedTo = true;
                        continue;
                    }

                    if (hasReachedTo) {
                        eventTo += parameters[i];
                        eventTo += " ";
                    } else if (hasReachedFrom) {
                        eventFrom += parameters[i];
                        eventFrom += " ";
                    } else {
                        eventDescription += parameters[i];
                        eventDescription += " ";
                    }
                }

                if (eventDescription.isEmpty()) {
                    // handle error 3a: event description is empty
                    System.out.println("____________________________________________________________");
                    System.out.println("OOPS!!! The description of an event cannot be empty.");
                    System.out.println("To fix: Add a description after event");
                    System.out.println("____________________________________________________________");
                    System.out.print("\n");
                    break;
                } else if (eventFrom.isEmpty()) {
                    // handle error 3b: event from is empty
                    System.out.println("____________________________________________________________");
                    System.out.println("OOPS!!! The start of an event cannot be empty.");
                    System.out.println("To fix: Add a start after /from option");
                    System.out.println("____________________________________________________________");
                    System.out.print("\n");
                    break;
                } else if (eventTo.isEmpty()) {
                    // handle error 3c: event to is empty
                    System.out.println("____________________________________________________________");
                    System.out.println("OOPS!!! The end of an event cannot be empty.");
                    System.out.println("To fix: Add an end after /to option");
                    System.out.println("____________________________________________________________");
                    System.out.print("\n");
                    break;
                }
                
                Task eventTask = new Event(eventDescription, eventFrom, eventTo);
                tasks.add(eventTask);

                System.out.println("____________________________________________________________");
                System.out.println("Got it. I've added this task:");
                System.out.println(eventTask);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                System.out.println("____________________________________________________________");
                System.out.print("\n");
                break;

            case "mark":
                boolean isMarkNumber = numberMatch.matcher(parameters[1]).matches();
                if (!isMarkNumber) {
                    // handle error 4: mark task with invalid index
                    System.out.println("____________________________________________________________");
                    System.out.println(parameters[1] + " is not a number!");
                    System.out.println("To fix: Enter a number");
                    System.out.println("____________________________________________________________");
                    System.out.print("\n");
                }
                
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
                    // handle error 4: mark task with invalid index
                    System.out.println("____________________________________________________________");
                    System.out.println("No task with index " + markTaskNo + " exists!");
                    System.out.println("To fix: Enter a number between 1 and " + tasks.size());
                    System.out.println("____________________________________________________________");
                    System.out.print("\n");
                }
                break;

            case "unmark":
                boolean isUnmarkNumber = numberMatch.matcher(parameters[1]).matches();
                if (!isUnmarkNumber) {
                    // handle error 5: unmark task with invalid index
                    System.out.println("____________________________________________________________");
                    System.out.println(parameters[1] + " is not a number!");
                    System.out.println("To fix: Enter a number");
                    System.out.println("____________________________________________________________");
                    System.out.print("\n");
                }

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
                    // handle error 5: unmark task with invalid index
                    System.out.println("____________________________________________________________");
                    System.out.println("No task with index " + unmarkTaskNo + " exists!");
                    System.out.println("To fix: Enter a number between 1 and " + tasks.size());
                    System.out.println("____________________________________________________________");
                    System.out.print("\n");
                }
                break;

            default:
                // handle error 6: unknown command
                System.out.println("____________________________________________________________");
                System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(");
                System.out.println("To fix: Enter one of the following commands in the correct format");
                System.out.println("list");
                System.out.println("todo <description>");
                System.out.println("deadline <description> /by <due>");
                System.out.println("event <description> /from <start> /to <end>");
                System.out.println("mark <index>");
                System.out.println("unmark <index>");
                System.out.println("bye");
                System.out.println("____________________________________________________________");
                System.out.print("\n");
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
