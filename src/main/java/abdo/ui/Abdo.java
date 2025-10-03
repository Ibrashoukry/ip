package abdo.ui;

import abdo.process.AbdoException;
import abdo.process.Deadline;
import abdo.process.Event;
import abdo.process.Printer;
import abdo.process.Task;
import abdo.process.Todo;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class Abdo {

    public static final int MAX_TASKS = 100;

    /** Processes user input if it one of TODO, EVENT, or DEADLINE and returns a task object with
     * initialized values */
    public static Task processTask(String command, String type) throws AbdoException {

        Printer printer = new Printer();
        String[] parsedDesc;
        String description, by, from, to;
        Task task;

        String unparsedDesc = command.substring(type.length() + 1);

        switch(type) {
        case "todo":
            task = new Todo(unparsedDesc);
            break;
        case "deadline":
            try {
                parsedDesc = unparsedDesc.split("/by");
                description = parsedDesc[0].trim();
                by = parsedDesc[1].trim();
                task = new Deadline(description, by);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new AbdoException(printer.lineBreak +
                        "Invalid format. Expected: \"deadline {description} /by {due date/time}\"" +
                        System.lineSeparator() + printer.lineBreak);
            }
            break;
        case "event":
            try {
                parsedDesc = unparsedDesc.split("/from|/to");
                description = parsedDesc[0].trim();
                from = parsedDesc[1].trim();
                to = parsedDesc[2].trim();
                task = new Event(description, from, to);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new AbdoException(printer.lineBreak +
                        "Incorrect format! Expected: \"event {description} /from {start date/time} /to {end date/time}\"" +
                        System.lineSeparator() + printer.lineBreak);
            }
            break;
        default:
            task = new Task(command);
        }
        return task;
    }

    // Makes changes to the file whenever an addition or deletion is done
    public static void updateFile(File file, Task[] tasks, int tasksLen) throws IOException {

        FileWriter fw = new FileWriter(file);
        Task task;
        String doneIndicator;

        for (int i = 0; i < tasksLen; i++) {

            task = tasks[i];
            doneIndicator = (task.getIsDone()) ? "1" : "0";

            if (task instanceof Todo) {
                fw.write("T|" + doneIndicator + "|" + task.getDescription() + System.lineSeparator());
            } else if (task instanceof Deadline) {
                fw.write("D|" + doneIndicator + "|" + task.getDescription() +
                        "|" + ((Deadline) task).getBy() + System.lineSeparator());
            } else if (task instanceof Event) {
                fw.write("E|" + doneIndicator + "|" + task.getDescription() +
                        "|" + ((Event) task).getFrom() + "|" + ((Event) task).getTo() + System.lineSeparator());
            }
        }

        fw.close();
    }

    // Populates the tasks list if it is the beginning of the session
    public static int populateList(File file, Task[] tasks, int nextTask) throws FileNotFoundException {

        Scanner s = new Scanner(file);
        String[] parsedLine;
        Task task;
        Printer printer = new Printer();
        boolean isDone;

        while(s.hasNext()) {
            parsedLine = s.nextLine().split("\\|");
            try {
                switch (parsedLine[0]) {
                case "T":
                    task = new Todo(parsedLine[2]);
                    break;
                case "D":
                    task = new Deadline(parsedLine[2], parsedLine[3]);
                    break;
                case "E":
                    task = new Event(parsedLine[2], parsedLine[3], parsedLine[4]);
                    break;
                default:
                    task = null;
                    break;
                }

                if (task != null) {
                    isDone = parsedLine[1].equals("1");
                    task.setDone(isDone);
                    tasks[nextTask] = task;
                    nextTask++;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(printer.lineBreak + "Invalid format in tasks file" + printer.lineBreak);
            }
        }

        s.close();

        return nextTask;
    }

    public static void main(String[] args) throws IOException {

        Printer printer = new Printer();
        Scanner in = new Scanner(System.in);
        Task[] tasks = new Task[MAX_TASKS];
        int nextTask = 0;

        File file = new File("./data/tasks.txt");
        File parentDir = file.getParentFile();

        // creates new parent folder if directory doesn't exist
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        if (!file.exists()) {
            file.createNewFile();
        }

        // populate list with existing tasks from file
        if (file.length() != 0) {
            nextTask = populateList(file, tasks, nextTask);
        }

        printer.printGreeting();

        String command = in.nextLine();

        while (!command.equals("bye")) {
            String[] parsedCommand = command.split(" ");
            switch(parsedCommand[0]) {
            case "list":
                System.out.println(printer.lineBreak + "Here are the tasks in your list:");
                for (int i = 0; i < nextTask; i++) {
                    System.out.println((i + 1) + ". " + tasks[i].toString());
                }
                System.out.print(printer.lineBreak);
                break;
            case "mark":
                tasks[Integer.parseInt(parsedCommand[1]) - 1].setDone(true);
                System.out.print(printer.lineBreak + "Nice job! abdo.process.Task marked as DONE!\n" +
                        tasks[Integer.parseInt(parsedCommand[1]) - 1].toString() + "\n" + printer.lineBreak);
                break;
            case "unmark":
                tasks[Integer.parseInt(parsedCommand[1]) - 1].setDone(false);
                System.out.print(printer.lineBreak + "Ahhh! abdo.process.Task marked NOT DONE!\n" +
                        tasks[Integer.parseInt(parsedCommand[1]) - 1].toString() + "\n" + printer.lineBreak);
                break;
            case "todo":
            case "deadline":
            case "event":
                // handles error where no other info has been inputted except the command
                if (parsedCommand.length == 1) {
                    System.out.print(printer.lineBreak +
                            "I don't understand. You have to give \"" + parsedCommand[0] +
                            "\" a description!!!" + System.lineSeparator() + printer.lineBreak);
                    break;
                }

                try {
                    tasks[nextTask] = processTask(command, parsedCommand[0]);
                    printer.printAddTask(tasks[nextTask], nextTask + 1);
                    nextTask++;
                    updateFile(file, tasks, nextTask);
                } catch (AbdoException e) {
                    System.out.print(e.getMessage());
                }
                break;
            default:
                System.out.print(printer.lineBreak +
                        "Not a real command... you should know better!" + System.lineSeparator() + printer.lineBreak);
                break;
            }

            System.out.print("> ");
            command = in.nextLine();
        }

        printer.printExit();
        in.close();
    }
}