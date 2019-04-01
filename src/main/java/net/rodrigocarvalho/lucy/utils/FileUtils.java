package net.rodrigocarvalho.lucy.utils;

import java.nio.file.Files;
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
}