package pl.tlewand.shop.repository;

import pl.tlewand.shop.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    void save(Product newProduct);

    void update(Product updatedProduct);

    Optional<Product> find(long id);

    List<Product> findAll();

    void delete(long id);
}
