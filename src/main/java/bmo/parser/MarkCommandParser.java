package bmo.parser;

import bmo.command.Command;
import bmo.command.MarkCommand;
import bmo.exception.BmoException;

/**
 * Represents a specific command parser which returns the respective command.
 * A <code>MarkCommandParser</code> object corresponds to a parser for a command with
 * the specific CommandWord e.g., <code>CommandWord.MARK</code>. 
 */
public class MarkCommandParser extends CommandParser {
    
    private int totalTasks;
    
    MarkCommandParser(int totalTasks) {
        super(CommandWord.MARK, new String[]{ "index" });
        this.totalTasks = totalTasks;
    }

    /**
     * Parses a given string to get parameters corresponding to a command type.
     * Returns a Command object created based on the parameters.
     * Throws a BmoException if not all parameters can be extracted.
     *
     * @param parameters A string containing parameters 
     *                   corresponding to a MarkCommand object.
     * @return A Command object corresponding to CommandWord.MARK.
     * @throws BmoException If parameters does not contain all parameters 
     *                      specific to MarkCommand.
     */
    @Override
    public Command parse(String parameters) throws BmoException {
        // Extract index parameter
        String markIndex = parameters;
        
        // Ensure index is a valid parameter
        CommandParser.checkNonEmpty(markIndex, this.paramNames[0], this.commandWord, 
                TaskListParser.MARK_COMMAND_FORMAT);
        int index = CommandParser.parseInteger(markIndex);
        CommandParser.checkIntegerInRange(index, this.totalTasks);
        
        return new MarkCommand(index);
    }
}
