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
        for (File s : result) {
            System.out.println(s.getName());
        }
    }
}
