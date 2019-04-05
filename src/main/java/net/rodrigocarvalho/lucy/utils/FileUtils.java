package net.rodrigocarvalho.lucy.utils;

import lombok.var;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    public static void create(String name) {
        var path = Paths.get(name);
        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
                var is = FileUtils.class.getResourceAsStream("/" + name);
                var os = Files.newOutputStream(path);
                int b;
                while ((b = is.read()) != -1) {
                    os.write(b);
                }
                os.flush();
                os.close();
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean exists(String path) {
        Path localPath = Paths.get(path);
        return Files.exists(localPath);
    }
}