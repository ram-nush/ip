public abstract class Command {
    protected String commandName;
    protected boolean isExit;
    
    Command(String commandName, boolean isExit) {
        this.commandName = commandName;
        this.isExit = isExit;
    }
    
    public boolean isExit() {
        return this.isExit;
    }
    
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws BmoException;
}
