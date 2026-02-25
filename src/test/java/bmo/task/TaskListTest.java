package bmo.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import bmo.parser.TaskListParser;

public class TaskListTest {
    @Test
    public void addTask_todoTask() {
        TaskList taskList = new TaskList();
        Task task = new Todo("read book");
        taskList.addTask(task);
        assertEquals("[T][ ] read book\n", taskList.toString());
    }

    @Test
    public void addTask_todoDeadline() {
        TaskList taskList = new TaskList();
        LocalDateTime due = LocalDateTime.parse("05-03-2026 1200", 
                TaskListParser.INPUT_FORMATTER);
        Task task = new Deadline("return book", due);
        taskList.addTask(task);
        assertEquals("[D][ ] return book (by: Mar 5 2026 1200)\n", taskList.toString());
    }

    @Test
    public void addTask_todoEvent() {
        TaskList taskList = new TaskList();
        LocalDateTime start = LocalDateTime.parse("05-03-2026 1300",
                TaskListParser.INPUT_FORMATTER);
        LocalDateTime end = LocalDateTime.parse("06-03-2026 1400",
                TaskListParser.INPUT_FORMATTER);
        Task task = new Event("project meeting", start, end);
        taskList.addTask(task);
        assertEquals("[E][ ] project meeting (from: Mar 5 2026 1300, to: Mar 6 2026 1400)\n", taskList.toString());
    }

    @Test
    public void listTasks_subclassTasks_success() {
        LocalDateTime due = LocalDateTime.parse("05-03-2026 1200",
                TaskListParser.INPUT_FORMATTER);
        LocalDateTime start = LocalDateTime.parse("05-03-2026 1300",
                TaskListParser.INPUT_FORMATTER);
        LocalDateTime end = LocalDateTime.parse("06-03-2026 1400",
                TaskListParser.INPUT_FORMATTER);
        
        Task task1 = new Todo("read book");
        Task task2 = new Deadline("return book", due);
        Task task3 = new Event("project meeting", start, end);
        List<Task> tasks = List.<Task>of(task1, task2, task3);
        TaskList taskList = new TaskList(tasks);
        
        assertEquals("1. [T][ ] read book\n" 
                + "2. [D][ ] return book (by: Mar 5 2026 1200)\n" 
                + "3. [E][ ] project meeting (from: Mar 5 2026 1300, to: Mar 6 2026 1400)\n", 
                taskList.listTasks());
    }
}
