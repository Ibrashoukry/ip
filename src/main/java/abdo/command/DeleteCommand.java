package abdo.command;

import abdo.storage.Storage;
import abdo.task.TaskList;
import abdo.ui.Ui;

import java.io.IOException;

/**
 * Represents a command that deletes a task from the task list.
 */
public class DeleteCommand extends Command {

    private String indexString;
    private boolean hasNoArgs;

    public DeleteCommand(Ui ui, String[] parsedCommand) {
        if (parsedCommand.length == 1) {
            this.hasNoArgs = true;
            ui.printNoArgs(parsedCommand[0]);
            return;
        }

        this.indexString = parsedCommand[1];
    }

    /**
     * Executes the delete command, removing a task from the list and updating storage.
     *
     * @param tasks the task list
     * @param ui the UI handler
     * @param storage the storage handler
     */
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
