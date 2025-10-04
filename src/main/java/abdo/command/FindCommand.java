package abdo.command;

import abdo.storage.Storage;
import abdo.task.TaskList;
import abdo.ui.Ui;

import java.util.ArrayList;

public class FindCommand extends Command {

    private String keyword;
    private boolean hasNoArgs;

    public FindCommand(Ui ui, String[] parsedCommand) {
        if (parsedCommand.length == 1) {
            this.hasNoArgs = true;
            ui.printNoArgs(parsedCommand[0]);
            return;
        }

        this.keyword = parsedCommand[1];
    }

    /**
     * Executes the find command, finding the tasks that contain
     * the specified keyword and prints out results.
     *
     * @param tasks the task list
     * @param ui the UI handler
     * @param storage he storage handler
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {

        if (hasNoArgs) {
            return;
        }

        int taskNum = 0;
        ArrayList<String> taskStringList = new ArrayList<>();
        String taskString;

        for (int i = 0; i < tasks.getSize(); i++) {
           taskString = tasks.getTask(i).toString();

           if (taskString.contains(keyword)) {
               taskStringList.add((taskNum + 1) + ". " + taskString);
               taskNum++;
           }
        }

        if (taskStringList.isEmpty()) {
            ui.printNotFound();
        } else {
            ui.printFound(taskStringList);
        }
    }
}
