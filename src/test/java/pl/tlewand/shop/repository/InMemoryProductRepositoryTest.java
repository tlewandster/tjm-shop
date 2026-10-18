package pl.tlewand.shop.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pl.tlewand.shop.model.Electronics;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class InMemoryProductRepositoryTest {
    private final InMemoryProductRepository inMemoryProductRepository = new InMemoryProductRepository();

    @Test
    void shouldSaveNewProduct() {
        long id = 1;
        Electronics product = new Electronics(id, "Produkt", new BigDecimal("10.00"), 100);

        inMemoryProductRepository.save(product);

        assertThat(inMemoryProductRepository.find(id)).containsSame(product);
    }

    @Test
    @Disabled("TODO")
    void shouldThrowXWhenTryToSaveNull() {
        //save(null)
    }

    @Test
    @Disabled("TODO")
    void shouldUpdateProduct() {
        //update(ok) dodać najpierw zmieniany obiekt
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
