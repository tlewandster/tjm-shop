package pl.tlewand.shop.service;

import org.apache.commons.lang3.Validate;
import pl.tlewand.shop.storage.FileStorage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class StockService {
    private final FileStorage fileStorage;

    public StockService(FileStorage fileStorage) {
        Validate.notNull(fileStorage,"File storage can not be null");
        this.fileStorage = fileStorage;
    }

    public void load(Path path, ProductManager productManager) throws IOException {
        List<String> content = fileStorage.load(path);
        content.forEach(line -> {
            String[] lineValue = line.split(";");
            productManager.updateStock(Long.parseLong(lineValue[0]), Integer.parseInt(lineValue[1]));
        });
    }

    public void save(Path path, ProductManager productManager) throws IOException {
        String content = productManager.findAll().stream()
                .map(product -> product.getId() + ";" + product.getStock())
                .collect(Collectors.joining("\n"));
        fileStorage.save(path, content);
    }
}
