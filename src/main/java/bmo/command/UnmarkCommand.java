package bmo.command;

import bmo.parser.CommandWord;
import bmo.storage.Storage;
import bmo.task.Task;
import bmo.task.TaskList;
import bmo.ui.Ui;

public class UnmarkCommand extends Command {

    private int index;

    public UnmarkCommand(int index) {
        super(CommandWord.UNMARK,false);
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task unmarkTask = tasks.unmarkTask(this.index);
        ui.showUnmarkMessage(unmarkTask);
    }
}
