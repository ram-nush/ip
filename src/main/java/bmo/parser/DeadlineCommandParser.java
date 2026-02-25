package bmo.parser;

import java.time.LocalDateTime;

import bmo.command.Command;
import bmo.command.DeadlineCommand;
import bmo.exception.BmoException;

/**
 * Represents a specific command parser which returns the respective command.
 * A <code>DeadlineCommandParser</code> object corresponds to a parser for a command with
 * the specific CommandWord e.g., <code>CommandWord.DEADLINE</code>. 
 */
public class DeadlineCommandParser extends CommandParser {

    DeadlineCommandParser() {
        super(CommandWord.DEADLINE, new String[]{ "/by" }, new String[]{ "description", "due" });
    }

    /**
     * Parses a given string to get parameters corresponding to a command type.
     * Returns a Command object created based on the parameters.
     * Throws a BmoException if not all parameters can be extracted.
     *
     * @param parameters A string containing parameters 
     *                   corresponding to a DeadlineCommand object.
     * @return A Command object corresponding to CommandWord.DEADLINE.
     * @throws BmoException If parameters does not contain all parameters 
     *                      specific to DeadlineCommand.
     */
    @Override
    public Command parse(String parameters) throws BmoException {
        // Split parameters into a parameter array
        String[] parts = CommandParser.splitParameters(parameters, this.delimiters);
        
        // Extract individual parameters
        String description = parts[0];
        String due = parts[1];

        // Ensure all parameters are non-empty
        CommandParser.checkNonEmpty(description, this.paramNames[0], this.commandWord, 
                TaskListParser.DEADLINE_COMMAND_FORMAT);
        CommandParser.checkNonEmpty(due, this.paramNames[1], this.commandWord, 
                TaskListParser.DEADLINE_COMMAND_FORMAT);
        
        // Parse due as a datetime
        LocalDateTime dueDateTime = parseDateTime(due, this.paramNames[1], 
            this.commandWord, TaskListParser.DEADLINE_COMMAND_FORMAT);

        return new DeadlineCommand(description, dueDateTime);
    }
}