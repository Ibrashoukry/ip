package abdo.command;

import abdo.storage.Storage;
import abdo.task.TaskList;
import abdo.ui.Ui;

import java.io.IOException;

public class DeleteCommand extends Command {

    private int index;

    public boolean isExit() {
        return false;
    }

    public DeleteCommand(Ui ui, String[] parsedCommand) {
        if (parsedCommand.length == 1) {
            ui.printNoArgs(parsedCommand[0]);
        }

        this.index = Integer.parseInt(parsedCommand[1]) - 1;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {

        try {
            ui.printDeleteTask(tasks.getTask(index), tasks.getSize()-1);
            tasks.removeTask(index);

            try {
                storage.updateFile(tasks.getTasks());

            } catch (IOException e) {
                ui.printSaveError();

            }

        } catch (IndexOutOfBoundsException e) {
            ui.printBreak();
            System.out.print("Out of bounds! Try again." + ui.NL);
            ui.printBreak();

        } catch (NumberFormatException e) {
            ui.printBreak();
            System.out.print("That's not a number!" + ui.NL);
            ui.printBreak();

        }
    }
}
