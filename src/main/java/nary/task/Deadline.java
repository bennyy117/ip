package nary.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Deadline extends Task {
    public LocalDate by;

    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDate.parse(by); // yyyy-MM-dd
    }

    @Override
    public String toString() {
        String formatted = by.format(DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH));
        return "[D]" + super.toString() + " (by: " + formatted + ")";
    }
}
