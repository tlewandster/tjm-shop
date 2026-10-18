package pl.tlewand.shop.service;

import org.apache.commons.lang3.Validate;
import pl.tlewand.shop.model.Product;
import pl.tlewand.shop.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProductManager {
    private final ProductRepository productRepository;

    public ProductManager(ProductRepository productRepository) {
        Validate.notNull(productRepository, "Product repository cannot be null");
        this.productRepository = productRepository;
    }

    public void add(Product product) {
        Validate.notNull(product, "Product cannot be null");
        Validate.isTrue(
                productRepository.find(product.getId()).isEmpty(),
                "Product with ID %d already exists", product.getId()
        );
        productRepository.save(product);
    }

    public Product find(long id) {
        return null;
    }

    public List<Product> findAll() {
        return List.of();
    }

    public Product delete(long id) {
        return null; //tu będzie zwracany produkt, który usuniemy
    }

    public Product updateName(long id, String newName) {
        return null; //tu będzie zwracany zmieniany obiekt
    }

    public Product updatePrice(long id, BigDecimal newPrice) {
        return null; //tu będzie zwracany zmieniany obiekt
    }

    public Product updateStock(long id, int newStock) {
        return null; //tu będzie zwracany zmieniany obiekt
    }
}

