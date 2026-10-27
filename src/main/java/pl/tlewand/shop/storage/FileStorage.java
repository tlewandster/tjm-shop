package pl.tlewand.shop.storage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileStorage {
    public static List<String> load(Path path) throws IOException {
        if (Files.exists(path)) {
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        }
        return List.of();
    }

    public static void save(Path path, String string) throws IOException {
        if (Files.notExists(path)) {
            Files.createFile(path);
        }
        Files.writeString(path, string, StandardCharsets.UTF_8);
    }
}
