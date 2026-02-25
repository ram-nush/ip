package bmo.task;

import java.util.ArrayList;
import java.util.List;

import bmo.exception.BmoException;

public class TaskList {
    private List<Task> tasks;
    
    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }
    
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }
    
    public int getTotal() {
        return this.tasks.size();
    }
    
    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }
    
    public boolean isInRange(int taskNumber) {
        return taskNumber >= 1 && taskNumber <= this.getTotal();
    }
    
    public void addTask(Task newTask) {
        this.tasks.add(newTask);
    }
    
    public Task markTask(int index) {
        Task markTask = this.tasks.get(index - 1);
        markTask.markAsDone();
        return markTask;
    }
    
    public Task unmarkTask(int index) {
        Task unmarkTask = this.tasks.get(index - 1);
        unmarkTask.markAsNotDone();
        return unmarkTask;
    }
    
    public Task deleteTask(int index) {
        Task deleteTask = this.tasks.get(index - 1);
        this.tasks.remove(index - 1);
        return deleteTask;
    }
    
    public String listTasks() {
        String listOutput = "";
        for (int i = 1; i <= this.tasks.size(); i++) {
            listOutput += String.format("%d. %s\n", i, this.tasks.get(i - 1));
        }
        return listOutput;
    }

    /**
     * Returns a numbered list of tasks containing the provided keyword.
     * This match is case-insensitive and ignores non-alphanumeric characters.
     *
     * @param keyword a String that represents the keyword to match
     * @return the String corresponding to the numbered list of tasks which contain the keyword
     */
    public String listMatchingTasks(String keyword) {
        // Filter the list for tasks which contain the keyword
        List<Task> matchingTasks = this.tasks.stream()
                .filter(task -> task.hasMatch(keyword))
                .toList();
        
        // Create a TaskList using the filtered list
        TaskList filteredTaskList = new TaskList(matchingTasks);
        
        // List the tasks inside the new TaskList
        return filteredTaskList.listTasks();
    }
    
    public String saveString() {
        String saveOutput = "";
        for (Task task : this.tasks) {
            saveOutput += task.saveString() + "\n";
        }
        return saveOutput;
    }
    
    @Override
    public String toString() {
        String stringOutput = "";
        for (Task task : this.tasks) {
            stringOutput += task.toString() + "\n";
        }
        return stringOutput;
    }
}
