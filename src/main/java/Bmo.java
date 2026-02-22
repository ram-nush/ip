import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Bmo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Path BMO_FILE = Path.of("data", "bmo.txt");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM d yyyy HHmm");
        
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
        helpMessages.add("format of <due>,<start>,<end> is dd-MM-yyyy HHmm");
        
        TaskList tasks = new TaskList();
        // retrieve tasks
        try {
            if (Files.exists(BMO_FILE)) {
                List<String> lines = Files.readAllLines(BMO_FILE);
                for (String line : lines) {
                    String[] properties = line.split(" \\| ");
                    if (properties[0].equals("T")) {
                        if (properties.length == 3) {
                            Task task = new Todo(properties[2]);
                            if (properties[1].equals("1")) {
                                task.markAsDone();
                            }
                            tasks.addTask(task);
                        }
                    } else if (properties[0].equals("D")) {
                        if (properties.length == 4) {
                            LocalDateTime by = LocalDateTime.parse(properties[3], outputFormatter);
                            Task task = new Deadline(properties[2], by);
                            if (properties[1].equals("1")) {
                                task.markAsDone();
                            }
                            tasks.addTask(task);
                        }
                    } else if (properties[0].equals("E")) {
                        if (properties.length == 5) {
                            LocalDateTime from = LocalDateTime.parse(properties[3], outputFormatter);
                            LocalDateTime to = LocalDateTime.parse(properties[4], outputFormatter);
                            Task task = new Event(properties[2], from, to);
                            if (properties[1].equals("1")) {
                                task.markAsDone();
                            }
                            tasks.addTask(task);
                        }
                    }
                }
                
                printMessage("The following tasks have been retrieved:\n" + tasks.toString());
            }
        } catch (IOException e) {
            System.err.println("An I/O error occurred: " + e.getMessage());
        } catch (DateTimeParseException e) {
            printMessage("Datetime is in incorrect format: " + e.getMessage());
        } catch (MissingArgumentException e) {
            printMessage(e.getMessage() + "\n" + e.getSuggestString());
        }
        
        printMessage("Hello! I'm BMO\n" + "What can I do for you?");

        String userInput = scanner.nextLine();
        String[] parts = userInput.split(" ", 2);
        String command = parts[0];
        String arguments = "";
        if (parts.length > 1) {
            arguments = parts[1];
        }
        
        while (!command.equals("bye")) {
            switch (command) {
            case "list":
                System.out.println("____________________________________________________________");
                System.out.println("Here are the tasks in your list:");
                System.out.println(tasks.listTasks());
                System.out.println("____________________________________________________________");
                System.out.print("\n");
                break;

            case "todo":
                String todoDescription = arguments.strip();
                                
                try {
                    Task todoTask = new Todo(todoDescription);
                    tasks.addTask(todoTask);

                    printMessage("Got it. I've added this task:\n" + todoTask
                            + "\nNow you have " + tasks.getTotal() + " tasks in the list.");
                    
                } catch (MissingArgumentException e) {
                    printMessage(e.getMessage() + "\n" + e.getSuggestString());
                }
                break;

            case "deadline":
                String[] deadlineParts = arguments.split(" /by ");
                String deadlineDescription = deadlineParts[0].strip();
                String by = "";
                if (deadlineParts.length > 1) {
                    by = deadlineParts[1].strip();
                }
                
                try {
                    LocalDateTime deadlineBy = LocalDateTime.parse(by, formatter);
                    Task deadlineTask = new Deadline(deadlineDescription, deadlineBy);
                    tasks.addTask(deadlineTask);

                    printMessage("Got it. I've added this task:\n" + deadlineTask
                            + "\nNow you have " + tasks.getTotal() + " tasks in the list.");
                } catch (DateTimeParseException e) {
                    printMessage("Datetime is in incorrect format: " + e.getMessage());
                } catch (MissingArgumentException e) {
                    printMessage(e.getMessage() + "\n" + e.getSuggestString());
                }
                break;

            case "event":
                
                String[] eventParts = arguments.split(" /from ");
                String eventDescription = eventParts[0].strip();
                String from = "";
                String to = "";
                
                if (eventParts.length > 1) {
                    String[] timeParts = eventParts[1].split(" /to ");
                    from = timeParts[0].strip();
                    if (timeParts.length > 1) {
                        to = timeParts[1].strip();
                    }
                }
                 
                try {
                    LocalDateTime eventFrom = LocalDateTime.parse(from, formatter);
                    LocalDateTime eventTo = LocalDateTime.parse(to, formatter);
                    Task eventTask = new Event(eventDescription, eventFrom, eventTo);
                    tasks.addTask(eventTask);

                    printMessage("Got it. I've added this task:\n" + eventTask
                            + "\nNow you have " + tasks.getTotal() + " tasks in the list.");
                } catch (DateTimeParseException e) {
                    printMessage("Datetime is in incorrect format: " + e.getMessage());
                } catch (MissingArgumentException e) {
                    printMessage(e.getMessage() + "\n" + e.getSuggestString());
                }
                break;

            case "mark":
                try {
                    int markTaskNo = 0;
                    try {
                        markTaskNo = Integer.parseInt(arguments);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new MissingArgumentException("index", "mark",
                                "To fix: Add an index after mark");
                    }
                    if (isInRange(markTaskNo, tasks)) {
                        Task markTask = tasks.markTask(markTaskNo);
                        printMessage("Nice! I've marked this task as done:\n" + markTask);
                    }
                } catch (MissingArgumentException e) {
                    printMessage(e.getMessage() + "\n" + e.getSuggestString());
                } catch (NumberFormatException e) {
                    printMessage(arguments + " is not a number!\n"
                            + "To fix: Enter a number");
                } catch (InvalidIndexException e) {
                    printMessage(e.getMessage() + "\n" + e.getSuggestString());
                }
                break;

            case "unmark":
                try {
                    int unmarkTaskNo = 0;
                    try {
                        unmarkTaskNo = Integer.parseInt(arguments);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new MissingArgumentException("index", "unmark",
                                "To fix: Add an index after unmark");
                    }
                    if (isInRange(unmarkTaskNo, tasks)) {
                        Task unmarkTask = tasks.unmarkTask(unmarkTaskNo);
                        printMessage("OK, I've marked this task as not done yet:\n" + unmarkTask);
                    }
                } catch (MissingArgumentException e) {
                    printMessage(e.getMessage() + "\n" + e.getSuggestString());
                } catch (NumberFormatException e) {
                    printMessage(arguments + " is not a number!\n"
                            + "To fix: Enter a number");
                } catch (InvalidIndexException e) {
                    printMessage(e.getMessage() + "\n" + e.getSuggestString());
                }
                break;
                
            case "delete":
                try {
                    int deleteTaskNo = 0;
                    try {
                        deleteTaskNo = Integer.parseInt(arguments);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new MissingArgumentException("index", "delete",
                                "To fix: Add an index after delete");
                    }
                    if (isInRange(deleteTaskNo, tasks)) {
                        Task deleteTask = tasks.deleteTask(deleteTaskNo);
                        printMessage("Noted. I've removed this task:\n" + deleteTask
                                + "\nNow you have " + tasks.getTotal() + " tasks in the list.");
                    }
                } catch (MissingArgumentException e) {
                    printMessage(e.getMessage() + "\n" + e.getSuggestString());
                } catch (NumberFormatException e) {
                    printMessage(arguments + " is not a number!\n"
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
            parts = userInput.split(" ", 2);
            command = parts[0];
            arguments = "";
            if (parts.length > 1) {
                arguments = parts[1];
            }
        }

        // save tasks here
        String saveText = tasks.saveString();
        printMessage("The following tasks will be saved:\n" + saveText);
        
        try {
            if (BMO_FILE.getParent() != null) {
                Files.createDirectories(BMO_FILE.getParent());
            }

            if (Files.notExists(BMO_FILE)) {
                Files.createFile(BMO_FILE);
            }
            
            Files.writeString(BMO_FILE, saveText);
        } catch (IOException e) {
            System.err.println("An I/O error occurred: " + e.getMessage());
        }
        
        printMessage("Bye. Hope to see you again soon!");
        scanner.close();
    }
    
    public static boolean isInRange(int index, TaskList tasks) 
            throws InvalidIndexException {
        if (index < 1 || index > tasks.getTotal()) {
            throw new InvalidIndexException("No task with index " + index + " exists!",
                    "To fix: Enter a number between 1 and " + tasks.getTotal());
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
