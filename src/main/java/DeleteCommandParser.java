public class DeleteCommandParser extends CommandParser {

    private int totalTasks;

    DeleteCommandParser(int totalTasks) {
        super("delete", new String[]{ "index" });
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
