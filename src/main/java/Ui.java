import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Ui {
    private static String retrieveString;
    private static String welcomeString;
    private static String listString;
    private static String defaultString;
    private static List<String> helpMessages;
    private static String saveString;
    private static String byeString;
    
    Ui() {
        retrieveString = "The following tasks have been retrieved:";
        welcomeString = "Hello! I'm BMO\n" + "What can I do for you?";
        listString = "Here are the tasks in your list:";
        defaultString = "OOPS!!! I'm sorry, but I don't know what that means :-(";
        
        helpMessages = new ArrayList<String>();
        helpMessages.add("To fix: Enter one of the following commands in the correct format");
        helpMessages.add("list");
        helpMessages.add("todo <description>");
        helpMessages.add("deadline <description> /by <due>");
        helpMessages.add("event <description> /from <start> /to <end>");
        helpMessages.add("mark <index>");
        helpMessages.add("unmark <index>");
        helpMessages.add("delete <index>");
        helpMessages.add("bye");
        helpMessages.add("format of <due>,<start>,<end> is dd-MM-yyyy HHmm");
        
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
    
    public void showHelpMessage() {
        this.printMessage(helpMessages);
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
