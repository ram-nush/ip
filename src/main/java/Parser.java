import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Parser {

    public static final String INPUT_DATETIME_PATTERN = "d-M-uuuu HHmm";
    public static final DateTimeFormatter INPUT_FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern(INPUT_DATETIME_PATTERN)
            .toFormatter(Locale.ENGLISH)
            .withResolverStyle(ResolverStyle.STRICT);

    public static final String LIST_COMMAND_FORMAT = "list";  
    public static final String TODO_COMMAND_FORMAT = "todo <description>";
    public static final String DEADLINE_COMMAND_FORMAT = "deadline <description> /by <due>";
    public static final String EVENT_COMMAND_FORMAT = "event <description> /from <start> /to <end>";
    public static final String MARK_COMMAND_FORMAT = "mark <index>";
    public static final String UNMARK_COMMAND_FORMAT = "unmark <index>";
    public static final String DELETE_COMMAND_FORMAT = "delete <index>";
    public static final String BYE_COMMAND_FORMAT = "bye";
    public static final String DATETIME_COMMAND_FORMAT = String.format("Note: <due>, <start>, <end>"
            + " are in %s datetime format",  INPUT_DATETIME_PATTERN);
    
    public static final List<String> COMMAND_FORMATS = List.of(LIST_COMMAND_FORMAT, TODO_COMMAND_FORMAT, 
            DEADLINE_COMMAND_FORMAT, EVENT_COMMAND_FORMAT, MARK_COMMAND_FORMAT, UNMARK_COMMAND_FORMAT, 
            DELETE_COMMAND_FORMAT, BYE_COMMAND_FORMAT, DATETIME_COMMAND_FORMAT);
    
    public boolean hasExit;
    
    Parser() {
        this.hasExit = false;
    }
    
    public boolean hasExitCommand() {
        return this.hasExit;
    }
    
    public void parseCommand(String userInput, Ui ui, TaskList tasks, Storage storage) 
            throws BmoException {
        userInput = userInput.strip();
        String[] parts = parseArguments(userInput, new String[]{ " " });
        String commandType = parts[0];
        String arguments = parts[1];

        Command command = null;
        switch (commandType) {
        case "list":
            command = new ListCommand();
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

            command = new TodoCommand(todoDescription);
            break;

        case "deadline":
            String[] deadlineDelimiters = new String[]{ "/by" };
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
                deadlineBy = LocalDateTime.parse(by, INPUT_FORMATTER);
            } catch (DateTimeParseException e) {
                String message = String.format(BmoException.BMO_STORE_DATETIME_MESSAGE,
                        "due", "deadline");
                String suggestion = String.format(BmoException.BMO_STORE_DATETIME_SUGGESTION,
                        "deadline", INPUT_DATETIME_PATTERN, DEADLINE_COMMAND_FORMAT);
                throw new BmoException(message, suggestion);
            }

            command = new DeadlineCommand(deadlineDescription, deadlineBy);
            break;

        case "event":
            String[] eventDelimiters = new String[]{ "/from", "/to" };
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
                eventFrom = LocalDateTime.parse(from, INPUT_FORMATTER);
            } catch (DateTimeParseException e) {
                String message = String.format(BmoException.BMO_STORE_DATETIME_MESSAGE, 
                        "start", "event");
                String suggestion = String.format(BmoException.BMO_STORE_DATETIME_SUGGESTION, 
                        "event", INPUT_DATETIME_PATTERN, EVENT_COMMAND_FORMAT);
                throw new BmoException(message, suggestion);
            }

            try {
                eventTo = LocalDateTime.parse(to, INPUT_FORMATTER);
            } catch (DateTimeParseException e) {
                String message = String.format(BmoException.BMO_STORE_DATETIME_MESSAGE,
                        "end", "event");
                String suggestion = String.format(BmoException.BMO_STORE_DATETIME_SUGGESTION,
                        "event", INPUT_DATETIME_PATTERN, EVENT_COMMAND_FORMAT);
                throw new BmoException(message, suggestion);
            }

            command = new EventCommand(eventDescription, eventFrom, eventTo);
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
                    command = new MarkCommand(markTaskNo);
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
                    command = new UnmarkCommand(unmarkTaskNo);
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
                    command = new DeleteCommand(deleteTaskNo);
                }
            } catch (NumberFormatException e) {
                String message = String.format(BmoException.BMO_NOT_INTEGER_MESSAGE, arguments);
                String suggestion = BmoException.BMO_NOT_INTEGER_SUGGESTION;
                throw new BmoException(message, suggestion);
            }
            break;
            
        case "bye":
            command = new ByeCommand();
            break;

        default:
            // invalid command type, throw BmoException to Bmo
            command = new InvalidCommand(commandType);
        }
        command.execute(tasks, ui, storage);
        this.hasExit = command.isExit();
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
