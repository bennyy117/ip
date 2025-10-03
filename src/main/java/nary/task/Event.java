package nary.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Event extends Task {
    public LocalDate from;
    public LocalDate to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = LocalDate.parse(from);
        this.to = LocalDate.parse(to);
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH);
        return "[E]" + super.toString() + " (from: " + from.format(fmt) + " to: " + to.format(fmt) + ")";
    }
}
