import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Locale;

public class TaskListParser {

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
    
    public Command parseCommand(String userInput, Ui ui, TaskList tasks, Storage storage) 
            throws BmoException {
        userInput = userInput.strip();
        String[] parts = CommandParser.splitParameters(userInput, new String[]{ " " });
        String commandName = parts[0];
        String parameters = parts[1];

        CommandParser commandParser = null;
        switch (commandName) {
        case "list":
            commandParser = new ListCommandParser();
            break;

        case "todo":
            commandParser = new TodoCommandParser();
            break;

        case "deadline":
            commandParser = new DeadlineCommandParser();
            break;

        case "event":
            commandParser = new EventCommandParser();
            break;

        case "mark":
            int totalTasks = tasks.getTotal();
            commandParser = new MarkCommandParser(totalTasks);
            break;

        case "unmark":
            totalTasks = tasks.getTotal();
            commandParser = new UnmarkCommandParser(totalTasks);
            break;

        case "delete":
            totalTasks = tasks.getTotal();
            commandParser = new DeleteCommandParser(totalTasks);
            break;
            
        case "bye":
            commandParser = new ByeCommandParser();
            break;

        default:
            // unknown command type, throw BmoException to Bmo
            commandParser = new UnknownCommandParser(commandName);
        }
        
        return commandParser.parse(parameters);
    }
}
