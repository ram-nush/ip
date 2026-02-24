public class UnmarkCommandParser implements CommandParser {

    private int totalTasks;

    UnmarkCommandParser(int numTasks) {
        this.totalTasks = numTasks;
    }

    @Override
    public Command parse(String parameters) throws BmoException {
        // can throw BmoException, handle in Bmo
        CommandParser.checkNonEmpty(parameters, "index", "unmark", TaskListParser.UNMARK_COMMAND_FORMAT);
        int index = CommandParser.parseInteger(parameters);
        CommandParser.checkIntegerInRange(index, this.totalTasks);

        return new UnmarkCommand(index);
    }
}
