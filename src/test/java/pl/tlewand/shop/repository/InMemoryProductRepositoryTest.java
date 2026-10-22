package pl.tlewand.shop.repository;

import org.junit.jupiter.api.Test;
import pl.tlewand.shop.model.Electronics;
import pl.tlewand.shop.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InMemoryProductRepositoryTest {
    private final InMemoryProductRepository repository = new InMemoryProductRepository();

    @Test
    void shouldSaveNewProduct() {
        long id = 1;
        Electronics product = new Electronics(id, "Produkt", new BigDecimal("10.00"), 100);

        repository.save(product);

        assertThat(repository.find(id))
                .containsSame(product);
    }

    @Test
    void shouldThrowNullPointerExceptionWhenTryToSaveNull() {

        assertThatThrownBy(() -> repository.save(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Product being saved cannot be null");
    }

    @Test
    void shouldUpdateProduct() {

        long id = 10;
        Product product = new Electronics(id, "Product", new BigDecimal("10.00"), 200);
        repository.save(product);
        Product updatedProduct = new Electronics(id, "Product", new BigDecimal("20.00"), 200);

        repository.update(updatedProduct);

        assertThat(repository.find(id))
                .containsSame(updatedProduct);

    }

    @Test
    void shouldThrowNullPointerExceptionWhenTryToUpdateNull() {

        assertThatThrownBy(() -> repository.update(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Product being updated cannot be null");
    }

    @Test
    void shouldReturnProductIfExists() {

        Product product = new Electronics(10, "Product", new BigDecimal("10.00"), 200);
        repository.save(product);
        long searchedId = 20;
        Product searchedProduct = new Electronics(searchedId, "Product", new BigDecimal("20.00"), 200);
        repository.save(searchedProduct);

        Optional<Product> foundProduct = repository.find(searchedId);

        assertThat(foundProduct)
                .containsSame(searchedProduct);
    }

    @Test
    void shouldReturnEmptyOptionalIfNotExists() {

        Product product = new Electronics(10, "Product", new BigDecimal("10.00"), 200);
        repository.save(product);
        long searchedId = 20;

        Optional<Product> foundProduct = repository.find(searchedId);

        assertThat(foundProduct)
                .isEmpty();
    }

    @Test
    void shouldReturnProductsList() {

        Product product1 = new Electronics(1, "Product1", new BigDecimal("10.00"), 100);
        Product product2 = new Electronics(2, "Product2", new BigDecimal("20.00"), 200);
        Product product3 = new Electronics(3, "Product3", new BigDecimal("30.00"), 300);
        repository.save(product1);
        repository.save(product2);
        repository.save(product3);

        List<Product> all = repository.findAll();

        assertThat(all)
                .containsExactly(product1, product2, product3);
    }

    @Test
    void shouldReturnEmptyList() {

        List<Product> all = repository.findAll();

        assertThat(all)
                .isEmpty();
    }

    @Test
    void shouldThrowUnsupportedOperationExceptionWhenTryModifyResult() {

        Product product1 = new Electronics(1, "Product1", new BigDecimal("10.00"), 100);
        Product product2 = new Electronics(2, "Product2", new BigDecimal("20.00"), 200);
        repository.save(product1);
        List<Product> all = repository.findAll();

        assertThatThrownBy(() -> all.add(product2))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void shouldDeleteProductIfExists() {
        long idToDelete = 1;
        Product product1 = new Electronics(idToDelete, "Product1", new BigDecimal("10.00"), 100);
        Product product2 = new Electronics(2, "Product2", new BigDecimal("20.00"), 200);
        repository.save(product1);
        repository.save(product2);

        repository.delete(idToDelete);

        assertThat(repository.findAll())
                .containsExactly(product2);
    }

    @Test
    void shouldDoNothingWhenTryDeleteNotExistingProduct() {

        Product product1 = new Electronics(1, "Product1", new BigDecimal("10.00"), 100);
        repository.save(product1);

        repository.delete(2);

        assertThat(repository.findAll())
                .containsExactly(product1);
    }
}
