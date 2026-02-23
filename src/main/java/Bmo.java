import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Bmo {
    
    private Storage storage;
    private TaskList tasks;
    private Parser parser;
    private Ui ui;
    
    public Bmo(String filePath) {
        boolean exceptionCaught = true;
        try {
            storage = new Storage(filePath);
            parser = new Parser();
            tasks = new TaskList(storage.load());
            ui = new Ui();
            exceptionCaught = false;
        } catch (IOException e) {
            ui.showErrorMessage(e, filePath);
        } catch (InvalidPathException e) {
            ui.showErrorMessage(e, filePath);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.showErrorMessage(e);
        } catch (DateTimeParseException e) {
            ui.showErrorMessage(e, Storage.INPUT_DATETIME_PATTERN);
        } catch (MissingArgumentException e) {
            ui.showErrorMessage(e);
        } finally {
            if (exceptionCaught) {
                tasks = new TaskList();
            }
        }
    }
    
    public void run() {
        Scanner scanner = new Scanner(System.in);
        
        String retrieveText = tasks.toString();
        ui.showRetrieveMessage(retrieveText);
        ui.showWelcomeMessage();
        
        boolean hasExit = false;
        while (!hasExit) {
            String userInput = scanner.nextLine();
            parser.parseCommand(userInput, ui, tasks, storage);
            hasExit = parser.hasExitCommand();
        }
        scanner.close();
    }
    
    public static void main(String[] args) {
        String BMO_FILE_PATH = "data/bmo.txt";
        new Bmo(BMO_FILE_PATH).run();
    }
}
