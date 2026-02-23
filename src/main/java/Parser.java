import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    String INPUT_DATETIME_PATTERN = "dd-MM-yyyy HHmm";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(INPUT_DATETIME_PATTERN);

    public static final String TODO_COMMAND_FORMAT = "todo <description>";
    public static final String DEADLINE_COMMAND_FORMAT = "deadline <description> /by <due>";
    public static final String EVENT_COMMAND_FORMAT = "event <description> /from <start> /to <end>";
    public static final String MARK_COMMAND_FORMAT = "mark <index>";
    public static final String UNMARK_COMMAND_FORMAT = "unmark <index>";
    public static final String DELETE_COMMAND_FORMAT = "delete <index>";
    
    public boolean hasExit;
    
    Parser() {
        this.hasExit = false;
    }
    
    public boolean hasExitCommand() {
        return this.hasExit;
    }
    
    public void parseCommand(String userInput, Ui ui, TaskList tasks, Storage storage) 
            throws BmoException {
        String[] parts = parseArguments(userInput, new String[]{ " " });
        String commandType = parts[0];
        String arguments = parts[1];

        switch (commandType) {
        case "list":
            ui.showTasks(tasks);
            break;

        case "todo":
            String todoDescription = arguments;

            if (todoDescription.isEmpty()) {
                String message = String.format(BmoException.BMO_MISSING_PARAMS_MESSAGE,
                        "description", "todo");
                String suggestion = String.format(BmoException.BMO_MISSING_PARAMS_SUGGESTION,
                        "todo", TODO_COMMAND_FORMAT);
                throw new BmoException(message, suggestion);
            }

            Task todoTask = new Todo(todoDescription);
            tasks.addTask(todoTask);
            ui.showAddMessage(todoTask, tasks);
            break;

        case "deadline":
            String[] deadlineDelimiters = new String[]{ " /by " };
            String[] deadlineParts = parseArguments(arguments, deadlineDelimiters);
            
            String deadlineDescription = deadlineParts[0];
            String by = deadlineParts[1];

            if (deadlineDescription.isEmpty()) {
                String message = String.format(BmoException.BMO_MISSING_PARAMS_MESSAGE,
                        "description", "deadline");
                String suggestion = String.format(BmoException.BMO_MISSING_PARAMS_SUGGESTION,
                        "deadline", DEADLINE_COMMAND_FORMAT);
                throw new BmoException(message, suggestion);
            }

            if (by.isEmpty()) {
                String message = String.format(BmoException.BMO_MISSING_PARAMS_MESSAGE,
                        "due", "deadline");
                String suggestion = String.format(BmoException.BMO_MISSING_PARAMS_SUGGESTION,
                        "deadline", DEADLINE_COMMAND_FORMAT);
                throw new BmoException(message, suggestion);
            }

            LocalDateTime deadlineBy;

            try {
                deadlineBy = LocalDateTime.parse(by, formatter);
            } catch (DateTimeParseException e) {
                String message = String.format(BmoException.BMO_STORE_DATETIME_MESSAGE,
                        "due", "deadline");
                String suggestion = String.format(BmoException.BMO_STORE_DATETIME_SUGGESTION,
                        "deadline", INPUT_DATETIME_PATTERN);
                throw new BmoException(message, suggestion);
            }

            Task deadlineTask = new Deadline(deadlineDescription, deadlineBy);
            tasks.addTask(deadlineTask);
            ui.showAddMessage(deadlineTask, tasks);
            break;

        case "event":
            String[] eventDelimiters = new String[]{ " /from ", " /to " };
            String[] eventParts = parseArguments(arguments, eventDelimiters);
            
            String eventDescription = eventParts[0];
            String from = eventParts[1];
            String to = eventParts[2];
            
            if (eventDescription.isEmpty()) {
                String message = String.format(BmoException.BMO_MISSING_PARAMS_MESSAGE,
                        "description", "event");
                String suggestion = String.format(BmoException.BMO_MISSING_PARAMS_SUGGESTION,
                        "event", EVENT_COMMAND_FORMAT);
                throw new BmoException(message, suggestion);
            }

            if (from.isEmpty()) {
                String message = String.format(BmoException.BMO_MISSING_PARAMS_MESSAGE,
                        "start", "event");
                String suggestion = String.format(BmoException.BMO_MISSING_PARAMS_SUGGESTION,
                        "event", EVENT_COMMAND_FORMAT);
                throw new BmoException(message, suggestion);
            }

            if (to.isEmpty()) {
                String message = String.format(BmoException.BMO_MISSING_PARAMS_MESSAGE,
                        "end", "event");
                String suggestion = String.format(BmoException.BMO_MISSING_PARAMS_SUGGESTION,
                        "event", EVENT_COMMAND_FORMAT);
                throw new BmoException(message, suggestion);
            }

            LocalDateTime eventFrom;
            LocalDateTime eventTo;
            
            try {
                eventFrom = LocalDateTime.parse(from, formatter);
            } catch (DateTimeParseException e) {
                String message = String.format(BmoException.BMO_STORE_DATETIME_MESSAGE, 
                        "start", "event");
                String suggestion = String.format(BmoException.BMO_STORE_DATETIME_SUGGESTION, 
                        "event", INPUT_DATETIME_PATTERN);
                throw new BmoException(message, suggestion);
            }

            try {
                eventTo = LocalDateTime.parse(to, formatter);
            } catch (DateTimeParseException e) {
                String message = String.format(BmoException.BMO_STORE_DATETIME_MESSAGE,
                        "end", "event");
                String suggestion = String.format(BmoException.BMO_STORE_DATETIME_SUGGESTION,
                        "event", INPUT_DATETIME_PATTERN);
                throw new BmoException(message, suggestion);
            }

            Task eventTask = new Event(eventDescription, eventFrom, eventTo);
            tasks.addTask(eventTask);
            ui.showAddMessage(eventTask, tasks);
            break;

        case "mark":
            try {
                if (arguments.isEmpty()) {
                    String message = String.format(BmoException.BMO_MISSING_PARAMS_MESSAGE,
                            "index", "mark");
                    String suggestion = String.format(BmoException.BMO_MISSING_PARAMS_SUGGESTION,
                            "mark", MARK_COMMAND_FORMAT);
                    throw new BmoException(message, suggestion);
                }

                int markTaskNo = Integer.parseInt(arguments);
                // can throw BmoException, handle in Bmo
                if (isInRange(markTaskNo, tasks)) {
                    Task markTask = tasks.markTask(markTaskNo);
                    ui.showMarkMessage(markTask);
                }
            } catch (NumberFormatException e) {
                String message = String.format(BmoException.BMO_NOT_INTEGER_MESSAGE, arguments);
                String suggestion = BmoException.BMO_NOT_INTEGER_SUGGESTION;
                throw new BmoException(message, suggestion);
            }
            break;

        case "unmark":
            try {
                if (arguments.isEmpty()) {
                    String message = String.format(BmoException.BMO_MISSING_PARAMS_MESSAGE,
                            "index", "unmark");
                    String suggestion = String.format(BmoException.BMO_MISSING_PARAMS_SUGGESTION,
                            "unmark", UNMARK_COMMAND_FORMAT);
                    throw new BmoException(message, suggestion);
                }

                int unmarkTaskNo = Integer.parseInt(arguments);
                // can throw BmoException, handle in Bmo
                if (isInRange(unmarkTaskNo, tasks)) {
                    Task unmarkTask = tasks.unmarkTask(unmarkTaskNo);
                    ui.showUnmarkMessage(unmarkTask);
                }
            } catch (NumberFormatException e) {
                String message = String.format(BmoException.BMO_NOT_INTEGER_MESSAGE, arguments);
                String suggestion = BmoException.BMO_NOT_INTEGER_SUGGESTION;
                throw new BmoException(message, suggestion);
            }
            break;

        case "delete":
            try {
                if (arguments.isEmpty()) {
                    String message = String.format(BmoException.BMO_MISSING_PARAMS_MESSAGE, 
                            "index", "delete");
                    String suggestion = String.format(BmoException.BMO_MISSING_PARAMS_SUGGESTION, 
                            "delete", DELETE_COMMAND_FORMAT);
                    throw new BmoException(message, suggestion);
                }
                
                int deleteTaskNo = Integer.parseInt(arguments);
                // can throw BmoException, handle in Bmo
                if (isInRange(deleteTaskNo, tasks)) {
                    Task deleteTask = tasks.deleteTask(deleteTaskNo);
                    ui.showDeleteMessage(deleteTask, tasks);
                }
            } catch (NumberFormatException e) {
                String message = String.format(BmoException.BMO_NOT_INTEGER_MESSAGE, arguments);
                String suggestion = BmoException.BMO_NOT_INTEGER_SUGGESTION;
                throw new BmoException(message, suggestion);
            }
            break;
            
        case "bye":
            String saveText = tasks.saveString();
            ui.showSaveMessage(saveText);
            // can throw BmoException
            storage.save(saveText);
            break;

        default:
            ui.showDefaultMessage();
            ui.showHelpMessage();
            break;
        }
        
        this.hasExit = commandType.equals("bye");
    }
    
    public static String[] parseArguments(String arguments, String[] delimiters) {
        List<String> argumentsList = new ArrayList<String>();
        String remainingArgs = arguments;
        String[] parts;
        String argument;
        
        for (String delimiter : delimiters) {
            parts = remainingArgs.split(delimiter, 2);
            argument = parts[0].strip();
            argumentsList.add(argument);
            
            remainingArgs = "";
            if (parts.length == 2) {
                remainingArgs = parts[1];
            }
        }
        argumentsList.add(remainingArgs.strip());
        
        return argumentsList.toArray(new String[0]);
    }

    public static boolean isInRange(int index, TaskList tasks)
            throws BmoException {
        
        if (!tasks.isInRange(index)) {
            int numTasks = tasks.getTotal();
            if (tasks.isEmpty()) {
                String exMessage = String.format(BmoException.BMO_INVALID_INTEGER_MESSAGE, index);
                String exSuggestion = BmoException.BMO_INVALID_INTEGER_SUGGESTION_EMPTY;
                throw new BmoException(exMessage, exSuggestion);
            }
            
            String exMessage = String.format(BmoException.BMO_INVALID_INTEGER_MESSAGE, index);
            String exSuggestion = String.format(BmoException.BMO_INVALID_INTEGER_SUGGESTION_EXISTING,
                    numTasks, numTasks);
            throw new BmoException(exMessage, exSuggestion);
        }
        
        return true;
    }
}
