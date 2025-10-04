package abdo.command;

import abdo.storage.Storage;
import abdo.task.TaskList;
import abdo.ui.Ui;

public class ListCommand extends Command{

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.checkEmpty()) {
            ui.printBreak();
            System.out.println("Task list empty!");
        } else {
            ui.printBreak();
            System.out.println("Here are the tasks in your list:");
        }

        for (int i = 0; i < tasks.getSize(); i++) {
            System.out.println((i + 1) + ". " + tasks.getTask(i).toString());
        }

        ui.printBreak();
    }
}
