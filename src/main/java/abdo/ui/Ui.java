package abdo.ui;

import abdo.AbdoException;
import abdo.task.Task;

import java.io.InputStream;
import java.util.ArrayList;
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
        System.out.println(LINEBREAK + "Bye. Come back for more help ;)" + NL + NL + LOGO);
    }

    public void printAddTask (Task task, int numOfTasks) {
        System.out.println(LINEBREAK + "Great! I added the following abdo.task to your list.");
        System.out.print("  " + task.toString() + NL + "Now you have " +
                numOfTasks + " task(s) in the list." + NL + LINEBREAK);
    }

    public void printDeleteTask (Task task, int numOfTasks) {
        System.out.println(LINEBREAK +
                "Alr, I deleted the following abdo.task from your list." + NL + "(I hope it's not because you're too lazy to do it...)");
        System.out.print("  " + task.toString() + NL + "Now you have " +
                numOfTasks + " task(s) in the list." + NL + LINEBREAK);
    }

    /**
     * Prints error message when creating a new file.
     */
    public void printLoadingError () {
        System.out.println(LINEBREAK + "File not found! Creating new file..." + NL);
    }

    public void printBreak() {
        System.out.print(LINEBREAK);
    }

    /**
     * Prints an error message for the case of
     * the command having no arguments after one
     * that expects arguments after it.
     * Eg. mark, delete, todo, event
     */
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

    /**
     * Prints the corresponding error message depending
     * on what message the AbdoException contains
     *
     * @param e exception object
     */
    public void printError(AbdoException e) {
        printBreak();
        System.out.print(e.getMessage());
        printBreak();
    }

    /**
     * Prints the list of tasks if keyword is found.
     * Used in FindCommand execute function
     *
     * @param tasks list of tasks in string format
     */
    public void printFound(ArrayList<String> tasks) {
        printBreak();
        System.out.println("Is this what you want?!?");
        for (String task : tasks) {
            System.out.println(task);
        }
        printBreak();
    }

    /**
     * Prints error message when keyword was not found
     * in any of the tasks.
     */
    public void printNotFound() {
        printBreak();
        System.out.println("Sorry brother ); No task exists with this keyword");
        printBreak();
    }

    /**
     * Prints out of bounds error message when
     * user inputs an index of a task item
     * that is not in the list.
     */
    public void printOOB() {
        printBreak();
        System.out.print("Out of bounds! Try again bromi." + NL);
        printBreak();
    }

    /**
     * Prints error message when user inputs
     * a non-number value as an index.
     */
    public void printInvalidNumArg() {
        printBreak();
        System.out.print("That's not a number shmoski!" + NL);
        printBreak();
    }

    /**
     * Prints error message and correction
     * of deadline command format when user
     * inputs deadline command incorrectly.
     */
    public String deadlineError() {
        return "Invalid format. Expected: \"deadline {description} /by {due date/time}\"" + NL;
    }

    /**
     * Prints error message and correction
     * of event command format when user
     * inputs event command incorrectly.
     */
    public String eventError() {
        return "Incorrect format! Expected: \"event {description} /from {start date/time} /to {end date/time}\"" + NL;
    }


}
