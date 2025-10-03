package abdo.process;

public class Printer {

    public String lineBreak = "~~~~~~~~~~~~~~~~~~~~~~\n";

    public void printGreeting () {
        System.out.print(lineBreak + "Hello I'm abdo.ui.Abdo\n" + "I'm here to assist you *_*\n" + lineBreak);
        System.out.print("> ");
    }

    public void printExit () {
        System.out.println(lineBreak + "Bye. Come back for more help ;)\n" + lineBreak);
    }

    public void printAddTask (Task task, int numOfTasks) {
        System.out.println(lineBreak + "Great! I added the following task to your list.");
        System.out.print("  " + task.toString() + "\n" +
                "Now you have " + numOfTasks + " task(s) in the list.\n" + lineBreak);
    }
}
