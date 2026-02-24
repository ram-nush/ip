package bmo.command;

import java.time.LocalDateTime;

import bmo.parser.CommandWord;
import bmo.storage.Storage;
import bmo.task.Deadline;
import bmo.task.Task;
import bmo.task.TaskList;
import bmo.ui.Ui;

public class DeadlineCommand extends Command {

    private String description;
    private LocalDateTime due;

    public DeadlineCommand(String description, LocalDateTime due) {
        super(CommandWord.DEADLINE, false);
        this.description = description;
        this.due = due;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task deadlineTask = new Deadline(this.description, this.due);
        tasks.addTask(deadlineTask);
        ui.showAddMessage(deadlineTask, tasks);
    }
}
