import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class EventCommandParser implements CommandParser {

    @Override
    public Command parse(String parameters) throws BmoException {
        String[] delimiters = new String[]{ "/from", "/to" };
        String[] parts = CommandParser.splitParameters(parameters, delimiters);

        String description = parts[0];
        String fromString = parts[1];
        String toString = parts[2];

        if (description.isEmpty()) {
            String message = String.format(BmoException.BMO_MISSING_PARAMS_MESSAGE,
                    "description", "event");
            String suggestion = String.format(BmoException.BMO_MISSING_PARAMS_SUGGESTION,
                    "event", TaskListParser.EVENT_COMMAND_FORMAT);
            throw new BmoException(message, suggestion);
        }

        if (fromString.isEmpty()) {
            String message = String.format(BmoException.BMO_MISSING_PARAMS_MESSAGE,
                    "start", "event");
            String suggestion = String.format(BmoException.BMO_MISSING_PARAMS_SUGGESTION,
                    "event", TaskListParser.EVENT_COMMAND_FORMAT);
            throw new BmoException(message, suggestion);
        }

        if (toString.isEmpty()) {
            String message = String.format(BmoException.BMO_MISSING_PARAMS_MESSAGE,
                    "end", "event");
            String suggestion = String.format(BmoException.BMO_MISSING_PARAMS_SUGGESTION,
                    "event", TaskListParser.EVENT_COMMAND_FORMAT);
            throw new BmoException(message, suggestion);
        }

        LocalDateTime from;
        LocalDateTime to;

        try {
            from = LocalDateTime.parse(fromString, TaskListParser.INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            String message = String.format(BmoException.BMO_STORE_DATETIME_MESSAGE,
                    "start", "event");
            String suggestion = String.format(BmoException.BMO_STORE_DATETIME_SUGGESTION,
                    "event", TaskListParser.INPUT_DATETIME_PATTERN, TaskListParser.EVENT_COMMAND_FORMAT);
            throw new BmoException(message, suggestion);
        }

        try {
            to = LocalDateTime.parse(toString, TaskListParser.INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            String message = String.format(BmoException.BMO_STORE_DATETIME_MESSAGE,
                    "end", "event");
            String suggestion = String.format(BmoException.BMO_STORE_DATETIME_SUGGESTION,
                    "event", TaskListParser.INPUT_DATETIME_PATTERN, TaskListParser.EVENT_COMMAND_FORMAT);
            throw new BmoException(message, suggestion);
        }

        return new EventCommand(description, from, to);
    }
}