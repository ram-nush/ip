package bmo.command;

import bmo.parser.CommandWord;
import bmo.storage.Storage;
import bmo.task.TaskList;
import bmo.ui.Ui;

public class ListCommand extends Command {

    public ListCommand() {
        super(CommandWord.LIST, false);
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTasks(tasks);
    }
}
