package nary.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a task with a deadline.
 * Inherits from {@link Task} and includes a due date.
 */
public class Deadline extends Task {
    public LocalDate by;

    /**
     * Constructs a Deadline task with the given description and due date.
     *
     * @param description The description of the task.
     * @param by The deadline in yyyy-MM-dd format.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDate.parse(by); // yyyy-MM-dd
    }

    /**
     * Returns a string representation of the Deadline task,
     * including its due date formatted as "MMM d yyyy".
     *
     * @return A string describing the deadline task.
     */
    @Override
    public String toString() {
        String formatted = by.format(DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH));
        return "[D]" + super.toString() + " (by: " + formatted + ")";
    }
}
