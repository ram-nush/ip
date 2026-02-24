import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class DeadlineCommandParser implements CommandParser {

    @Override
    public Command parse(String parameters) throws BmoException {
        String[] delimiters = new String[]{ "/by" };
        String[] parts = CommandParser.splitParameters(parameters, delimiters);

        String description = parts[0];
        String byString = parts[1];

        if (description.isEmpty()) {
            String message = String.format(BmoException.BMO_MISSING_PARAMS_MESSAGE,
                    "description", "deadline");
            String suggestion = String.format(BmoException.BMO_MISSING_PARAMS_SUGGESTION,
                    "deadline", Parser.DEADLINE_COMMAND_FORMAT);
            throw new BmoException(message, suggestion);
        }

        if (byString.isEmpty()) {
            String message = String.format(BmoException.BMO_MISSING_PARAMS_MESSAGE,
                    "due", "deadline");
            String suggestion = String.format(BmoException.BMO_MISSING_PARAMS_SUGGESTION,
                    "deadline", Parser.DEADLINE_COMMAND_FORMAT);
            throw new BmoException(message, suggestion);
        }

        LocalDateTime by;

        try {
            by = LocalDateTime.parse(byString, Parser.INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            String message = String.format(BmoException.BMO_STORE_DATETIME_MESSAGE,
                    "due", "deadline");
            String suggestion = String.format(BmoException.BMO_STORE_DATETIME_SUGGESTION,
                    "deadline", Parser.INPUT_DATETIME_PATTERN, Parser.DEADLINE_COMMAND_FORMAT);
            throw new BmoException(message, suggestion);
        }

        return new DeadlineCommand(description, by);
    }
}