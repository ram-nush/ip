public class InvalidCommand extends Command {

    private String commandType;
    
    InvalidCommand(String commandType) {
        super(false);
        this.commandType = commandType;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BmoException {
        ui.showDefaultMessage();
        String commandFormats = String.join("\n", Parser.COMMAND_FORMATS);
        String message = String.format(BmoException.BMO_INVALID_COMMAND_MESSAGE, this.commandType);
        String suggestion = String.format(BmoException.BMO_INVALID_COMMAND_SUGGESTION, commandFormats);
        throw new BmoException(message, suggestion);
    }
}
