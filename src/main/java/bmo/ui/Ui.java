package bmo.ui;

import java.util.List;

import bmo.exception.BmoException;
import bmo.task.Task;
import bmo.task.TaskList;

public class Ui {
    private static final String RETRIEVE_STRING = "The following tasks have been retrieved:";
    private static final String WELCOME_STRING = "Hello! I'm BMO\nWhat can I do for you?";
    private static final String LIST_STRING = "Here are the tasks in your list:";
    private static final String DEFAULT_STRING = "OOPS!!! I'm sorry, but I don't know what that means :-(";
    private static final String SAVE_STRING = "The following tasks will be saved:";
    private static final String BYE_STRING = "Bye. Hope to see you again soon!";
    
    public void showRetrieveMessage(String message) {
        String output = String.format("%s\n%s", RETRIEVE_STRING, message);
        this.printMessage(output);
    }
    
    public void showWelcomeMessage() {
        this.printMessage(WELCOME_STRING);
    }
    
    public void showTasks(TaskList tasks) {
        String output = String.format("%s\n%s", LIST_STRING, tasks.listTasks());
        this.printMessage(output);
    }
    
    public void showAddMessage(Task addTask, TaskList tasks) {
        String output = String.format("Got it. I've added this task:\n%s\n" 
        + "Now you have %d tasks in the list.", addTask, tasks.getTotal());
        this.printMessage(output);
    }

    public void showMarkMessage(Task markTask) {
        String output = String.format("Nice! I've marked this task as done:\n%s", markTask);
        this.printMessage(output);
    }

    public void showUnmarkMessage(Task unmarkTask) {
        String output = String.format("OK, I've marked this task as not done yet:\n%s", unmarkTask);
        this.printMessage(output);
    }
    
    public void showDeleteMessage(Task deleteTask, TaskList tasks) {
        String output = String.format("Noted. I've removed this task:\n%s\n"
                + "Now you have %d tasks in the list.", deleteTask, tasks.getTotal());
        this.printMessage(output);
    }
    
    public void showErrorMessage(BmoException e) {
        this.printMessage(e.toString());
    }

    public void showDefaultMessage() {
        this.printMessage(DEFAULT_STRING);
    }
    
    public void showSaveMessage(String message) {
        String output = String.format("%s\n%s", SAVE_STRING, message);
        this.printMessage(output);
    }
    
    public void showByeMessage() {
        this.printMessage(BYE_STRING);
    }
    
    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public void printMessage(String message) {
        this.showLine();
        System.out.println(message);
        this.showLine();
        System.out.print("\n");
    }

    public void printMessage(List<String> messages) {
        this.showLine();
        for (String message : messages) {
            System.out.println(message);
        }
        this.showLine();
        System.out.print("\n");
    }
}
