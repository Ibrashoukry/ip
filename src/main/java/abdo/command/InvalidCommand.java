package abdo.command;

import abdo.storage.Storage;
import abdo.task.TaskList;
import abdo.ui.Ui;

public class InvalidCommand extends Command {

    private Ui ui;

    public InvalidCommand(Ui ui) {
        this.ui = ui;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.printInvalidCommand();
    }
}
