import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private List<Task> tasks;
    
    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }
    
    public int getTotal() {
        return this.tasks.size();
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
            listOutput += String.format("%d %s\n", i, this.tasks.get(i - 1));
        }
        return listOutput;
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
