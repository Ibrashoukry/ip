package abdo.parser;

import abdo.command.AddCommand;
import abdo.command.Command;
import abdo.command.DeleteCommand;
import abdo.command.ExitCommand;
import abdo.command.InvalidCommand;
import abdo.command.ListCommand;
import abdo.command.StatusCommand;
import abdo.ui.Ui;

public class Parser {

    public static Command parse(String command) {
        Ui ui = new Ui();
        Command c;

        String[] parsedCommand = command.split(" ");

        switch(parsedCommand[0]) {
        case "bye":
            c = new ExitCommand();
            break;
        case "list":
            c = new ListCommand();
            break;
        case "mark":
        case "unmark":
            c = new StatusCommand(ui, parsedCommand);
            break;
        case "delete":
            c = new DeleteCommand(ui, parsedCommand);
            break;
        case "todo":
        case "deadline":
        case "event":
            c = new AddCommand(ui, command, parsedCommand);
            break;
        default:
            c = new InvalidCommand(ui);
            break;
        }

        return c;
    }
}
