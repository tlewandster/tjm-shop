package pl.tlewand.shop.storage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileStorage {
    public List<String> load(Path path) throws IOException {
        if (Files.exists(path)) {
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        }
        return List.of();
    }

    public void save(Path path, String string) throws IOException {
        Files.createDirectories(path.getParent());
        Files.writeString(path, string, StandardCharsets.UTF_8);
    }
}
