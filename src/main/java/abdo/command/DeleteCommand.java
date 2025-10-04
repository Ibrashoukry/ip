package abdo.command;

import abdo.storage.Storage;
import abdo.task.TaskList;
import abdo.ui.Ui;

import java.io.IOException;

public class DeleteCommand extends Command {

    private String indexString;
    private boolean hasNoArgs;

    public boolean isExit() {
        return false;
    }

    public DeleteCommand(Ui ui, String[] parsedCommand) {
        if (parsedCommand.length == 1) {
            this.hasNoArgs = true;
            ui.printNoArgs(parsedCommand[0]);
            return;
        }

        this.indexString = parsedCommand[1];
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {

        if (hasNoArgs) {
            return;
        }

        try {
            int index = Integer.parseInt(indexString) - 1;

            ui.printDeleteTask(tasks.getTask(index), tasks.getSize()-1);
            tasks.removeTask(index);

            try {
                storage.updateFile(tasks.getTasks());
            } catch (IOException e) {
                ui.printSaveError();
            }

        } catch (IndexOutOfBoundsException e) {
            ui.printOOB();
        } catch (NumberFormatException e) {
            ui.printInvalidNumArg();

        }
    }
}
