package abdo.command;

import abdo.storage.Storage;
import abdo.task.TaskList;
import abdo.ui.Ui;

import java.io.IOException;

public class StatusCommand extends Command {

    private String action;
    private String indexString;
    private boolean hasNoArgs;

    public StatusCommand(Ui ui, String[] parsedCommand) {
        if (parsedCommand.length == 1) {
            this.hasNoArgs = true;
            ui.printNoArgs(parsedCommand[0]);
            return;
        }

        this.action = parsedCommand[0];
        this.indexString = parsedCommand[1];
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {

        if (hasNoArgs) {
            return;
        }

        try {
            int index = Integer.parseInt(indexString) - 1;

            boolean isDone = action.equals("mark");

            tasks.getTask(index).setDone(isDone);

            ui.printBreak();

            if (isDone) {
                System.out.print("Nice job habibi! Task marked as DONE!");
            } else {
                System.out.print("Ahhh, you're not by habibi nomo! Task marked NOT DONE!");
            }
            System.out.print(ui.NL + tasks.getTask(index).toString() + ui.NL);

            ui.printBreak();

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
