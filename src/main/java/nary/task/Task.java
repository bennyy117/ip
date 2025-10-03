package nary.task;

/**
 * Represents a general task in the Nary chatbot.
 * A task has a description and can be marked as done or not done.
 */
public class Task {
    public String description;
    public boolean isDone;

    /**
     * Constructs a new Task with the given description.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon of the task.
     *
     * @return "X" if the task is done, otherwise a blank space.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /** Marks this task as done. */
    public void markAsDone() {
        isDone = true;
    }

    /** Marks this task as not done. */
    public void markAsNotDone() {
        isDone = false;
    }

    /** @return The description of this task. */
    public String getDescription() { return this.description; }

    /** @return A string representation of the task. */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}