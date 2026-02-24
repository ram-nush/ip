package bmo.command;

import bmo.parser.CommandWord;
import bmo.storage.Storage;
import bmo.task.Task;
import bmo.task.TaskList;
import bmo.ui.Ui;

public class DeleteCommand extends Command {

    private int index;

    public DeleteCommand(int index) {
        super(CommandWord.DELETE, false);
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task deleteTask = tasks.deleteTask(this.index);
        ui.showDeleteMessage(deleteTask, tasks);
    }
}
