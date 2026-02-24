public class TodoCommandParser extends CommandParser {

    TodoCommandParser() {
        super("todo", new String[]{ "description" });
    }
    
    @Override
    public Command parse(String parameters) throws BmoException {
        String description = parameters;
        
        checkNonEmpty(description, this.paramNames[0], this.commandName, TaskListParser.TODO_COMMAND_FORMAT);
        
        return new TodoCommand(description);
    }
}