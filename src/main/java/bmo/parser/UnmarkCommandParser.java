package bmo.parser;

import bmo.command.Command;
import bmo.command.UnmarkCommand;
import bmo.exception.BmoException;

/**
 * Represents a specific command parser which returns the respective command.
 * An <code>UnmarkCommandParser</code> object corresponds to a parser for a command with
 * the specific CommandWord e.g., <code>CommandWord.UNMARK</code>.
 */
public class UnmarkCommandParser extends CommandParser {

    private int totalTasks;

    UnmarkCommandParser(int totalTasks) {
        super(CommandWord.UNMARK, new String[]{ "index" });
        this.totalTasks = totalTasks;
    }

    /**
     * Parses a given string to get parameters corresponding to a command type.
     * Returns a Command object created based on the parameters.
     * Throws a BmoException if not all parameters can be extracted.
     *
     * @param parameters A string containing parameters
     *                   corresponding to an UnmarkCommand object.
     * @return A Command object corresponding to CommandWord.UNMARK.
     * @throws BmoException If parameters does not contain all parameters
     *                      specific to UnmarkCommand.
     */
    @Override
    public Command parse(String parameters) throws BmoException {
        // Extract index parameter
        String unmarkIndex = parameters;

        // Ensure index is a valid parameter
        CommandParser.checkNonEmpty(unmarkIndex, this.paramNames[0], this.commandWord,
                TaskListParser.UNMARK_COMMAND_FORMAT);
        int index = CommandParser.parseInteger(unmarkIndex);
        CommandParser.checkIntegerInRange(index, this.totalTasks);

        return new UnmarkCommand(index);
    }
}
