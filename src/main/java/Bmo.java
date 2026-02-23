import java.util.List;
import java.util.Scanner;

public class Bmo {
    
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;
    
    public Bmo(String filePath) {
        try {
            storage = new Storage(filePath);
            tasks = new TaskList(storage.load());
            ui = new Ui();
            parser = new Parser();
            
            if (storage.hasCorruptedLines()) {
                String message = StorageCorruptedException.BMO_CORRUPTED_LINES_MESSAGE;
                String suggestion = StorageCorruptedException.BMO_CORRUPTED_LINES_SUGGESTION;
                List<String> corruptedLines = storage.getCorruptedLines();
                throw new StorageCorruptedException(message, suggestion, corruptedLines);
            }
        } catch (StorageCorruptedException e) {
            ui.showErrorMessage(e);
        } catch (BmoException e) {
            ui.showErrorMessage(e);
            tasks = new TaskList();
        }
    }
    
    public void run() {
        Scanner scanner = new Scanner(System.in);
        
        String retrieveText = tasks.toString();
        ui.showRetrieveMessage(retrieveText);
        ui.showWelcomeMessage();
        
        boolean hasExit = false;
        while (!hasExit) {
            try {
                String userInput = scanner.nextLine();
                parser.parseCommand(userInput, ui, tasks, storage);
                hasExit = parser.hasExitCommand();
            } catch (BmoException e) {
                ui.showErrorMessage(e);
            }
        }
        ui.showByeMessage();
        scanner.close();
    }
    
    public static void main(String[] args) {
        String BMO_FILE_PATH = "data/bmo.txt";
        new Bmo(BMO_FILE_PATH).run();
    }
}
