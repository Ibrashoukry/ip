package abdo;

import abdo.command.Command;
import abdo.parser.Parser;
import abdo.storage.Storage;
import abdo.ui.Ui;
import abdo.task.TaskList;

public class Abdo {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Abdo(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
           tasks = new TaskList(storage.load());
        } catch (AbdoException e) {
            ui.printLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.printGreeting();
        boolean isExit = false;
        while(!isExit) {
            String fullCommand = ui.readCommand();
            Command c = Parser.parse(fullCommand);
            c.execute(tasks, ui, storage);
            isExit = c.isExit();
        }
        ui.printExit();
    }

    public static void main(String[] args){
        new Abdo("./data/tasks.txt").run();
    }
}