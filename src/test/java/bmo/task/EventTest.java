package bmo.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.util.Locale;
import org.junit.jupiter.api.Test;

/**
 * Represents the test class for the <code>Event</code> class.
 */
public class EventTest {
    public static final String INPUT_DATETIME_PATTERN = "d-M-uuuu HHmm";

    // Enforce strict date checking
    public static final DateTimeFormatter INPUT_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern(INPUT_DATETIME_PATTERN)
            .toFormatter(Locale.ENGLISH)
            .withResolverStyle(ResolverStyle.STRICT);

    @Test
    public void toString_eventTask_success() {
        LocalDateTime start = LocalDateTime.parse("05-03-2026 1300", INPUT_FORMATTER);
        LocalDateTime end = LocalDateTime.parse("06-03-2026 1400", INPUT_FORMATTER);
        Event event = new Event("project meeting", start, end);
        assertEquals("[E][ ] project meeting (from: Mar 5 2026 1300, to: Mar 6 2026 1400)", event.toString());
    }

    @Test
    public void saveString_eventTask_success() {
        LocalDateTime start = LocalDateTime.parse("05-03-2026 1300", INPUT_FORMATTER);
        LocalDateTime end = LocalDateTime.parse("06-03-2026 1400", INPUT_FORMATTER);
        Event event = new Event("project meeting", start, end);
        assertEquals("E | 0 | project meeting | Mar 5 2026 1300 | Mar 6 2026 1400", event.saveString());
    }

    @Test
    public void markAsDone_eventTask_success() {
        LocalDateTime start = LocalDateTime.parse("05-03-2026 1300", INPUT_FORMATTER);
        LocalDateTime end = LocalDateTime.parse("06-03-2026 1400", INPUT_FORMATTER);
        Event event = new Event("project meeting", start, end);
        event.markAsDone();
        assertEquals("[E][X] project meeting (from: Mar 5 2026 1300, to: Mar 6 2026 1400)", event.toString());
        assertEquals("E | 1 | project meeting | Mar 5 2026 1300 | Mar 6 2026 1400", event.saveString());
    }
}
