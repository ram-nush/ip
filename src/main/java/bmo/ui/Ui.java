package bmo.ui;

import java.util.List;

import bmo.exception.BmoException;
import bmo.task.Task;
import bmo.task.TaskList;

/**
 * Represents the user interface of the program. A <code>Ui</code> object 
 * displays messages based on the respective commands entered. It also displays  
 * error messages when unexpected events occur e.g., user enters an unknown command
 */
public class Ui {
    private static String retrieveString;
    private static String welcomeString;
    private static String listString;
    private static String defaultString;
    private static String saveString;
    private static String byeString;
    
    public Ui() {
        retrieveString = "The following tasks have been retrieved:";
        welcomeString = "Hello! I'm BMO\n" + "What can I do for you?";
        listString = "Here are the tasks in your list:";
        defaultString = "OOPS!!! I'm sorry, but I don't know what that means :-(";
        saveString = "The following tasks will be saved:";
        byeString = "Bye. Hope to see you again soon!";
    }

    public void showRetrieveMessage(String message) {
        this.printMessage(retrieveString + "\n" + message);
    }
    
    public void showWelcomeMessage() {
        this.printMessage(welcomeString);
    }
    
    public void showTasks(TaskList tasks) {
        String message = listString + "\n" + tasks.listTasks();
        this.printMessage(message);
    }
    
    public void showAddMessage(Task addTask, TaskList tasks) {
        this.printMessage("Got it. I've added this task:\n" + addTask
                + "\nNow you have " + tasks.getTotal() + " tasks in the list.");
    }

    public void showMarkMessage(Task markTask) {
        this.printMessage("Nice! I've marked this task as done:\n" + markTask);
    }

    public void showUnmarkMessage(Task unmarkTask) {
        this.printMessage("OK, I've marked this task as not done yet:\n" + unmarkTask);
    }
    
    public void showDeleteMessage(Task deleteTask, TaskList tasks) {
        this.printMessage("Noted. I've removed this task:\n" + deleteTask
                + "\nNow you have " + tasks.getTotal() + " tasks in the list.");
    }
    
    public void showErrorMessage(BmoException e) {
        this.printMessage(e.toString());
    }

    public void showDefaultMessage() {
        this.printMessage(defaultString);
    }
    
    public void showSaveMessage(String message) {
        this.printMessage(saveString + "\n" + message);
    }
    
    public void showByeMessage() {
        this.printMessage(byeString);
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
