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
    private static final String RETRIEVE_STRING = "The following tasks have been retrieved:";
    private static final String WELCOME_STRING = "Hello! I'm BMO\nWhat can I do for you?";
    private static final String LIST_STRING = "Here are the tasks in your list:";
    private static final String FIND_STRING = "Here are the matching tasks in your list:";
    private static final String ADD_STRING = "Got it. I've added this task:\n%s\n"
            + "Now you have %d tasks in the list.";
    private static final String MARK_STRING = "Nice! I've marked this task as done:\n%s";
    private static final String UNMARK_STRING = "OK, I've marked this task as not done yet:\n%s";
    private static final String DELETE_STRING = "Noted. I've removed this task:\n%s\n"
            + "Now you have %d tasks in the list.";
    private static final String DEFAULT_STRING = "OOPS!!! I'm sorry, but I don't know what that means :-(";
    private static final String SAVE_STRING = "The following tasks will be saved:";
    private static final String BYE_STRING = "Bye. Hope to see you again soon!";

    /**
     * Displays a message with the list of tasks retrieved from the save file to the user.
     *
     * @param message The list of tasks which have been retrieved from the save file.
     */
    public String showRetrieveMessage(String message) {
        String output = String.format("%s\n%s", RETRIEVE_STRING, message);
        
        this.printMessage(output);
        return output;
    }

    /**
     * Displays a welcome message to the user on app startup.
     */
    public String showWelcomeMessage() {
        String output = WELCOME_STRING;
        
        this.printMessage(output);
        return output;
    }

    /**
     * Displays a message with the list of tasks to the user.
     *
     * @param tasks The list of tasks.
     */
    public String showTasks(TaskList tasks) {
        String output = String.format("%s\n%s", LIST_STRING, tasks.listTasks());
        
        this.printMessage(output);
        return output;
    }

    /**
     * Displays a message with the list of tasks that match
     * a specific keyword, to the user.
     *
     * @param tasks The list of tasks.
     */
    public String showMatchingTasks(TaskList tasks) {
        String output = String.format("%s\n%s", FIND_STRING, tasks.listTasks());
        
        this.printMessage(output);
        return output;
    }

    /**
     * Displays a message to the user after adding a task to the task list.
     *
     * @param addTask Task to be added.
     * @param tasks The list of tasks.
     */
    public String showAddMessage(Task addTask, TaskList tasks) {
        String output = String.format(ADD_STRING, addTask, tasks.getTotal());
        
        this.printMessage(output);
        return output;
    }

    /**
     * Displays a message to the user after marking a task as done.
     *
     * @param markTask Task to be marked as done.
     */
    public String showMarkMessage(Task markTask) {
        String output = String.format(MARK_STRING, markTask);
        
        this.printMessage(output);
        return output;
    }

    /**
     * Displays a message to the user after marking a task as not done.
     *
     * @param unmarkTask Task to be marked as not done.
     */
    public String showUnmarkMessage(Task unmarkTask) {
        String output = String.format(UNMARK_STRING, unmarkTask);
        
        this.printMessage(output);
        return output;
    }

    /**
     * Displays a message to the user after deleting a task from the task list.
     *
     * @param deleteTask Task to be deleted from the task list.
     * @param tasks The list of tasks.
     */
    public String showDeleteMessage(Task deleteTask, TaskList tasks) {
        String output = String.format(DELETE_STRING, deleteTask, tasks.getTotal());
        
        this.printMessage(output);
        return output;
    }

    /**
     * Displays an error message to the user when it is thrown.
     */
    public String showErrorMessage(BmoException e) {
        String output = e.toString();
        
        this.printMessage(output);
        return output;
    }

    /**
     * Displays a default message to the user when an unknown command is entered.
     */
    public String showDefaultMessage() {
        String output = DEFAULT_STRING;
        
        this.printMessage(output);
        return output;
    }

    /**
     * Displays a message to the user that the current list of tasks have been saved.
     *
     * @param message The list of tasks which have been saved to the save file.
     */
    public String showSaveMessage(String message) {
        String output = String.format("%s\n%s", SAVE_STRING, message);
        
        this.printMessage(output);
        return output;
    }

    /**
     * Displays a closing message to the user on app startup.
     */
    public String showByeMessage() {
        String output = BYE_STRING;
        
        this.printMessage(output);
        return output;
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
