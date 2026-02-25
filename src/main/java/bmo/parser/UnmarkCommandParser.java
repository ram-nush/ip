package bmo.parser;

import bmo.command.Command;
import bmo.command.UnmarkCommand;
import bmo.exception.BmoException;

public class UnmarkCommandParser extends CommandParser {

    private int totalTasks;

    UnmarkCommandParser(int totalTasks) {
        super(CommandWord.UNMARK, new String[]{ "index" });
        this.totalTasks = totalTasks;
    }
    
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
