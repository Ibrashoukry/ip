import java.util.Scanner;

public class Abdo {

    public static final int MAX_TASKS = 100;

    /** Processes user input if it one of TODO, EVENT, or DEADLINE and returns a task object with
     * initialized values */
    public static Task processTask(String command, String type) throws AbdoException{

        Printer printer = new Printer();
        String[] parsedDesc;
        String description, by, from, to;
        Task task;

        String unparsedDesc = command.substring(type.length() + 1);

        switch(type) {
        case "todo":
            task = new Todo(unparsedDesc);
            break;
        case "deadline":
            try {
                parsedDesc = unparsedDesc.split("/by");
                description = parsedDesc[0].trim();
                by = parsedDesc[1].trim();
                task = new Deadline(description, by);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new AbdoException(printer.lineBreak +
                        "Invalid format. Expected: \"deadline {description} /by {due date/time}\"" +
                        System.lineSeparator() + printer.lineBreak);
            }
            break;
        case "event":
            try {
                parsedDesc = unparsedDesc.split("/from|/to");
                description = parsedDesc[0].trim();
                from = parsedDesc[1].trim();
                to = parsedDesc[2].trim();
                task = new Event(description, from, to);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new AbdoException(printer.lineBreak +
                        "Incorrect format! Expected: \"event {description} /from {start date/time} /to {end date/time}\"" +
                        System.lineSeparator() + printer.lineBreak);
            }
            break;
        default:
            task = new Task(command);
        }
        return task;
    }

    public static void main(String[] args) {

        Printer printer = new Printer();
        Scanner in = new Scanner(System.in);
        Task[] tasks = new Task[MAX_TASKS];
        int nextTask = 0;

        printer.printGreeting();

        String command = in.nextLine();

        while (!command.equals("bye")) {
            String[] parsedCommand = command.split(" ");
            switch(parsedCommand[0]) {
            case "list":
                System.out.println(printer.lineBreak + "Here are the tasks in your list:");
                for (int i = 0; i < nextTask; i++) {
                    System.out.println((i + 1) + ". " + tasks[i].toString());
                }
                System.out.print(printer.lineBreak);
                break;
            case "mark":
                tasks[Integer.parseInt(parsedCommand[1]) - 1].setDone(true);
                System.out.print(printer.lineBreak + "Nice job! Task marked as DONE!\n" +
                        tasks[Integer.parseInt(parsedCommand[1]) - 1].toString() + "\n" + printer.lineBreak);
                break;
            case "unmark":
                tasks[Integer.parseInt(parsedCommand[1]) - 1].setDone(false);
                System.out.print(printer.lineBreak + "Ahhh! Task marked NOT DONE!\n" +
                        tasks[Integer.parseInt(parsedCommand[1]) - 1].toString() + "\n" + printer.lineBreak);
                break;
            case "todo":
            case "deadline":
            case "event":
                // handles error where no other info has been inputted except the command
                if (parsedCommand.length == 1) {
                    System.out.print(printer.lineBreak +
                            "I don't understand. You have to give \"" + parsedCommand[0] +
                            "\" a description!!!" + System.lineSeparator() + printer.lineBreak);
                    break;
                }

                try {
                    tasks[nextTask] = processTask(command, parsedCommand[0]);
                    printer.printAddTask(tasks[nextTask], nextTask + 1);
                    nextTask++;
                } catch (AbdoException e) {
                    System.out.print(e.getMessage());
                }
                break;
            default:
                System.out.print(printer.lineBreak +
                        "Not a real command... you should know better!" + System.lineSeparator() + printer.lineBreak);
                break;
            }

            System.out.print("> ");
            command = in.nextLine();
        }

        printer.printExit();
        in.close();
    }
}