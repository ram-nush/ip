package bmo.parser;

import java.time.LocalDateTime;

import bmo.command.Command;
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

        return new EventCommand(description, startDateTime, endDateTime);
    }
}