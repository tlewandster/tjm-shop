package pl.tlewand.shop.repository;

import org.apache.commons.lang3.Validate;
import pl.tlewand.shop.model.product.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static java.util.logging.Logger.getLogger;

/// Logs every repository operation to logs/shop.log and delegates it to the wrapped repository.
///
/// Proxy pattern. A layer between its clients and any implementation of [ProductRepository],
///  responsible for logging all operations to file. When file cannot be opened, logs are sent to console.
public final class LoggingProductRepository implements ProductRepository {

    private static final String LOGS_DIR_NAME = "logs";
    private static final Logger LOGGER = getLogger(LoggingProductRepository.class.getName());

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tF %1$tT %4$s %5$s%n");
        try {
            Path logsDir = Path.of(LOGS_DIR_NAME);
            Files.createDirectories(logsDir);
            boolean appendMode = true;
            FileHandler fileHandler = new FileHandler(LOGS_DIR_NAME + "/shop.log", appendMode);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.setUseParentHandlers(false);
        } catch (IOException e) {
            System.err.println("Problem starting the logger. Operations on products will not be logged");
        }
    }

    private final ProductRepository delegate;

    public LoggingProductRepository(ProductRepository delegate) {
        Validate.notNull(delegate, "delegate cannot be null");
        this.delegate = delegate;
    }

    @Override
    public void save(Product newProduct) {
        LOGGER.log(Level.INFO, "Saving id=%s".formatted(newProduct));
        delegate.save(newProduct);
        LOGGER.log(Level.INFO, "Product saved with id=%d".formatted(newProduct.getId()));
    }

    @Override
    public void update(Product updatedProduct) {
        LOGGER.log(Level.INFO, "Updating id=%s".formatted(updatedProduct));
        delegate.update(updatedProduct);
        LOGGER.log(Level.INFO, "Product with id=%d updated".formatted(updatedProduct.getId()));
    }

    @Override
    public Optional<Product> find(long id) {
        LOGGER.log(Level.INFO, "Searching for product id=" + id);
        Optional<Product> result = delegate.find(id);
        result.ifPresentOrElse(product -> LOGGER.log(Level.INFO, "Found %s".formatted(product.getName())),
                () -> LOGGER.log(Level.INFO, "Product with id=%d not found".formatted(id))
        );
        return result;
    }

    @Override
    public List<Product> findAll() {
        LOGGER.log(Level.INFO, "Searching for all products");
        List<Product> all = delegate.findAll();
        LOGGER.log(Level.INFO, "%d products found".formatted(all.size()));
        return all;
    }

    @Override
    public void delete(long id) {
        LOGGER.log(Level.INFO, "Deleting product with id=%d".formatted(id));
        delegate.delete(id);
        LOGGER.log(Level.INFO, "Deleted product with id=%d".formatted(id));
    }
}
