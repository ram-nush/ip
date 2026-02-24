public class MarkCommandParser implements CommandParser {

    private int totalTasks;
    
    MarkCommandParser(int numTasks) {
        this.totalTasks = numTasks;
    }
    
    @Override
    public Command parse(String parameters) throws BmoException {
        // can throw BmoException, handle in Bmo
        CommandParser.checkNonEmpty(parameters, "index", "mark", Parser.MARK_COMMAND_FORMAT);
        int index = CommandParser.parseInteger(parameters);
        CommandParser.checkIntegerInRange(index, this.totalTasks);
        
        return new MarkCommand(index);
    }
}
