import java.util.Scanner;

public class Mark1 {
    public static void main(String[] args) {
        String lineBreak = "~~~~~~~~~~~~~~~~~~~~~~\n";
        System.out.println(lineBreak + "Hello I'm Mark I\n" + "Alssalam Alaikum. I'm here to assist you *_*\n" + lineBreak);

        Scanner in = new Scanner(System.in);
        String command = in.nextLine(); // initial request

        // do tasks if user does not want to exit
        while (!command.contains("bye")) {
            System.out.println("\n" + lineBreak + command + "\n" + lineBreak);
            command = in.nextLine();
        }

        System.out.println(lineBreak + "Bye. Come back for more help ;)\n" + lineBreak);
        in.close();


    }
}
