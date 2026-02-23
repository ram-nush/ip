import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {

    String INPUT_DATETIME_PATTERN = "dd-MM-yyyy HHmm";
    String OUTPUT_DATETIME_PATTERN = "MMM d yyyy HHmm";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(INPUT_DATETIME_PATTERN);
    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(OUTPUT_DATETIME_PATTERN);
    
    public boolean hasExit;
    
    Parser() {
        this.hasExit = false;
    }
    
    public boolean hasExitCommand() {
        return this.hasExit;
    }
    
    public void parseCommand(String userInput, Ui ui, TaskList tasks, Storage storage) {
        String[] parts = userInput.split(" ", 2);
        String commandType = parts[0];
        String arguments = "";
        if (parts.length > 1) {
            arguments = parts[1];
        }

        switch (commandType) {
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
            
        case "bye":
            try {
                String saveText = tasks.saveString();
                ui.showSaveMessage(saveText);
                storage.save(saveText);
            } catch (IOException e) {
                System.err.println("An I/O error occurred: " + e.getMessage());
            }
            break;

        default:
            ui.showDefaultMessage();
            ui.showHelpMessage();
            break;
        }
        
        if (commandType.equals("bye")) {
            this.hasExit = true;
        } else {
            this.hasExit = false;
        }
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
