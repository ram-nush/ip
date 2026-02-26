package bmo.parser;

import bmo.command.Command;
import bmo.command.ListCommand;

/**
 * Represents a specific command parser which returns the respective command.
 * A <code>ListCommandParser</code> object corresponds to a parser for a command with
 * the specific CommandWord e.g., <code>CommandWord.LIST</code>.
 */
public class ListCommandParser extends CommandParser {

    ListCommandParser() {
        super(CommandWord.LIST);
    }

    /**
     * Returns a Command object created based on the string of parameters.
     *
     * @param parameters A string containing parameters
     *                   corresponding to a ListCommand object.
     * @return A Command object corresponding to CommandWord.LIST.
     */
    @Override
    public Command parse(String parameters) {
        return new ListCommand();
    }
}
