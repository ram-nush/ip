public class DeleteCommandParser implements CommandParser {

    private int totalTasks;

    DeleteCommandParser(int numTasks) {
        this.totalTasks = numTasks;
    }

    @Override
    public Command parse(String parameters) throws BmoException {
        // can throw BmoException, handle in Bmo
        CommandParser.checkNonEmpty(parameters, "index", "delete", TaskListParser.DELETE_COMMAND_FORMAT);
        int index = CommandParser.parseInteger(parameters);
        CommandParser.checkIntegerInRange(index, this.totalTasks);

        return new DeleteCommand(index);
    }
}
