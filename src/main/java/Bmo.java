import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Bmo {
    
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    
    public Bmo(String filePath) {
        ui = new Ui();
        boolean exceptionCaught = true;
        try {
            storage = new Storage(filePath);
            tasks = new TaskList(storage.load());
            exceptionCaught = false;
        } catch (IOException e) {
            ui.showErrorMessage(e, filePath);
        } catch (InvalidPathException e) {
            ui.showErrorMessage(e, filePath);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.showErrorMessage(e);
        } catch (DateTimeParseException e) {
            ui.showErrorMessage(e, Storage.INPUT_DATETIME_PATTERN);
        } catch (MissingArgumentException e) {
            ui.showErrorMessage(e);
        } finally {
            if (exceptionCaught) {
                tasks = new TaskList();
            }
        }
    }
    
    public void run() {
        Scanner scanner = new Scanner(System.in);

        String INPUT_DATETIME_PATTERN = "dd-MM-yyyy HHmm";
        String OUTPUT_DATETIME_PATTERN = "MMM d yyyy HHmm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(INPUT_DATETIME_PATTERN);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(OUTPUT_DATETIME_PATTERN);

        // retrieve tasks
        ui.showRetrieveMessage(tasks.toString());
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
        try {
            String saveText = tasks.saveString();
            ui.showSaveMessage(saveText);
            storage.save(saveText);
        } catch (IOException e) {
            System.err.println("An I/O error occurred: " + e.getMessage());
        }

        ui.showByeMessage();
        scanner.close();
    }
    
    public static void main(String[] args) {
        String BMO_FILE_PATH = "data/bmo.txt";
        new Bmo(BMO_FILE_PATH).run();
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
