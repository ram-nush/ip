package bmo.command;

import java.time.LocalDateTime;

import bmo.parser.CommandWord;
import bmo.storage.Storage;
import bmo.task.Event;
import bmo.task.Task;
import bmo.task.TaskList;
import bmo.ui.Ui;

/**
 * Represents a command which adds an event task to the list of tasks. An 
 * <code>EventCommand</code> object corresponds to a command which stores the 
 * EVENT CommandWord e.g., <code>CommandWord.EVENT</code>, a description e.g., 
 * <code>project meeting</code>, a start date and time, and an end date and time for 
 * the task e.g., <code>5-3-2026 1800</code>
 */
public class EventCommand extends Command {

    private String description;
    private LocalDateTime start;
    private LocalDateTime end;

    public EventCommand(String description, LocalDateTime start, LocalDateTime end) {
        super(CommandWord.EVENT, false);
        this.description = description;
        this.start = start;
        this.end = end;
    }

    /**
     * Creates an Event object.
     * Adds the Task to the task list.
     * Displays the information to the user.
     *
     * @param tasks The list of tasks to be saved.
     * @param ui The user interface object.
     * @param storage The storage object.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        Task eventTask = new Event(this.description, this.start, this.end);
        
        taskList.addTask(eventTask);
        
        ui.showAddMessage(eventTask, taskList);
    }
}
