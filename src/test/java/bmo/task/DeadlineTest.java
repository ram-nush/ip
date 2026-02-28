package bmo.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.util.Locale;
import org.junit.jupiter.api.Test;

/**
 * Represents the test class for the <code>Deadline</code> class.
 */
public class DeadlineTest {
    public static final String INPUT_DATETIME_PATTERN = "d-M-uuuu HHmm";

    // Enforce strict date checking
    public static final DateTimeFormatter INPUT_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern(INPUT_DATETIME_PATTERN)
            .toFormatter(Locale.ENGLISH)
            .withResolverStyle(ResolverStyle.STRICT);

    @Test
    public void toString_deadlineTask_success() {
        LocalDateTime due = LocalDateTime.parse("05-03-2026 1200", INPUT_FORMATTER);
        Deadline deadline = new Deadline("return book", due);
        assertEquals("[D][ ] return book (by: Mar 5 2026 1200)", deadline.toString());
    }

    @Test
    public void saveString_deadlineTask_success() {
        LocalDateTime due = LocalDateTime.parse("05-03-2026 1200", INPUT_FORMATTER);
        Deadline deadline = new Deadline("return book", due);
        assertEquals("D | 0 | return book | Mar 5 2026 1200", deadline.saveString());
    }

    @Test
    public void markAsDone_deadlineTask_success() {
        LocalDateTime due = LocalDateTime.parse("05-03-2026 1200", INPUT_FORMATTER);
        Deadline deadline = new Deadline("return book", due);
        deadline.markAsDone();
        assertEquals("[D][X] return book (by: Mar 5 2026 1200)", deadline.toString());
        assertEquals("D | 1 | return book | Mar 5 2026 1200", deadline.saveString());
    }
}
