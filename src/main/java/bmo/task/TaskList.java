package bmo.task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Represents the list of tasks stored by the program. A <code>TaskList</code> object
 * corresponds to a list of <code>Task</code> objects stored as a <code>List</code>
 */
public class TaskList {
    private List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the number of tasks currently in the task list.
     *
     * @return Total number of tasks.
     */
    public int getTotal() {
        return this.tasks.size();
    }

    /**
     * Returns true if there are currently no tasks, false otherwise.
     *
     * @return Whether the list is empty or not.
     */
    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }

    /**
     * Returns true if a valid task number is given, false otherwise.
     *
     * @param taskNumber Index of a task in the task list.
     * @return Whether taskNumber is between 1 and the total number of tasks (both inclusive).
     */
    public boolean isInRange(int taskNumber) {
        return taskNumber >= 1 && taskNumber <= this.getTotal();
    }

    /**
     * Adds a given task to the task list.
     *
     * @param newTask Task to be added to the task list.
     */
    public void addTask(Task newTask) {
        this.tasks.add(newTask);
    }

    /**
     * Marks a task corresponding to the index given as done.
     * Returns the marked task.
     *
     * @param index Index of a task in the task list.
     * @return A task corresponding to the index given, marked as done.
     */
    public Task markTask(int index) {
        assert index >= 1 && index <= this.getTotal() : "Task index out of range";

        Task markTask = this.tasks.get(index - 1);
        markTask.markAsDone();
        return markTask;
    }

    /**
     * Marks a task corresponding to the index given as not done.
     * Returns the task marked as not done.
     *
     * @param index Index of a task in the task list.
     * @return A task corresponding to the index given, marked as not done.
     */
    public Task unmarkTask(int index) {
        assert index >= 1 && index <= this.getTotal() : "Task index out of range";

        Task unmarkTask = this.tasks.get(index - 1);
        unmarkTask.markAsNotDone();
        return unmarkTask;
    }

    /**
     * Deletes a task corresponding to the index given.
     * Returns the deleted task.
     *
     * @param index Index of a task in the task list.
     * @return A task corresponding to the index given.
     */
    public Task deleteTask(int index) {
        assert index >= 1 && index <= this.getTotal() : "Task index out of range";

        Task deleteTask = this.tasks.get(index - 1);
        this.tasks.remove(index - 1);
        return deleteTask;
    }

    /**
     * Returns a formatted string representing a numbered list of tasks,
     * along with their information.
     *
     * @return A string storing all tasks displayed to the user.
     */
    public String listTasks() {
        return IntStream.rangeClosed(1, this.getTotal())
                .mapToObj(i -> String.format("%d. %s", i, this.tasks.get(i - 1)))
                .reduce("", (s1, s2) -> String.format("%s\n%s", s1, s2));
    }

    /**
     * Returns a <code>TaskList</code> consisting of the tasks which contain the provided keyword.
     * This match is case-insensitive and ignores non-alphanumeric characters.
     *
     * @param keyword a String that represents the keyword to match
     * @return the new <code>TaskList</code>
     */
    public TaskList listMatchingTasks(String keyword) {
        // Filter the list for tasks which contain the keyword
        List<Task> matchingTasks = this.tasks.stream()
                .filter(task -> task.hasMatch(keyword))
                .toList();

        // Create a TaskList using the filtered list
        return new TaskList(matchingTasks);
    }

    /**
     * Returns a formatted string with task properties of each task in the task list,
     * to be saved in the save file.
     *
     * @return A string matching the save format of all tasks in the task list.
     */
    public String saveString() {
        return this.tasks.stream()
                .map(task -> task.saveString())
                .reduce("", (s1, s2) -> String.format("%s\n%s", s1, s2));
    }

    /**
     * Returns a formatted string with task properties of each task in the task list,
     * to be displayed to the user.
     *
     * @return A string matching the displayed format of all tasks in the task list.
     */
    @Override
    public String toString() {
        return this.tasks.stream()
                .map(task -> task.toString())
                .reduce("", (s1, s2) -> String.format("%s\n%s", s1, s2));
    }
}
