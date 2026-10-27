package pl.tlewand.shop.service;

import pl.tlewand.shop.storage.FileStorage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class StockService {
    public static void load(Path path, ProductManager productManager) throws IOException {
        List<String> content = FileStorage.load(path);
        content.forEach(line -> {
            String[] lineValue = line.split(";");
            productManager.updateStock(Long.parseLong(lineValue[0]), Integer.parseInt(lineValue[1]));
        });
    }

    public static void save(Path path, ProductManager productManager) throws IOException {
        String content = productManager.findAll().stream()
                .map(product -> product.getId() + ";" + product.getStock())
                .collect(Collectors.joining("\n"));
        FileStorage.save(path, content);
    }
}
