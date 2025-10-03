package nary.storage;

import nary.task.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Handles loading and saving tasks to a file.
 * Provides persistence for the Nary chatbot.
 */
public class Storage {
    private final String filePath;

    /**
     * Creates a Storage instance that will read/write data to the specified file path.
     *
     * @param filePath The path of the file to store tasks.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file.
     *
     * @return A list of tasks read from the file. Returns an empty list if no file exists.
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            return tasks;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" - ");
                switch (parts[0]) {
                    case "T":
                        Task todo = new Todo(parts[2]);
                        if (parts[1].equals("X")) todo.markAsDone();
                        tasks.add(todo);
                        break;
                    case "D":
                        Task deadline = new Deadline(parts[2], parts[3]);
                        if (parts[1].equals("X")) deadline.markAsDone();
                        tasks.add(deadline);
                        break;
                    case "E":
                        Task event = new Event(parts[2], parts[3], parts[4]);
                        if (parts[1].equals("X")) event.markAsDone();
                        tasks.add(event);
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
        return tasks;
    }

    /**
     * Saves the given list of tasks to the file.
     *
     * @param tasks The list of tasks to be saved.
     */
    public void save(ArrayList<Task> tasks) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Task t : tasks) {
                if (t instanceof Todo) {
                    bw.write("T - " + (t.isDone ? "X" : "O") + " - " + t.description);
                } else if (t instanceof Deadline) {
                    Deadline d = (Deadline) t;
                    bw.write("D - " + (d.isDone ? "X" : "O") + " - " + d.description + " - " + d.by);
                } else if (t instanceof Event) {
                    Event e = (Event) t;
                    bw.write("E - " + (e.isDone ? "X" : "O") + " - " + e.description + " - " + e.from + " - " + e.to);
                }
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}
