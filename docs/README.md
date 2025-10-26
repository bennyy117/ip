# Nary User Guide

## Introduction

Welcome to **Nary** — your personal chatbot assistant for managing tasks directly from the command line!  
Nary helps you keep track of **Todos**, **Deadlines**, and **Events** effortlessly.  
It stores all your tasks in a text file (`data/nary.txt`) so your progress is never lost between sessions.

---

## Quick Start

1. **Ensure Java 17 or above is installed** on your computer.  
   *Mac users:* Make sure you have the exact JDK version specified.

2. **Download the latest `.jar` file** from the official source.

3. **Copy the `.jar` file** to the folder you want to use as the home folder for Nary.

4. **Open a command terminal**, navigate (`cd`) into the folder containing the jar file, and run: `java -jar nary.jar`. A command-line interface will appear in a few seconds. Nary may display some sample tasks to start with.

5. **Type commands** in the terminal and press Enter to execute them.

**Example commands you can try:**

- `list` : Lists all current tasks.
- `todo read book` : Adds a simple todo task.
- `deadline submit report /by 2025-10-10` : Adds a deadline task.
- `event project meeting /from 2025-10-07 /to 2025-10-08` : Adds an event task.
- `delete 3` : Deletes the 3rd task in the current list.
- `help` : Displays the help guide.
- `bye` : Exits Nary.

6. Refer to the **Basic Features** section below for detailed usage of each command.

## Basic Features

### Adding a Todo
Adds a simple task.

**Format:**
```
todo TASK_NAME
```

**Example:**
```
todo read book
```

**Expected Output:**
```
____________________________________________________________
 Got it. I've added this task:
   [T][ ] read book
 Now you have 1 task in the list.
____________________________________________________________
```

---

### Adding a Deadline
Adds a task that must be completed before a specific date.

**Format:**
```
deadline TASK_NAME /by DATE
```
DATE must be in yyyy-MM-dd format (e.g., 2025-10-10)

**Example:**
```
deadline submit report /by 2025-10-10
```

**Expected Output:**
```
____________________________________________________________
 Got it. I've added this task:
   [D][ ] submit report (by: Oct 10 2025)
 Now you have 2 tasks in the list.
____________________________________________________________
```

---

### Adding an Event
Adds a task with both start and end dates.

**Format:**
```
event TASK_NAME /from START_DATE /to END_DATE
```
START_DATE and END_DATE must be in yyyy-MM-dd format (e.g., 2025-10-10)

**Example:**
```
event project meeting /from 2025-10-07 /to 2025-10-08
```

**Expected Output:**
```
____________________________________________________________
 Got it. I've added this task:
   [E][ ] project meeting (from: Oct 7 2025 to: Oct 8 2025)
 Now you have 3 tasks in the list.
____________________________________________________________
```

---

### Listing All Tasks
Displays the current list of tasks.

**Format:**
```
list
```

**Expected Output:**
```
____________________________________________________________
 Here are the tasks in your list:
 1.[T][ ] read book
 2.[D][ ] submit report (by: Oct 10 2025)
 3.[E][ ] project meeting (from: Oct 7 2025 to: Oct 8 2025)
____________________________________________________________
```

---

### Marking Tasks as Done
Marks a task as completed.

**Format:**
```
mark TASK_NUMBER
```

**Example:**
```
mark 2
```

**Expected Output:**
```
____________________________________________________________
 Nice! I've marked this task as done:
   [D][X] submit report (by: Oct 10 2025)
____________________________________________________________
```

---

### Unmarking a Task
Marks a completed task as *not done yet*.

**Format:**
```
unmark TASK_NUMBER
```

**Example:**
```
unmark 2
```

**Expected Output:**
```
____________________________________________________________
 OK, I've marked this task as not done yet:
   [D][ ] submit report (by: Oct 10 2025)
____________________________________________________________
```

---

### Deleting a Task
Removes a task permanently from the list.

**Format:**
```
delete TASK_NUMBER
```

**Example:**
```
delete 1
```

**Expected Output:**
```
____________________________________________________________
 I've removed this task:
   [T][ ] read book
 Now you have 2 tasks in the list.
____________________________________________________________
```

---

### Finding Tasks by Keyword
Searches for tasks containing a specific keyword.

**Format:**
```
find KEYWORD
```

**Example:**
```
find project
```

**Expected Output:**
```
____________________________________________________________
 Here are the matching tasks in your list:
 1.[E][ ] project meeting (from: Oct 7 2025 to: Oct 8 2025)
____________________________________________________________
```

---

### Displaying Help
Shows a guide with all available commands, their format, and examples.

**Format:**
```
help
```

---

### Exiting the Program
Ends the session safely.

**Format:**
```
bye
```

**Expected Output:**
```
____________________________________________________________
 Bye. Hope to see you again soon!
____________________________________________________________
```

---

## Data Storage

All tasks are automatically saved to:
```
data/nary.txt
```

**Example file contents:**
```
T - X - read book
D - O - submit report - 2025-10-10
E - X - project meeting - 2025-10-07 - 2025-10-08
```

When you restart Nary, it automatically loads all your saved tasks.

---

## Error Handling

Nary handles invalid commands gracefully and provides feedback, for example:

**Example:**
```
todo
```

**Output:**
```
OOPS!!! The description of a todo cannot be empty.
```

---

## Summary of Commands

| Command                                         | Description | Example |
|-------------------------------------------------|--------------|----------|
| `todo TASK_NAME`                                | Add a todo | `todo read book` |
| `deadline TASK_NAME /by DATE`                   | Add a deadline | `deadline submit report /by 2025-10-10` |
| `event TASK_NAME /from START_DATE /to END_DATE` | Add an event | `event meeting /from 2025-10-07 /to 2025-10-08` |
| `list`                                          | Show all tasks | `list` |
| `mark TASK_NUMBER`                                    | Mark task as done | `mark 2` |
| `unmark TASK_NUMBER`                                  | Mark task as not done | `unmark 2` |
| `delete TASK_NUMBER`                                  | Delete a task | `delete 3` |
| `find KEYWORD`                                | Find matching tasks | `find project` |
| `help`                                          | Display help guide | `help` |
| `bye`                                           | Exit Nary | `bye` |
