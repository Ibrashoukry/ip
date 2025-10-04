package abdo.ui;

import abdo.AbdoException;
import abdo.task.Task;

import java.io.InputStream;
import java.util.Scanner;

public class Ui {

    public final String NL = System.lineSeparator();
    public final String LINEBREAK = "~~~~~~~~~~~~~~~~~~~~~~" + NL;
    private final String LOGO =
                    " ░▒▓██████▓▒░░▒▓███████▓▒░░▒▓███████▓▒░ ░▒▓██████▓▒░  \n" +
                    "░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░ \n" +
                    "░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░ \n" +
                    "░▒▓████████▓▒░▒▓███████▓▒░░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░ \n" +
                    "░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░ \n" +
                    "░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░ \n" +
                    "░▒▓█▓▒░░▒▓█▓▒░▒▓███████▓▒░░▒▓███████▓▒░ ░▒▓██████▓▒░  \n" +
                    "                                                      \n" +
                    "                                                      \n";

    private Scanner in;

    public Ui() {
        this(System.in);
    }

    public Ui(InputStream in) {
        this.in = new Scanner(in);
    }

    public String readCommand() {
        System.out.print("> ");
        return in.nextLine();
    }

    public void printGreeting () {
        System.out.print(LINEBREAK + "Hello I'm" + NL + NL + LOGO + "I'm here to assist you *_*" + NL + LINEBREAK);
    }

    public void printExit () {
        System.out.println(LINEBREAK + "Bye. Come back for more help ;)" + NL + LOGO);
    }

    public void printAddTask (Task task, int numOfTasks) {
        System.out.println(LINEBREAK + "Great! I added the following abdo.task to your list.");
        System.out.print("  " + task.toString() + NL + "Now you have " +
                numOfTasks + " task(s) in the list." + NL + LINEBREAK);
    }

    public void printDeleteTask (Task task, int numOfTasks) {
        System.out.println(LINEBREAK +
                "Alr, I deleted the following abdo.task from your list. (I hope it's not because you're too lazy to do it...)");
        System.out.print("  " + task.toString() + NL + "Now you have " +
                numOfTasks + " task(s) in the list." + NL + LINEBREAK);
    }

    public void printLoadingError () {
        System.out.println(LINEBREAK + "File not found! Creating new file..." + NL);
    }

    public void printBreak() {
        System.out.print(LINEBREAK);
    }

    public void printNoArgs(String type) {
            printBreak();
            System.out.print("No! You have to add something after \"" + type + "\"" + NL);
            printBreak();
    }

    public void printSaveError() {
        printBreak();
        System.out.print("Error saving file!" + NL);
        printBreak();
    }

    public void printInvalidCommand() {
        printBreak();
        System.out.print("Not a real command... you should know better!" + NL);
        printBreak();
    }

    public void printError(AbdoException e) {
        printBreak();
        System.out.print(e.getMessage());
        printBreak();
    }

    public String deadlineError() {
        return "Invalid format. Expected: \"deadline {description} /by {due date/time}\"" + NL;
    }

    public String eventError() {
        return "Incorrect format! Expected: \"event {description} /from {start date/time} /to {end date/time}\"" + NL;
    }


}
