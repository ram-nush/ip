package bmo.command;

import bmo.parser.CommandWord;
import bmo.storage.Storage;
import bmo.task.Task;
import bmo.task.TaskList;
import bmo.task.Todo;
import bmo.ui.Ui;

public class FindCommand extends Command {

    private String keyword;

    public FindCommand(String keyword) {
        super(CommandWord.FIND, false);
        this.keyword = keyword;
    }

    /**
     * Performs an action on the TaskList to display a filtered list of tasks 
     * which contain the keyword.
     *
     * @param tasks a TaskList object with the full list of tasks
     * @param ui a user interface object
     * @param storage a storage object
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        // Get a TaskList consisting of tasks which contain the keyword
        TaskList filteredTasks = tasks.listMatchingTasks(this.keyword);
        
        // List the tasks inside the new TaskList
        ui.showMatchingTasks(filteredTasks);
    }
}
