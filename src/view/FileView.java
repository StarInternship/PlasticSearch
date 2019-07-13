package view;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FileView {
    private final String PATH;

    public FileView(String path){
        PATH = path;
    }

    public Map<File, String> readFiles() throws IOException {
        HashMap<File, String> filesHashMap = new HashMap<>();
        File file = new File(PATH);
        readFile(filesHashMap,file);
        return filesHashMap;
    }
    public void readFile( HashMap<File, String> filesHashMap ,File parent) throws IOException {
        if (parent.isDirectory()) {
            for (File child :
                    parent.listFiles()) {
                readFile(filesHashMap,child);
            }
            return;
        }
        String s = new String(Files.readAllBytes(parent.toPath()));
        filesHashMap.put(parent,s);
    }
}
