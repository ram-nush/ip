import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Bmo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        List<String> helpMessages = new ArrayList<String>();
        helpMessages.add("To fix: Enter one of the following commands in the correct format");
        helpMessages.add("list");
        helpMessages.add("todo <description>");
        helpMessages.add("deadline <description> /by <due>");
        helpMessages.add("event <description> /from <start> /to <end>");
        helpMessages.add("mark <index>");
        helpMessages.add("unmark <index>");
        helpMessages.add("delete <index>");
        helpMessages.add("bye");
        
        List<Task> tasks = new ArrayList<Task>();

        printMessage("Hello! I'm BMO\n" + "What can I do for you?");

        String userInput = scanner.nextLine();
        String[] parameters = userInput.split(" ");
        String command = "";
        if (parameters.length > 0) {
            command = parameters[0];
        }
        
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
                                
                try {
                    Task todoTask = new Todo(todoDescription);
                    tasks.add(todoTask);

                    printMessage("Got it. I've added this task:n\n" + todoTask
                            + "\nNow you have " + tasks.size() + " tasks in the list.");
                    
                } catch (MissingArgumentException e) {
                    printMessage(e.getMessage() + "\n" + e.getSuggestString());
                }
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
                
                try {
                    Task deadlineTask = new Deadline(deadlineDescription, deadlineBy);
                    tasks.add(deadlineTask);

                    printMessage("Got it. I've added this task:\n" + deadlineTask
                            + "\nNow you have " + tasks.size() + " tasks in the list.");
                } catch (MissingArgumentException e) {
                    printMessage(e.getMessage() + "\n" + e.getSuggestString());
                }
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
                
                try {
                    Task eventTask = new Event(eventDescription, eventFrom, eventTo);
                    tasks.add(eventTask);

                    printMessage("Got it. I've added this task:\n" + eventTask
                            + "\nNow you have " + tasks.size() + " tasks in the list.");
                } catch (MissingArgumentException e) {
                    printMessage(e.getMessage() + "\n" + e.getSuggestString());
                }
                break;

            case "mark":
                try {
                    int markTaskNo = 0;
                    try {
                        markTaskNo = Integer.parseInt(parameters[1]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new MissingArgumentException("index", "mark",
                                "To fix: Add an index after mark");
                    }
                    if (isInRange(markTaskNo, tasks)) {
                        Task markTask = tasks.get(markTaskNo - 1);
                        markTask.markAsDone();

                        printMessage("Nice! I've marked this task as done:\n" + markTask);
                    }
                } catch (MissingArgumentException e) {
                    printMessage(e.getMessage() + "\n" + e.getSuggestString());
                } catch (NumberFormatException e) {
                    printMessage(parameters[1] + " is not a number!\n"
                            + "To fix: Enter a number");
                } catch (InvalidIndexException e) {
                    printMessage(e.getMessage() + "\n" + e.getSuggestString());
                }
                break;

            case "unmark":
                try {
                    int unmarkTaskNo = 0;
                    try {
                        unmarkTaskNo = Integer.parseInt(parameters[1]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new MissingArgumentException("index", "unmark",
                                "To fix: Add an index after unmark");
                    }
                    if (isInRange(unmarkTaskNo, tasks)) {
                        Task unmarkTask = tasks.get(unmarkTaskNo - 1);
                        unmarkTask.markAsNotDone();

                        printMessage("OK, I've marked this task as not done yet:\n" + unmarkTask);
                    }
                } catch (MissingArgumentException e) {
                    printMessage(e.getMessage() + "\n" + e.getSuggestString());
                } catch (NumberFormatException e) {
                    printMessage(parameters[1] + " is not a number!\n"
                            + "To fix: Enter a number");
                } catch (InvalidIndexException e) {
                    printMessage(e.getMessage() + "\n" + e.getSuggestString());
                }
                break;
                
            case "delete":
                try {
                    int deleteTaskNo = 0;
                    try {
                        deleteTaskNo = Integer.parseInt(parameters[1]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new MissingArgumentException("index", "delete",
                                "To fix: Add an index after delete");
                    }
                    if (isInRange(deleteTaskNo, tasks)) {
                        Task deleteTask = tasks.get(deleteTaskNo - 1);
                        int newSize = tasks.size() - 1;

                        printMessage("Noted. I've removed this task:\n" + deleteTask
                                + "\nNow you have " + newSize + " tasks in the list.");
                        
                        tasks.remove(deleteTaskNo - 1);
                    }
                } catch (MissingArgumentException e) {
                    printMessage(e.getMessage() + "\n" + e.getSuggestString());
                } catch (NumberFormatException e) {
                    printMessage(parameters[1] + " is not a number!\n"
                            + "To fix: Enter a number");
                } catch (InvalidIndexException e) {
                    printMessage(e.getMessage() + "\n" + e.getSuggestString());
                }
                break;

            default:
                printMessage("OOPS!!! I'm sorry, but I don't know what that means :-(");
                printMessages(helpMessages);
                break;
            }

            userInput = scanner.nextLine();
            parameters = userInput.split(" ");
            if (parameters.length > 0) {
                command = parameters[0];
            } else {
                command = "";
            }
        }

        // save tasks here
        String saveText = "";
        for (Task task : tasks) {
            saveText += task.saveString() + "\n";
        }
        printMessage("The following tasks will be saved:\n" + saveText);
        
        Path bmoFile = Path.of("data", "bmo.txt");
        
        try {
            if (bmoFile.getParent() != null) {
                Files.createDirectories(bmoFile.getParent());
            }

            if (Files.notExists(bmoFile)) {
                Files.createFile(bmoFile);
            }
            
            Files.writeString(bmoFile, saveText);
        } catch (IOException e) {
            System.err.println("An I/O error occurred: " + e.getMessage());
        }
        
        printMessage("Bye. Hope to see you again soon!");
        scanner.close();
    }
    
    public static boolean isInRange(int index, List<Task> tasks) 
            throws InvalidIndexException {
        if (index < 1 || index > tasks.size()) {
            throw new InvalidIndexException("No task with index " + index + " exists!",
                    "To fix: Enter a number between 1 and " + tasks.size());
        }
        return true;
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
