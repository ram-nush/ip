package bmo.parser;

import bmo.command.Command;
import bmo.command.CommandWord;
import bmo.command.TodoCommand;
import bmo.exception.BmoException;

/**
 * Represents a specific command parser which returns the respective command.
 * A <code>TodoCommandParser</code> object corresponds to a parser for a command with
 * the specific CommandWord e.g., <code>CommandWord.TODO</code>.
 */
public class TodoCommandParser extends CommandParser {

    TodoCommandParser() {
        super(CommandWord.TODO, new String[]{ "description" });
    }

    /**
     * Parses a given string to get parameters corresponding to a command type.
     * Returns a Command object created based on the parameters.
     * Throws a BmoException if not all parameters can be extracted.
     *
     * @param parameters A string containing parameters
     *                   corresponding to a TodoCommand object.
     * @return A Command object corresponding to CommandWord.TODO.
     * @throws BmoException If parameters does not contain all parameters
     *                      specific to TodoCommand.
     */
    @Override
    public Command parse(String parameters) throws BmoException {
        // Extract description parameter
        String description = parameters;

        // Ensure description is a valid parameter
        CommandParser.checkNonEmpty(description, this.paramNames[0], this.commandWord,
                TaskListParser.TODO_COMMAND_FORMAT);

        return new TodoCommand(description);
    }
}
