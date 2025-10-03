package abdo.ui;

import abdo.process.AbdoException;
import abdo.process.Deadline;
import abdo.process.Event;
import abdo.process.Printer;
import abdo.process.Task;
import abdo.process.Todo;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class Abdo {

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
    public static void updateFile(File file, ArrayList<Task> tasks) throws IOException {

        FileWriter fw = new FileWriter(file);
        String doneIndicator;

        for (Task task : tasks) {
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
    public static void populateList(File file, ArrayList<Task> tasks) throws FileNotFoundException {

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
                    tasks.add(task);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(printer.lineBreak + "Invalid format in tasks file" + printer.lineBreak);
            }
        }

        s.close();
    }

    public static void main(String[] args) throws IOException {

        Printer printer = new Printer();
        Scanner in = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

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
            populateList(file, tasks);
        }

        printer.printGreeting();

        String command = in.nextLine();

        while (!command.equals("bye")) {

            String[] parsedCommand = command.split(" ");
            int index;

            switch(parsedCommand[0]) {
            case "list":
                System.out.println(printer.lineBreak + "Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i).toString());
                }
                System.out.print(printer.lineBreak);
                break;
            case "mark":
                try {
                    index = Integer.parseInt(parsedCommand[1]) - 1;
                    tasks.get(index).setDone(true);
                    System.out.print(printer.lineBreak + "Nice job! abdo.process.Task marked as DONE!\n" +
                            tasks.get(Integer.parseInt(parsedCommand[1]) - 1).toString() + "\n" + printer.lineBreak);

                    updateFile(file, tasks);
                } catch (IndexOutOfBoundsException e) {
                    System.out.print(printer.lineBreak + "Out of bounds! Try again." +
                            System.lineSeparator() + printer.lineBreak);
                } catch (NumberFormatException e) {
                    System.out.print(printer.lineBreak + "That's not a number!" +
                            System.lineSeparator() + printer.lineBreak);
                }

                break;
            case "unmark":
                try {
                    index = Integer.parseInt(parsedCommand[1]) - 1;
                    tasks.get(index).setDone(false);
                    System.out.print(printer.lineBreak + "Ahhh! abdo.process.Task marked NOT DONE!\n" +
                            tasks.get(Integer.parseInt(parsedCommand[1]) - 1).toString() + "\n" + printer.lineBreak);

                    updateFile(file, tasks);
                } catch (IndexOutOfBoundsException e) {
                    System.out.print(printer.lineBreak + "Out of bounds! Try again." +
                            System.lineSeparator() + printer.lineBreak);
                } catch (NumberFormatException e) {
                    System.out.print(printer.lineBreak + "That's not a number!" +
                            System.lineSeparator() + printer.lineBreak);
                }

                break;
            case "delete":
                // handles error where no other info has been inputted except the command
                if (parsedCommand.length == 1) {
                    System.out.print(printer.lineBreak +
                            "No! You have to add something after \"" + parsedCommand[0] +
                            "\"" + System.lineSeparator() + printer.lineBreak);
                    break;
                } else if (parsedCommand.length > 2) {
                    System.out.print(printer.lineBreak +
                            "You doing too much.. just give me a number" + System.lineSeparator() + printer.lineBreak);
                    break;
                }

                try {
                    index = Integer.parseInt(parsedCommand[1]) - 1;
                    printer.printDeleteTask(tasks.get(index), tasks.size() - 1);
                    tasks.remove(index);

                    updateFile(file, tasks);
                } catch (IndexOutOfBoundsException e) {
                    System.out.print(printer.lineBreak + "Out of bounds! Try again." +
                            System.lineSeparator() + printer.lineBreak);
                } catch (NumberFormatException e) {
                    System.out.print(printer.lineBreak + "That's not a number!" +
                            System.lineSeparator() + printer.lineBreak);
                }

                break;
            case "todo":
            case "deadline":
            case "event":
                // handles error where no other info has been inputted except the command
                if (parsedCommand.length == 1) {
                    System.out.print(printer.lineBreak +
                            "No! You have to add something after \"" + parsedCommand[0] +
                            "\"" + System.lineSeparator() + printer.lineBreak);
                    break;
                }

                try {
                    tasks.add(processTask(command, parsedCommand[0]));
                    printer.printAddTask(tasks.get(tasks.size()-1), tasks.size());

                    updateFile(file, tasks);
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