package bmo.parser;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Locale;

import bmo.command.Command;
import bmo.exception.BmoException;
import bmo.storage.Storage;
import bmo.task.TaskList;
import bmo.ui.Ui;

/**
 * Represents a parser which parses a line of user input to create the respective 
 * command parsers. A <code>TaskListParser</code> object passes a string containing 
 * all parameters to the command parser.
 */
public class TaskListParser {

    // Accept single number day and month
    public static final String INPUT_DATETIME_PATTERN = "d-M-uuuu HHmm";
    
    // Enforce strict date checking
    public static final DateTimeFormatter INPUT_FORMATTER = new DateTimeFormatterBuilder()
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

    /**
     * Parses a line of user input to determine the command type and parameters.
     * Creates a CommandParser object created based on the command type.
     * Returns a Command object from CommandParser parsing the parameters.
     * May throw an exception if the parameter string does not follow the command format.
     *
     * @param userInput A string entered by the user.
     * @param ui The user interface object.
     * @param tasks The list of tasks to be saved.
     * @param storage The storage object.
     * @return A Command object corresponding to the CommandWord generated from 
     *         the extracted command type.
     * @throws BmoException If the parameter string does not follow the command format.
     */
    public Command parseCommand(String userInput, Ui ui, TaskList tasks, Storage storage) 
            throws BmoException {
        // Remove surrounding whitespaces from user input
        userInput = userInput.strip();

        // Split input into command name and parametersText
        String[] parts = CommandParser.splitParameters(userInput, new String[]{ " " });
        String commandName = parts[0];
        CommandWord commandWord = CommandWord.fromString(commandName);
        
        String parameterText = parts[1];

        // Create specific command parser based on command word
        CommandParser commandParser = switch (commandWord) {
            case LIST -> new ListCommandParser();
            case TODO -> new TodoCommandParser();
            case DEADLINE -> new DeadlineCommandParser();
            case EVENT -> new EventCommandParser();
            case MARK -> {
                int totalTasks = tasks.getTotal();
                yield new MarkCommandParser(totalTasks);
            }
            case UNMARK -> {
                int totalTasks = tasks.getTotal();
                yield new UnmarkCommandParser(totalTasks);
            }
            case DELETE -> {
                int totalTasks = tasks.getTotal();
                yield new DeleteCommandParser(totalTasks);
            }
            case BYE -> new ByeCommandParser();
            // Command name does not match any other command
            // Provide command name to parser for reference
            case UNKNOWN -> new UnknownCommandParser(commandName);
        };

        // Parse the parameterText to return a Command
        return commandParser.parse(parameterText);
    }
}
