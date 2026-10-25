package pl.tlewand.shop.repository;

import org.apache.commons.lang3.Validate;
import pl.tlewand.shop.model.product.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryProductRepository implements ProductRepository {

    private final Map<Long, Product> products = new HashMap<>();

    @Override
    public void save(Product newProduct) {
        Validate.notNull(newProduct, "Product being saved cannot be null");
        products.put(newProduct.getId(), newProduct);
    }

    @Override
    public void update(Product updatedProduct) {
        Validate.notNull(updatedProduct, "Product being updated cannot be null");
        save(updatedProduct);
    }

    @Override
    public Optional<Product> find(long id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public List<Product> findAll() {
        return List.copyOf(products.values());
    }

    @Override
    public void delete(long id) {
        products.remove(id);
    }
}
