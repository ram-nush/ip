package bmo.parser;

import static bmo.task.Task.REMOVE_SYMBOL_REGEX;

import bmo.command.Command;
import bmo.command.FindCommand;
import bmo.exception.BmoException;

public class FindCommandParser extends CommandParser {

    FindCommandParser() {
        super(CommandWord.FIND, new String[]{ "keyword" });
    }

    /**
     * Returns a corresponding Command object with valid parameters.
     * This method parses the parameters in a string into a list of parameters and ensures their validity.
     * A BmoException may be thrown if the keyword does not contain any alphanumeric character.
     *
     * @param parameters a String that contains parameters entered by the user
     * @return The specified Command object
     * @throws BmoException If the keyword does not contain any alphanumeric character.
     */
    @Override
    public Command parse(String parameters) throws BmoException {
        // Extract keyword parameter
        String keyword = parameters;
        
        // Remove all non-alphanumeric characters
        keyword = keyword.replaceAll(REMOVE_SYMBOL_REGEX, "");

        // Ensure keyword has at least one alphanumeric character
        checkNonEmpty(keyword, this.paramNames[0], this.commandWord, TaskListParser.FIND_COMMAND_FORMAT);

        return new FindCommand(keyword);
    }
}