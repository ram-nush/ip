package bmo.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Represents the test class for the <code>Task</code> class. A <code>TaskTest</code>
 * object stores the test cases which perform operations on <code>Task</code> objects.
 */
public class TaskTest {
    /**
     * Mark a newly created task as done.
     *
     * @result Task will be marked as done without any errors,
     *         and it is reflected in Task.toString()
     */
    @Test
    public void markAsDone_newTask() {
        Task task = new Task("read book");
        task.markAsDone();
        assertEquals("[X] read book", task.toString());
    }

    /**
     * Mark an already marked task as done.
     *
     * @result Task will remain marked as done without any errors,
     *         and it is reflected in Task.toString()
     */
    @Test
    public void markAsDone_markedTask() {
        Task task = new Task("read book");
        task.markAsDone();
        task.markAsDone();
        assertEquals("[X] read book", task.toString());
    }

    /**
     * Mark a newly created task as not done.
     *
     * @result Task will remain marked as not done without any errors,
     *         and it is reflected in Task.toString()
     */
    @Test
    public void markAsNotDone_newTask() {
        Task task = new Task("read book");
        task.markAsNotDone();
        assertEquals("[ ] read book", task.toString());
    }

    /**
     * Mark an already marked task as not done.
     *
     * @result Task will be marked as not done without any errors,
     *         and it is reflected in Task.toString()
     */
    @Test
    public void markAsNotDone_markedTask() {
        Task task = new Task("read book");
        task.markAsDone();
        task.markAsNotDone();
        assertEquals("[ ] read book", task.toString());
    }
}
