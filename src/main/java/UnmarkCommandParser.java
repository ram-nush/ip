public class UnmarkCommandParser extends CommandParser {

    private int totalTasks;

    UnmarkCommandParser(int totalTasks) {
        super("unmark", new String[]{ "index" });
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
