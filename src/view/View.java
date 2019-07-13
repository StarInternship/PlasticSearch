package view;

import java.io.File;
import java.util.Scanner;
import java.util.Set;

public class View {
    private static Scanner scanner = new Scanner(System.in);

    public String readNextLine() {
        return scanner.nextLine();
    }

    public void showResult(Set<File> result) {
        if (result.isEmpty()) {
            System.out.println("not found");
            return;
        }
        for (File file : result) {
            System.out.println(file.getPath());
        }
    }
}
