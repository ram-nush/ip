package bmo.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
