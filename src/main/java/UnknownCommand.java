public class UnknownCommand extends Command {

    private String unknownInput;
    
    UnknownCommand(String unknownInput) {
        super(CommandWord.UNKNOWN, false);
        this.unknownInput = unknownInput;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BmoException {
        ui.showDefaultMessage();
        String commandFormats = String.join("\n", TaskListParser.COMMAND_FORMATS);
        String message = String.format(BmoException.BMO_INVALID_COMMAND_MESSAGE, this.unknownInput);
        String suggestion = String.format(BmoException.BMO_INVALID_COMMAND_SUGGESTION, commandFormats);
        throw new BmoException(message, suggestion);
    }
}
