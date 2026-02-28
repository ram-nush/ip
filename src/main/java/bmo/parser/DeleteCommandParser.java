package bmo.parser;

import bmo.command.Command;
import bmo.command.CommandWord;
import bmo.command.DeleteCommand;
import bmo.exception.BmoException;

/**
 * Represents a specific command parser which returns the respective command.
 * A <code>DeleteCommandParser</code> object corresponds to a parser for a command with
 * the specific CommandWord e.g., <code>CommandWord.DEADLINE</code>.
 */
public class DeleteCommandParser extends CommandParser {

    private int totalTasks;

    DeleteCommandParser(int totalTasks) {
        super(CommandWord.DELETE, new String[]{ "index" });
        this.totalTasks = totalTasks;
    }

    /**
     * Parses a given string to get parameters corresponding to a command type.
     * Returns a Command object created based on the parameters.
     * Throws a BmoException if not all parameters can be extracted.
     *
     * @param parameters A string containing parameters
     *                   corresponding to a DeleteCommand object.
     * @return A Command object corresponding to CommandWord.DELETE.
     * @throws BmoException If parameters does not contain all parameters
     *                      specific to DeleteCommand.
     */
    @Override
    public Command parse(String parameters) throws BmoException {
        // Extract index parameter
        String deleteIndex = parameters;

        // Ensure index is a valid parameter
        CommandParser.checkNonEmpty(deleteIndex, this.paramNames[0], this.commandWord,
                TaskListParser.DELETE_COMMAND_FORMAT);
        int index = CommandParser.parseInteger(deleteIndex);
        CommandParser.checkIntegerInRange(index, this.totalTasks);

        return new DeleteCommand(index);
    }
}
