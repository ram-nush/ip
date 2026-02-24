public abstract class Command {
    protected String commandWord;
    protected boolean isExit;
    
    Command(String commandWord, boolean isExit) {
        this.commandWord = commandWord;
        this.isExit = isExit;
    }
    
    public boolean isExit() {
        return this.isExit;
    }
    
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws BmoException;
}
