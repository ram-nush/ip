package bmo.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import bmo.command.Command;
import bmo.command.CommandWord;
import bmo.command.EventCommand;
import bmo.exception.BmoException;

/**
 * Represents a specific command parser which returns the respective command.
 * An <code>EventCommandParser</code> object corresponds to a parser for a command with
 * the specific CommandWord e.g., <code>CommandWord.EVENT</code>.
 */
public class EventCommandParser extends CommandParser {

    EventCommandParser() {
        super(CommandWord.EVENT, new String[]{ "/from", "/to" }, new String[]{ "description", "start", "end" });
    }

    /**
     * Parses a given string to get parameters corresponding to a command type.
     * Returns a Command object created based on the parameters.
     * Throws a BmoException if not all parameters can be extracted.
     *
     * @param parameters A string containing parameters
     *                   corresponding to an EventCommand object.
     * @return A Command object corresponding to CommandWord.EVENT.
     * @throws BmoException If parameters does not contain all parameters
     *                      specific to EventCommand.
     */
    @Override
    public Command parse(String parameters) throws BmoException {
        // Split parameters into a parameter array
        String[] parts = CommandParser.splitParameters(parameters, this.delimiters);

        // Extract individual parameters
        String description = parts[0];
        String start = parts[1];
        String end = parts[2];

        // Ensure all parameters are non-empty
        CommandParser.checkNonEmpty(description, this.paramNames[0], this.commandWord,
                TaskListParser.EVENT_COMMAND_FORMAT);
        CommandParser.checkNonEmpty(start, this.paramNames[1], this.commandWord,
                TaskListParser.EVENT_COMMAND_FORMAT);
        CommandParser.checkNonEmpty(end, this.paramNames[2], this.commandWord,
                TaskListParser.EVENT_COMMAND_FORMAT);

        // Parse start and end as datetimes
        LocalDateTime startDateTime = parseDateTime(start, this.paramNames[1],
                this.commandWord, TaskListParser.EVENT_COMMAND_FORMAT);
        LocalDateTime endDateTime = parseDateTime(end, this.paramNames[2],
                this.commandWord, TaskListParser.EVENT_COMMAND_FORMAT);

        // Ensure start datetime is not after end datetime
        checkStartNotAfterEnd(startDateTime, endDateTime);

        return new EventCommand(description, startDateTime, endDateTime);
    }

    /**
     * Checks whether a given start datetime is not after an end datetime.
     * If the start datetime is after the end datetime, a BmoException will be thrown.
     *
     * @param startDateTime A parameter extracted from splitting user input.
     * @param endDateTime The name of the parameter.
     * @throws BmoException If startDateTime is after endDateTime
     */
    public static void checkStartNotAfterEnd(LocalDateTime startDateTime, LocalDateTime endDateTime)
            throws BmoException {

        boolean isStartAfterEnd = startDateTime.isAfter(endDateTime);

        if (isStartAfterEnd) {
            throw new BmoException(BmoException.BMO_START_AFTER_END_MESSAGE,
                    BmoException.BMO_START_AFTER_END_SUGGESTION);
        }
    }
}
