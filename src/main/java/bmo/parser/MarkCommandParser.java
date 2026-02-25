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
    
    @Override
    public Command parse(String parameters) throws BmoException {
        String markIndex = parameters;
        
        CommandParser.checkNonEmpty(markIndex, this.paramNames[0], this.commandWord, TaskListParser.MARK_COMMAND_FORMAT);
        int index = CommandParser.parseInteger(markIndex);
        CommandParser.checkIntegerInRange(index, this.totalTasks);
        
        return new MarkCommand(index);
    }
}
