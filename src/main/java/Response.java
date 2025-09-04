public class Response {
    protected String lineBreak = "~~~~~~~~~~~~~~~~~~~~~~\n";

    public void printGreeting () {
        System.out.print(lineBreak + "Hello I'm Abdo\n" + "I'm here to assist you *_*\n" + lineBreak);
    }

    public void printExit () {
        System.out.println(lineBreak + "Bye. Come back for more help ;)\n" + lineBreak);
    }

    public void printAddTask (Task task) {
        System.out.println(lineBreak + "Great! I added the following task to your list.");
        System.out.print("- " + task.description + "\n" + lineBreak);
    }
}
