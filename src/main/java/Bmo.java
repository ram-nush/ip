import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Bmo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        Pattern numberMatch = Pattern.compile("\\d+");
        List<String> helpMessages = new ArrayList<String>();
        helpMessages.add("To fix: Enter one of the following commands in the correct format");
        helpMessages.add("list");
        helpMessages.add("todo <description>");
        helpMessages.add("deadline <description> /by <due>");
        helpMessages.add("event <description> /from <start> /to <end>");
        helpMessages.add("mark <index>");
        helpMessages.add("unmark <index>");
        helpMessages.add("bye");
        
        List<Task> tasks = new ArrayList<Task>();

        printMessage("Hello! I'm BMO");
        printMessage("What can I do for you?");

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
                    printMessage("OOPS!!! The description of a todo cannot be empty.\n" 
                            + "To fix: Add a description after todo");
                    break;
                }
                
                Task todoTask = new Todo(todoDescription);
                tasks.add(todoTask);

                printMessage("Got it. I've added this task:n\n"
                        + todoTask.toString()
                        + "\nNow you have " + tasks.size() + " tasks in the list.");
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
                    printMessage("OOPS!!! The description of a deadline cannot be empty.\n"
                            + "To fix: Add a description after deadline");
                    break;
                } else if (deadlineBy.isEmpty()) {
                    // handle error 2b: deadline by is empty
                    printMessage("OOPS!!! The due of a deadline cannot be empty.\n"
                            + "To fix: Add a due after /by option");
                    break;
                }
                
                Task deadlineTask = new Deadline(deadlineDescription, deadlineBy);
                tasks.add(deadlineTask);

                printMessage("Got it. I've added this task:\n" 
                        + deadlineTask.toString()
                        + "\nNow you have " + tasks.size() + " tasks in the list.");
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
                    printMessage("OOPS!!! The description of an event cannot be empty.\n"
                            + "To fix: Add a description after event");
                    break;
                } else if (eventFrom.isEmpty()) {
                    // handle error 3b: event from is empty
                    printMessage("OOPS!!! The start of an event cannot be empty.\n"
                            + "To fix: Add a start after /from option");
                    break;
                } else if (eventTo.isEmpty()) {
                    // handle error 3c: event to is empty
                    printMessage("OOPS!!! The end of an event cannot be empty.\n"
                            + "To fix: Add an end after /to option");
                    break;
                }
                
                Task eventTask = new Event(eventDescription, eventFrom, eventTo);
                tasks.add(eventTask);

                printMessage("Got it. I've added this task:\n" 
                        + eventTask.toString()
                        + "\nNow you have " + tasks.size() + " tasks in the list.");
                
                break;

            case "mark":
                boolean isMarkNumber = numberMatch.matcher(parameters[1]).matches();
                if (!isMarkNumber) {
                    // handle error 4: mark task with invalid index
                    printMessage(parameters[1] + " is not a number!\n" 
                            + "To fix: Enter a number");
                }
                
                int markTaskNo = Integer.parseInt(parameters[1]);
                if (markTaskNo >= 1 && markTaskNo <= tasks.size()) {
                    Task markTask = tasks.get(markTaskNo - 1);
                    markTask.markAsDone();

                    printMessage("Nice! I've marked this task as done:\n"
                            + markTask.toString());
                } else {
                    // handle error 4: mark task with invalid index
                    printMessage("No task with index " + markTaskNo + " exists!\n" 
                            + "To fix: Enter a number between 1 and " + tasks.size());
                }
                break;

            case "unmark":
                boolean isUnmarkNumber = numberMatch.matcher(parameters[1]).matches();
                if (!isUnmarkNumber) {
                    // handle error 5: unmark task with invalid index
                    printMessage(parameters[1] + " is not a number!\n"
                            + "To fix: Enter a number");
                }

                int unmarkTaskNo = Integer.parseInt(parameters[1]);
                if (unmarkTaskNo >= 1 && unmarkTaskNo <= tasks.size()) {
                    Task unmarkTask = tasks.get(unmarkTaskNo - 1);
                    unmarkTask.markAsNotDone();

                    printMessage("OK, I've marked this task as not done yet:\n" 
                            + unmarkTask.toString());
                } else {
                    // handle error 5: unmark task with invalid index
                    printMessage("No task with index " + unmarkTaskNo + " exists!\n" 
                            + "To fix: Enter a number between 1 and " + tasks.size());
                }
                break;

            default:
                // handle error 6: unknown command
                printMessage("OOPS!!! I'm sorry, but I don't know what that means :-(");
                printMessages(helpMessages);
                break;
            }

            userInput = scanner.nextLine();
            parameters = userInput.split(" ");
            command = parameters[0];
        }

        printMessage("Bye. Hope to see you again soon!");
        scanner.close();
    }
    
    public static void printMessage(String message) {
        System.out.println("____________________________________________________________");
        System.out.println(message);
        System.out.println("____________________________________________________________");
        System.out.print("\n");
    }

    public static void printMessages(List<String> messages) {
        System.out.println("____________________________________________________________");
        for (String message : messages) {
            System.out.println(message);
        }
        System.out.println("____________________________________________________________");
        System.out.print("\n");
    }
}
