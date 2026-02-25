package bmo.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Represents the test class for the <code>Task</code> class. A <code>TaskTest</code>
 * object stores the test cases which perform operations on <code>Task</code> objects.
 */
public class TaskTest {
    @Test
    public void markAsDone_newTask_success() {
        Task task = new Task("read book");
        task.markAsDone();
        assertEquals("[X] read book", task.toString());
    }

    @Test
    public void markAsDone_markedTask_success() {
        Task task = new Task("read book");
        task.markAsDone();
        task.markAsDone();
        assertEquals("[X] read book", task.toString());
    }

    @Test
    public void markAsNotDone_newTask_success() {
        Task task = new Task("read book");
        task.markAsNotDone();
        assertEquals("[ ] read book", task.toString());
    }

    @Test
    public void markAsNotDone_markedTask_success() {
        Task task = new Task("read book");
        task.markAsDone();
        task.markAsNotDone();
        assertEquals("[ ] read book", task.toString());
    }
}
