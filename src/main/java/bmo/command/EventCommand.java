package bmo.command;

import java.time.LocalDateTime;

import bmo.parser.CommandWord;
import bmo.storage.Storage;
import bmo.task.Event;
import bmo.task.Task;
import bmo.task.TaskList;
import bmo.ui.Ui;

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

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        Task eventTask = new Event(this.description, this.start, this.end);
        
        taskList.addTask(eventTask);
        
        ui.showAddMessage(eventTask, taskList);
    }
}
