package bmo.command;

import bmo.parser.CommandWord;
import bmo.storage.Storage;
import bmo.task.Task;
import bmo.task.TaskList;
import bmo.task.Todo;
import bmo.ui.Ui;

public class TodoCommand extends Command {

    private String description;

    public TodoCommand(String description) {
        super(CommandWord.TODO, false);
        this.description = description;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        Task todoTask = new Todo(this.description);
        
        taskList.addTask(todoTask);
        
        ui.showAddMessage(todoTask, taskList);
    }
}
