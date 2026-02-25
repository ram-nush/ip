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
    
    @Override
    public Command parse(String parameters) throws BmoException {
        String unmarkIndex = parameters;
        
        CommandParser.checkNonEmpty(unmarkIndex, this.paramNames[0], this.commandWord, TaskListParser.UNMARK_COMMAND_FORMAT);
        int index = CommandParser.parseInteger(unmarkIndex);
        CommandParser.checkIntegerInRange(index, this.totalTasks);

        return new UnmarkCommand(index);
    }
}
