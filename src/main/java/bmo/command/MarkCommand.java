package bmo.command;

import bmo.parser.CommandWord;
import bmo.storage.Storage;
import bmo.task.Task;
import bmo.task.TaskList;
import bmo.ui.Ui;

public class MarkCommand extends Command {
    
    private int index;

    public MarkCommand(int index) {
        super(CommandWord.MARK, false);
        this.index = index;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        Task markTask = taskList.markTask(this.index);
        
        ui.showMarkMessage(markTask);
    }
}
