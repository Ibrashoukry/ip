package abdo.command;

import abdo.storage.Storage;
import abdo.task.TaskList;
import abdo.ui.Ui;

public abstract class Command {

    public void execute(TaskList tasks, Ui ui, Storage storage) {};
    public boolean isExit() {
        return false;
    }
}
