import java.util.Scanner;
import java.util.Vector;

public class Mark1 {
    public static void main(String[] args) {
        String lineBreak = "~~~~~~~~~~~~~~~~~~~~~~\n";
        System.out.println(lineBreak + "Hello I'm Mark I\n" + "Alssalam Alaikum. I'm here to assist you *_*\n" + lineBreak);

        Scanner in = new Scanner(System.in);
        String command = in.nextLine(); // initial request
        Vector<String> tasks = new Vector<>();

        // do tasks if user does not want to exit
        while (!command.contains("bye")) {
            if (command.equals("list")) {
                System.out.println(lineBreak);
                for (String task : tasks) {
                    System.out.println((tasks.indexOf(task) + 1) + ". " + task);
                }
                System.out.println(lineBreak);
            } else {
                System.out.println(lineBreak + "added: " + command + "\n" + lineBreak);
                tasks.add(command);
            }

            command = in.nextLine();

        }

        System.out.println(lineBreak + "Bye. Come back for more help ;)\n" + lineBreak);
        in.close();


    }
}
