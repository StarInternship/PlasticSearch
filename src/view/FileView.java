package view;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class FileView {
    private final String PATH;

    public FileView(String path) {
        PATH = path;
    }

    public Map<File, String> readFiles() throws IOException {
        HashMap<File, String> filesHashMap = new HashMap<>();
        File file = new File(PATH);
        readFile(filesHashMap, file);
        return filesHashMap;
    }

    private void readFile(HashMap<File, String> filesHashMap, File parent) throws IOException {
        if (parent.isDirectory()) {
            File[] files = parent.listFiles();

            if (files != null) {
                for (File child : files) {
                    readFile(filesHashMap, child);
                }
            }
            return;
        }
        String text = new String(Files.readAllBytes(parent.toPath()));
        filesHashMap.put(parent, text);
    }
}
