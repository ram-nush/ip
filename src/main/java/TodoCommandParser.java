public class TodoCommandParser implements CommandParser {

    @Override
    public Command parse(String parameters) throws BmoException {
        String description = parameters;

        if (description.isEmpty()) {
            String message = String.format(BmoException.BMO_MISSING_PARAMS_MESSAGE,
                    "description", "todo");
            String suggestion = String.format(BmoException.BMO_MISSING_PARAMS_SUGGESTION,
                    "todo", TaskListParser.TODO_COMMAND_FORMAT);
            throw new BmoException(message, suggestion);
        }

        return new TodoCommand(description);
    }
}