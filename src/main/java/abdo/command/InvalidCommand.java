package abdo.command;

import abdo.storage.Storage;
import abdo.task.TaskList;
import abdo.ui.Ui;

/**
 * Used to process an invalid input from the user.
 */
public class InvalidCommand extends Command {

    private Ui ui;

    public InvalidCommand(Ui ui) {
        this.ui = ui;
    }

    /**
     * Handles the invalid command exception,
     * prints invalid command error message.
     *
     * @param tasks the task list
     * @param ui the UI handler
     * @param storage the storage handler
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.printInvalidCommand();
    }
}
