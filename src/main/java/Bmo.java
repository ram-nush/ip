import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.List;

public class Bmo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Path BMO_FILE = Path.of("data", "bmo.txt");
        String INPUT_DATETIME_PATTERN = "dd-MM-yyyy HHmm";
        String OUTPUT_DATETIME_PATTERN = "MMM d yyyy HHmm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(INPUT_DATETIME_PATTERN);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(OUTPUT_DATETIME_PATTERN);
        
        Ui ui = new Ui();
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
                
                ui.showRetrieveMessage(tasks.toString());
            }
        } catch (BmoException e) {
            ui.showErrorMessage(e);
        } catch (IOException e) {
            ui.showErrorMessage(e, BMO_FILE);
        } catch (DateTimeParseException e) {
            ui.showErrorMessage(e, INPUT_DATETIME_PATTERN);
        }

        ui.showWelcomeMessage();

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
                ui.showTasks(tasks);
                break;

            case "todo":
                String todoDescription = arguments.strip();
                                
                try {
                    Task todoTask = new Todo(todoDescription);
                    tasks.addTask(todoTask);
                    ui.showAddMessage(todoTask, tasks);
                } catch (BmoException e) {
                    ui.showErrorMessage(e);
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
                    ui.showAddMessage(deadlineTask, tasks);
                } catch (BmoException e) {
                    ui.showErrorMessage(e);
                } catch (DateTimeParseException e) {
                    ui.showErrorMessage(e, INPUT_DATETIME_PATTERN);
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
                    ui.showAddMessage(eventTask, tasks);
                } catch (BmoException e) {
                    ui.showErrorMessage(e);
                } catch (DateTimeParseException e) {
                    ui.showErrorMessage(e, INPUT_DATETIME_PATTERN);
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
                        ui.showMarkMessage(markTask);
                    }
                } catch (BmoException e) {
                    ui.showErrorMessage(e);
                } catch (NumberFormatException e) {
                    ui.showErrorMessage(e, arguments);
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
                        ui.showUnmarkMessage(unmarkTask);
                    }
                } catch (BmoException e) {
                    ui.showErrorMessage(e);
                } catch (NumberFormatException e) {
                    ui.showErrorMessage(e, arguments);
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
                        ui.showDeleteMessage(deleteTask, tasks);
                    }
                } catch (BmoException e) {
                    ui.showErrorMessage(e);
                } catch (NumberFormatException e) {
                    ui.showErrorMessage(e, arguments);
                }
                break;

            default:
                ui.showDefaultMessage();
                ui.showHelpMessage();
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
        ui.showSaveMessage(saveText);
        
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
        
        ui.showByeMessage();
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
}
