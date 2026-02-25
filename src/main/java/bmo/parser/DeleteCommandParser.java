package bmo.parser;

import bmo.command.Command;
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

    @Override
    public Command parse(String parameters) throws BmoException {
        String deleteIndex = parameters;
        
        CommandParser.checkNonEmpty(deleteIndex, this.paramNames[0], this.commandWord, TaskListParser.DELETE_COMMAND_FORMAT);
        int index = CommandParser.parseInteger(deleteIndex);
        CommandParser.checkIntegerInRange(index, this.totalTasks);

        return new DeleteCommand(index);
    }
}
