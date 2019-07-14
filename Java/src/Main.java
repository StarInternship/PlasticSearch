import controller.Controller;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Controller controller = new Controller(args[0]);
            controller.main();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Enter path as program argument.");
        } catch (IOException e) {
            System.out.println("File doesnt exist");
        }
    }
}
