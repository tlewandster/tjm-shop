package pl.tlewand.shop.service;

import org.apache.commons.lang3.Validate;
import pl.tlewand.shop.model.Product;
import pl.tlewand.shop.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

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
        return productRepository.find(id).orElseThrow(
                () -> new NoSuchElementException("No product with ID %d was found".formatted(id))
        );
    }

    public List<Product> findAll() {
        return List.copyOf(productRepository.findAll());
    }

    public void delete(long id) {
        find(id);
        productRepository.delete(id);
    }

    public void updateName(long id, String newName) {
        Product product = find(id);
        product.setName(newName);
        productRepository.update(product);
    }

    public void updatePrice(long id, BigDecimal newPrice) {
        Product product = find(id);
        product.setPrice(newPrice);
        productRepository.update(product);
    }

    public void updateStock(long id, int newStock) {
        Product product = find(id);
        product.setStock(newStock);
        productRepository.update(product);
    }
}

