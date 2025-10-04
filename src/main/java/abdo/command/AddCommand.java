package abdo.command;

import abdo.AbdoException;
import abdo.storage.Storage;
import abdo.task.Deadline;
import abdo.task.Event;
import abdo.task.Task;
import abdo.task.TaskList;
import abdo.task.Todo;
import abdo.ui.Ui;

import java.io.IOException;

public class AddCommand extends Command {

    private String command;
    private String type;
    private String description;
    private String by;
    private String from;
    private String to;
    private boolean hasNoArgs;

    public AddCommand(Ui ui, String command, String[] parsedCommand) {
        if (parsedCommand.length == 1) {
            this.hasNoArgs = true;
            ui.printNoArgs(parsedCommand[0]);
            return;
        }

        this.command = command;
        this.type = parsedCommand[0];
    }

    /**
     * Creates a Todo object from user command.
     *
     * @param unparsedDesc string list from user input
     * @return the created Todo object
     */
    private Todo createTodo(String unparsedDesc) {
        return new Todo(unparsedDesc);
    }

    /**
     * Parses user command and returns Deadline object from
     * that input.
     *
     * @param unparsedDesc string list from user input
     * @return the created Deadline object
     */
    private Deadline createDeadline(String unparsedDesc) {
        String[] parsedDesc = unparsedDesc.split("/by");
        description = parsedDesc[0].trim();
        by = parsedDesc[1].trim();
        return new Deadline(description, by);
    }

    /**
     * Parses user command and returns Event object from
     * that input.
     *
     * @param unparsedDesc string list from user input
     * @return the created Event object
     */
    private Event createEvent(String unparsedDesc) {
        String[] parsedDesc = unparsedDesc.split("/from|/to");
        description = parsedDesc[0].trim();
        from = parsedDesc[1].trim();
        to = parsedDesc[2].trim();
        return new Event(description, from, to);
    }

    /**
     * Processes the input and creates the corresponding task.
     *
     * @param ui the UI handler
     * @return the created task
     * @throws AbdoException if task creation fails due to invalid input
     */
    public Task processTask(Ui ui) throws AbdoException {
        Task task;
        String unparsedDesc = command.substring(type.length() + 1);

        switch(type) {
        case "todo":
            task = createTodo(unparsedDesc);
            break;
        case "deadline":
            try {
                task = createDeadline(unparsedDesc);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new AbdoException(ui.deadlineError());
            }
            break;
        case "event":
            try {
                task = createEvent(unparsedDesc);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new AbdoException(ui.eventError());
            }
            break;
        default:
            task = null;
        }

        return task;
    }

    /**
     * Executes the add command, adding a task to the list and saving it.
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
            tasks.addTask(processTask(ui));
            ui.printAddTask(tasks.getTask(tasks.getSize()-1), tasks.getSize());
            try {
                storage.updateFile(tasks.getTasks());
            } catch (IOException e) {
                ui.printSaveError();
            }
        } catch (AbdoException e) {
            ui.printError(e);
        }
    }
}
