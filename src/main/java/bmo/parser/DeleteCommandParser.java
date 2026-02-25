package bmo.parser;

import bmo.command.Command;
import bmo.command.DeleteCommand;
import bmo.exception.BmoException;

public class DeleteCommandParser extends CommandParser {

    private int totalTasks;

    DeleteCommandParser(int totalTasks) {
        super(CommandWord.DELETE, new String[]{ "index" });
        this.totalTasks = totalTasks;
    }

    @Override
    public Command parse(String parameters) throws BmoException {
        // Extract index parameter
        String deleteIndex = parameters;

        // Ensure index is a valid parameter
        CommandParser.checkNonEmpty(deleteIndex, this.paramNames[0], this.commandWord, TaskListParser.DELETE_COMMAND_FORMAT);
        int index = CommandParser.parseInteger(deleteIndex);
        CommandParser.checkIntegerInRange(index, this.totalTasks);

        return new DeleteCommand(index);
    }
}
