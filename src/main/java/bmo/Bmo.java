package bmo;

import bmo.command.Command;
import bmo.exception.BmoException;
import bmo.exception.StorageCorruptedException;
import bmo.command.CommandWord;
import bmo.parser.TaskListParser;
import bmo.storage.Storage;
import bmo.storage.StorageParser;
import bmo.task.TaskList;
import bmo.ui.Ui;

/**
 * Represents the main class of the program. A <code>Bmo</code> object initializes the
 * storage, task list, ui and task list parser components. On startup, lines from the
 * save file are loaded by storage.
 */
public class Bmo {

    private Storage storage;
    private TaskList taskList;
    private Ui ui;
    private TaskListParser taskListParser;

    private String loadingMessage;
    private CommandWord commandWord;

    /**
     * Initializes a <code>Bmo</code> object which creates instances of the major components.
     * This constructor takes in a file path representing the location of the save file.
     *
     * @param filePath The string which corresponds to the file path where the tasks are stored.
     */
    public Bmo(String filePath) {
        loadingMessage = "";

        try {
            // Initialize components
            storage = new Storage(filePath);
            ui = new Ui();
            taskListParser = new TaskListParser();

            // Load lines from storage into tasks
            taskList = new TaskList(storage.load());

            // Determine if there exist corrupted lines stored
            StorageParser.checkCorruptedLinesExist(storage);
        } catch (StorageCorruptedException e) {
            // Corrupted lines exist, display error message to user
            // taskList will contain tasks from non-corrupted lines
            loadingMessage = ui.showErrorMessage(e);
        } catch (BmoException e) {
            // Cannot read from file
            loadingMessage += ui.showErrorMessage(e);

            // Create empty task list
            taskList = new TaskList();
        }
    }

    public String getLoadingErrors() {
        return loadingMessage;
    }

    public String getWelcomeMessage() {
        String messages = "";

        // Display retrieved tasks to user
        String retrieveText = taskList.toString();
        messages += ui.showRetrieveMessage(retrieveText);

        // Display welcome message to user
        messages += ui.showWelcomeMessage();
        return messages;
    }

    public String getCommandFormats() {
        String commandFormatsText = String.join("\n", TaskListParser.COMMAND_FORMATS);
        return String.format(BmoException.BMO_INVALID_COMMAND_SUGGESTION, commandFormatsText);
    }

    public String getClosingMessage() {
        return ui.showByeMessage();
    }

    public boolean hasExitInput() throws BmoException {
        return commandWord.isExitWord();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) throws BmoException {
        // Parse user input to create a Command object
        Command command = taskListParser.parseCommand(input, ui, taskList, storage);

        // Retrieve the string from executing the command
        String response = command.execute(taskList, ui, storage);

        // Get the type of command
        commandWord = command.getCommandWord();

        return response;
    }

    public CommandWord getCommandWord() {
        return commandWord;
    }
}
