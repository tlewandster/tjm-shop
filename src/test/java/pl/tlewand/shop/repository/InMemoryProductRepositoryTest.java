package pl.tlewand.shop.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pl.tlewand.shop.model.Electronics;
import pl.tlewand.shop.model.Product;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


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
    void shouldThrowXWhenTryToSaveNull() {

        assertThatThrownBy(() -> repository.save(null))
                .isInstanceOf(NullPointerException.class);
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
    @Disabled("TODO")
    void shouldThrowXWhenTryToUpdateNull() {
        //update(null)
    }

    @Test
    @Disabled("TODO")
    void shouldReturnProductIfExists() {
        //find(istniejące id) - dwa produkty
    }

    @Test
    @Disabled("TODO")
    void shouldReturnEmptyOptionalIfNotExist() {
        //find(nieistniejące id)
    }

    @Test
    @Disabled("TODO")
    void shouldReturnProductsList() {
        //findAll() - istniejące repo z zawartością
    }

    @Test
    @Disabled("TODO")
    void shouldReturnEmptyList() {
        //findAll() - puste repo
    }

    @Test
    @Disabled("TODO")
    void shouldThrowUnsupportedOperationExceptionWhenTryModifyResult() {
        //findAll() - próba modyfikacji zwróconej listy
    }

    @Test
    @Disabled("TODO")
    void shouldDeleteProductIfExists() {
        //delete(istniejące id)
    }

    @Test
    @Disabled("TODO")
    void shouldDoNothingWhenTryDeleteNotExistingProduct() {
        //delete(nieistniejące id)
    }
}
