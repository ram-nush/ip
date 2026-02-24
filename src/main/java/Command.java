public abstract class Command {
    protected String commandType;
    protected boolean isExit;
    
    Command(String commandType, boolean isExit) {
        this.commandType = commandType;
        this.isExit = isExit;
    }
    
    public boolean isExit() {
        return this.isExit;
    }
    
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws BmoException;
}
