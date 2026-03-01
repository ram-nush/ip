package bmo.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import bmo.storage.Storage;
import bmo.task.Deadline;
import bmo.task.Event;
import bmo.task.Task;
import bmo.task.TaskList;
import bmo.task.Todo;
import bmo.ui.Ui;

/**
 * Represents the test class for the <code>ListCommand</code> class.
 */
public class ListCommandTest {
    public static final String INPUT_DATETIME_PATTERN = "d-M-uuuu HHmm";

    // Enforce strict date checking
    public static final DateTimeFormatter INPUT_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern(INPUT_DATETIME_PATTERN)
            .toFormatter(Locale.ENGLISH)
            .withResolverStyle(ResolverStyle.STRICT);

    private Storage storage;
    private TaskList taskList;
    private Ui ui;

    private final String filePath = "src/test/data/bmo.txt";

    @Test
    public void execute_emptyTaskList_success() {
        ListCommand listCommand = new ListCommand();
        storage = new Storage(filePath);
        taskList = new TaskList();
        ui = new Ui();

        String message = listCommand.execute(taskList, ui, storage);
        assertEquals("Look at all these tasks!\n", message);
    }

    @Test
    public void execute_nonEmptyTaskList_success() {
        ListCommand listCommand = new ListCommand();
        storage = new Storage(filePath);

        LocalDateTime due = LocalDateTime.parse("05-03-2026 1200", INPUT_FORMATTER);
        LocalDateTime start = LocalDateTime.parse("05-03-2026 1300", INPUT_FORMATTER);
        LocalDateTime end = LocalDateTime.parse("06-03-2026 1400", INPUT_FORMATTER);

        List<Task> listOfTasks = new ArrayList<>();
        listOfTasks.add(new Todo("read book"));
        listOfTasks.add(new Deadline("return book", due));
        listOfTasks.add(new Event("project meeting", start, end));

        taskList = new TaskList(listOfTasks);
        ui = new Ui();

        String message = listCommand.execute(taskList, ui, storage);
        assertEquals("Look at all these tasks!\n"
                + "1. [T][ ] read book\n"
                + "2. [D][ ] return book (by: Mar 5 2026 1200)\n"
                + "3. [E][ ] project meeting (from: Mar 5 2026 1300, to: Mar 6 2026 1400)",
                message);
    }
}
