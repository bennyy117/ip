package nary.task;

/**
 * Represents a Todo task (without a date).
 * Inherits from {@link Task}.
 */
public class Todo extends Task {
    /**
     * Constructs a Todo with the given description.
     *
     * @param description The description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    /** Returns a string representation of the Todo task. */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
