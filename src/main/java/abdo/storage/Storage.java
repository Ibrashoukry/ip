package abdo.storage;

import abdo.AbdoException;
import abdo.task.Deadline;
import abdo.task.Event;
import abdo.ui.Ui;
import abdo.task.Task;
import abdo.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {

    private File file;

    public Storage(String filePath) {
        this.file = new File(filePath);
    }

    // Makes changes to the file whenever an addition or deletion is done
    public void updateFile(ArrayList<Task> tasks) throws IOException {

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
    public ArrayList<Task> load() throws AbdoException {
        ArrayList<Task> tasks = new ArrayList<>();
        Ui ui = new Ui();

        try {
            Scanner s = new Scanner(file);
            String[] parsedLine;
            Task task;

            boolean isDone;

            while (s.hasNext()) {
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
                        throw new AbdoException("Invalid task format in file");
                    }
                    isDone = parsedLine[1].equals("1");
                    task.setDone(isDone);
                    tasks.add(task);

                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println(ui.LINEBREAK + "Invalid format in tasks file" + ui.LINEBREAK);
                }
            }

            s.close();

        } catch (FileNotFoundException e) {
            throw new abdo.AbdoException("File not found!");
        }

        return tasks;
    }
}
