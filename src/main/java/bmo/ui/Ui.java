package bmo.ui;

import java.util.List;

import bmo.exception.BmoException;
import bmo.task.Task;
import bmo.task.TaskList;

/**
 * Represents the response formatter of the program. A <code>Ui</code> object
 * formats messages based on the respective commands entered. It also crafts
 * relevant error messages when unexpected events occur e.g., user enters an
 * unknown command.
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
     * Returns a formatted message with the list of tasks retrieved
     * from the save file to the user.
     *
     * @param message The list of tasks which have been retrieved from the save file.
     * @return A formatted message with the list of tasks.
     */
    public String getRetrieveMessage(String message) {
        return String.format("%s\n%s", RETRIEVE_STRING, message);
    }

    /**
     * Returns a welcome message to the user on app startup.
     *
     * @return A message greeting the user.
     */
    public String getWelcomeMessage() {
        return WELCOME_STRING;
    }

    /**
     * Returns a formatted message with the list of tasks to the user.
     *
     * @param tasks The list of tasks.
     * @return A formatted message with the list of tasks.
     */
    public String getTasks(TaskList tasks) {
        return String.format("%s\n%s", LIST_STRING, tasks.listTasks());
    }

    /**
     * Returns a formatted message with the list of tasks that match
     * a specific keyword, to the user.
     *
     * @param tasks The list of tasks.
     * @return A formatted message with the list of tasks matching the keyword.
     */
    public String getMatchingTasks(TaskList tasks) {
        return String.format("%s\n%s", FIND_STRING, tasks.listTasks()).trim();
    }

    /**
     * Returns a formatted message to the user after adding a task to the task list.
     *
     * @param addTask Task to be added.
     * @param tasks The list of tasks.
     * @return A formatted message to the user after their task has been added.
     */
    public String getAddMessage(Task addTask, TaskList tasks) {
        return String.format(ADD_STRING, addTask, tasks.getTotal());
    }

    /**
     * Returns a formatted message to the user after marking a task as done.
     *
     * @param markTask Task to be marked as done.
     * @return A formatted message to the user after their task has been marked as done.
     */
    public String getMarkMessage(Task markTask) {
        return String.format(MARK_STRING, markTask);
    }

    /**
     * Returns a formatted message to the user after marking a task as not done.
     *
     * @param unmarkTask Task to be marked as not done.
     * @return A formatted message to the user after their task has been marked as not done.
     */
    public String getUnmarkMessage(Task unmarkTask) {
        return String.format(UNMARK_STRING, unmarkTask);
    }

    /**
     * Returns a formatted message to the user after deleting a task from the task list.
     *
     * @param deleteTask Task to be deleted from the task list.
     * @param tasks The list of tasks.
     * @return A formatted message to the user after their task has been deleted.
     */
    public String getDeleteMessage(Task deleteTask, TaskList tasks) {
        return String.format(DELETE_STRING, deleteTask, tasks.getTotal());
    }

    /**
     * Returns an error message to the user when it is thrown.
     *
     * @return An error message describing the error.
     */
    public String getErrorMessage(BmoException e) {
        return e.toString();
    }

    /**
     * Returns a default message to the user when an unknown command is entered.
     *
     * @return A message telling the user they entered an unknown command.
     */
    public String getDefaultMessage() {
        return DEFAULT_STRING;
    }

    /**
     * Returns a formatted message to the user that the current list of tasks have been saved.
     *
     * @param message The list of tasks which have been saved to the save file.
     * @return A formatted message with the saved list of tasks.
     */
    public String getSaveMessage(String message) {
        return String.format("%s\n%s", SAVE_STRING, message);
    }

    /**
     * Returns a closing message to the user on app startup.
     *
     * @return A closing message bidding the user goodbye.
     */
    public String getByeMessage() {
        return BYE_STRING;
    }
}
