import java.util.Scanner;

public class Abdo {

    public static Task processTask(String command) {
        Task task = new Task(command);
        Response response = new Response();
        response.printAddTask(task);
        return task;
    }

    public static void main(String[] args) {
        Response response = new Response();
        Scanner in = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int nextTask = 0;

        response.printGreeting();

        System.out.print("> ");
        String command = in.nextLine();

        while (!command.equals("bye")) {
            String[] parsedCommand = command.split(" ");
            switch(parsedCommand[0]) {
            case "":
                break;
            case "list":
                System.out.println(response.lineBreak + "Here are the tasks in your list:");
                for (int i = 0; i < nextTask; i++) {
                    System.out.println((i + 1) + ". " + tasks[i].printTask());
                }
                System.out.print(response.lineBreak);
                break;
            case "mark":
                tasks[Integer.parseInt(parsedCommand[1]) - 1].setDone(true);
                System.out.print(response.lineBreak + "Nice job! Task marked as DONE!\n" + tasks[Integer.parseInt(parsedCommand[1]) - 1].printTask() + "\n" + response.lineBreak);
                break;
            case "unmark":
                tasks[Integer.parseInt(parsedCommand[1]) - 1].setDone(false);
                System.out.print(response.lineBreak + "Ahhh! Task marked NOT DONE!\n" + tasks[Integer.parseInt(parsedCommand[1]) - 1].printTask() + "\n" + response.lineBreak);
                break;
            default:
                tasks[nextTask] = processTask(command);
                nextTask++;
                break;
            }

            System.out.print("> ");
            command = in.nextLine();
        }

        response.printExit();
        in.close();
    }
}