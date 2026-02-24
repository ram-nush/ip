public abstract class Command {
    protected CommandWord commandWord;
    protected boolean isExit;
    
    Command(CommandWord commandWord, boolean isExit) {
        this.commandWord = commandWord;
        this.isExit = isExit;
    }
    
    public boolean isExit() {
        return this.isExit;
    }
    
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws BmoException;
}
