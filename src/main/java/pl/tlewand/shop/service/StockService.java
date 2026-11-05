package pl.tlewand.shop.service;

import org.apache.commons.lang3.Validate;
import pl.tlewand.shop.storage.FileStorage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class StockService {
    private static final String SEPARATOR = ";";
    private final FileStorage fileStorage;

    public StockService(FileStorage fileStorage) {
        Validate.notNull(fileStorage, "File storage can not be null");
        this.fileStorage = fileStorage;
    }

    public void load(Path path, ProductService productService) throws IOException {
        List<String> content = fileStorage.load(path);
        content.forEach(line -> {
            String[] lineValues = line.split(SEPARATOR);
            String id = lineValues[0];
            String stock = lineValues[1];
            productService.updateStock(Long.parseLong(id), Integer.parseInt(stock));
        });
    }

    public void save(Path path, ProductService productService) throws IOException {
        String content = productService.findAll().stream()
                .map(product -> product.getId() + SEPARATOR + product.getStock())
                .collect(Collectors.joining("\n"));
        fileStorage.save(path, content);
    }
}
