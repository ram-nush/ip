public class ByeCommand extends Command {

    ByeCommand() {
        super("bye", true);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BmoException {
        String saveText = tasks.saveString();
        ui.showSaveMessage(saveText);
        // can throw BmoException
        storage.save(saveText);
    }
}
