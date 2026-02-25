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

    /**
     * Displays a message with the list of tasks retrieved from the save file to the user.
     *
     * @param message The list of tasks which have been retrieved from the save file.
     */
    public void showRetrieveMessage(String message) {
        this.printMessage(retrieveString + "\n" + message);
    }

    /**
     * Displays a welcome message to the user on app startup.
     */
    public void showWelcomeMessage() {
        this.printMessage(welcomeString);
    }

    /**
     * Displays a message with the list of tasks to the user.
     *
     * @param tasks The list of tasks.
     */
    public void showTasks(TaskList tasks) {
        String message = listString + "\n" + tasks.listTasks();
        this.printMessage(message);
    }

    /**
     * Displays a message to the user after adding a task to the task list.
     *
     * @param addTask Task to be added.
     * @param tasks The list of tasks.
     */
    public void showAddMessage(Task addTask, TaskList tasks) {
        this.printMessage("Got it. I've added this task:\n" + addTask
                + "\nNow you have " + tasks.getTotal() + " tasks in the list.");
    }

    /**
     * Displays a message to the user after marking a task as done.
     *
     * @param markTask Task to be marked as done.
     */
    public void showMarkMessage(Task markTask) {
        this.printMessage("Nice! I've marked this task as done:\n" + markTask);
    }

    /**
     * Displays a message to the user after marking a task as not done.
     *
     * @param unmarkTask Task to be marked as not done.
     */
    public void showUnmarkMessage(Task unmarkTask) {
        this.printMessage("OK, I've marked this task as not done yet:\n" + unmarkTask);
    }

    /**
     * Displays a message to the user after deleting a task from the task list.
     *
     * @param deleteTask Task to be deleted from the task list.
     * @param tasks The list of tasks.
     */
    public void showDeleteMessage(Task deleteTask, TaskList tasks) {
        this.printMessage("Noted. I've removed this task:\n" + deleteTask
                + "\nNow you have " + tasks.getTotal() + " tasks in the list.");
    }

    /**
     * Displays an error message to the user when it is thrown.
     */
    public void showErrorMessage(BmoException e) {
        this.printMessage(e.toString());
    }

    /**
     * Displays a default message to the user when an unknown command is entered.
     */
    public void showDefaultMessage() {
        this.printMessage(defaultString);
    }

    /**
     * Displays a message to the user that the current list of tasks have been saved.
     * 
     * @param message The list of tasks which have been saved to the save file.
     */
    public void showSaveMessage(String message) {
        this.printMessage(saveString + "\n" + message);
    }

    /**
     * Displays a closing message to the user on app startup.
     */
    public void showByeMessage() {
        this.printMessage(byeString);
    }
    
    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays a message to the user enclosed by lines.
     * 
     * @param message The text to display to the user.
     */
    public void printMessage(String message) {
        this.showLine();
        System.out.println(message);
        this.showLine();
        System.out.print("\n");
    }

    /**
     * Displays a list of messages to the user, one on each line, enclosed by lines.
     *
     * @param messages The messages to display to the user.
     */
    public void printMessage(List<String> messages) {
        this.showLine();
        for (String message : messages) {
            System.out.println(message);
        }
        this.showLine();
        System.out.print("\n");
    }
}
