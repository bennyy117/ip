package nary.parser;

import nary.task.Todo;
import nary.task.Event;
import nary.task.Task;
import nary.task.Deadline;

public class Parser {

    /**
     * Converts a line of text from storage into a Task object.
     */
    public static Task parseTask(String line) {
        String[] parts = line.split(" - ");
        switch (parts[0]) {
        case "T":
            Task todo = new Todo(parts[2]);
            if (parts[1].equals("X")) {
                todo.markAsDone();
            }
            return todo;
        case "D":
            Task deadline = new Deadline(parts[2], parts[3]);
            if (parts[1].equals("X")) {
                deadline.markAsDone();
            }
            return deadline;
        case "E":
            Task event = new Event(parts[2], parts[3], parts[4]);
            if (parts[1].equals("X")) {
                event.markAsDone();
            }
            return event;
        default:
            throw new IllegalArgumentException("Unknown task type in file: " + line);
        }
    }

    /**
     * Converts a Task object into a line for storage.
     */
    public static String formatTask(Task t) {
        if (t instanceof Todo) {
            return "T - " + (t.isDone ? "X" : "O") + " - " + t.description;
        } else if (t instanceof Deadline d) {
            return "D - " + (d.isDone ? "X" : "O") + " - " + d.description + " - " + d.by;
        } else if (t instanceof Event e) {
            return "E - " + (e.isDone ? "X" : "O") + " - " + e.description + " - " + e.from + " - " + e.to;
        }
        return "";
    }
}
