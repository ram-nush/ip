public class InvalidCommand extends Command {

    InvalidCommand(String commandType) {
        super(commandType, false);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BmoException {
        ui.showDefaultMessage();
        String commandFormats = String.join("\n", TaskListParser.COMMAND_FORMATS);
        String message = String.format(BmoException.BMO_INVALID_COMMAND_MESSAGE, super.commandType);
        String suggestion = String.format(BmoException.BMO_INVALID_COMMAND_SUGGESTION, commandFormats);
        throw new BmoException(message, suggestion);
    }
}
