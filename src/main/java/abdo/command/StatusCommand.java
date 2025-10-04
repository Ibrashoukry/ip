package abdo.command;

import abdo.storage.Storage;
import abdo.task.TaskList;
import abdo.ui.Ui;

import java.io.IOException;

public class StatusCommand extends Command {

    private String action;
    private int index;

    public StatusCommand(Ui ui, String[] parsedCommand) {
        if (parsedCommand.length == 1) {
            ui.printNoArgs(parsedCommand[0]);
        }

        this.action = parsedCommand[0];
        this.index = Integer.parseInt(parsedCommand[1]) - 1;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {

        try {
            boolean isDone = action.equals("mark");

            tasks.getTask(index).setDone(isDone);

            ui.printBreak();

            if (isDone) {
                System.out.print("Nice job! Task marked as DONE!");
            } else {
                System.out.print("Ahhh! Task marked NOT DONE!");
            }
            System.out.print(ui.NL + tasks.getTask(index).toString() + ui.NL);
            ui.printBreak();

            try {
                storage.updateFile(tasks.getTasks());

            } catch (IOException e) {
                ui.printSaveError();
            }

        } catch (IndexOutOfBoundsException e) {

            ui.printBreak();
            System.out.print("Out of bounds! Try again." + System.lineSeparator());
            ui.printBreak();

        } catch (NumberFormatException e) {

            ui.printBreak();
            System.out.print("That's not a number!" + System.lineSeparator());
            ui.printBreak();

        }
    }
}
