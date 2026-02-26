package bmo.parser;

import bmo.command.ByeCommand;
import bmo.command.Command;

/**
 * Represents a specific command parser which returns the respective command.
 * A <code>ByeCommandParser</code> object corresponds to a parser for a command with
 * the specific CommandWord e.g., <code>CommandWord.BYE</code>.
 */
public class ByeCommandParser extends CommandParser {

    ByeCommandParser() {
        super(CommandWord.BYE);
    }

    /**
     * Returns a Command object created based on the string of parameters.
     *
     * @param parameters A string containing parameters
     *                   corresponding to a ByeCommand object.
     * @return A Command object corresponding to CommandWord.BYE.
     */
    @Override
    public Command parse(String parameters) {
        return new ByeCommand();
    }
}
