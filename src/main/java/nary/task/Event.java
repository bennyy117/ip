package nary.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents an Event task that has a start and end date.
 * Inherits from {@link Task}.
 */
public class Event extends Task {
    public LocalDate from;
    public LocalDate to;

    /**
     * Constructs an Event with description, start date, and end date.
     *
     * @param description The description of the event.
     * @param from Start date in yyyy-MM-dd format.
     * @param to End date in yyyy-MM-dd format.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = LocalDate.parse(from);
        this.to = LocalDate.parse(to);
    }

    /**
     * Returns a string representation of the Event,
     * including the start and end dates formatted as "MMM d yyyy".
     *
     * @return String representation of the event task.
     */
    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH);
        return "[E]" + super.toString() + " (from: " + from.format(fmt) + " to: " + to.format(fmt) + ")";
    }
}
