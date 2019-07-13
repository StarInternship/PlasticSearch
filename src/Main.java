import controller.Controller;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Controller controller = new Controller(args[0]);
        try {
            controller.main();
        } catch (IOException e) {
            System.out.println("File doesnt exist");
        }
    }
}
