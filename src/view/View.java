package view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class View {
    private static Scanner scanner = new Scanner(System.in);

    public String readNextLine(){
        return scanner.nextLine();
    }

    public void showResult(List<File> result){
        for (File s :
                result) {
            System.out.println(s.getName());
        }
    }
}
