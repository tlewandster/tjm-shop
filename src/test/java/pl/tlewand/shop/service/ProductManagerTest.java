package pl.tlewand.shop.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.tlewand.shop.repository.ProductRepository;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductManagerTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductManager productManager;

    @Test
    void shouldThrowNPEWhenRepoIsNull() {

        assertThatThrownBy(()->new ProductManager(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Product repository cannot be null");
    }

    @Test
    @Disabled("TODO")
    void shouldAddProduct() {
        // add(ok)
        // HP
        // czy poszło save()
    }

    @Test
    @Disabled("TODO")
    void shouldThrowNPEWhenProductIsNull() {
        // add(null) -> NPE
        // czy nie poszło find()
        // czy nie poszło save()
    }

    @Test
    @Disabled("TODO")
    void shouldThrowIAEWhenProductAlreadyExist() {
        // add(nieznane id) -> NSEE + mess
        //czy poszło find()
        //czy nie poszło save()
    }

    @Test
    @Disabled("TODO")
    void shouldFindProduct() {
        // find(HP)
        // czy poszło find() -> Optional z wartością
    }

    @Test
    @Disabled("TODO")
    void shouldThrowNoSuchElementExceptionWhenProductNotFound() {
        // find(nieznane id) -> IAE + mess
        // czy poszło find() -> Optional pusty
    }

    @Test
    @Disabled("TODO")
    void shouldFindAll() {
        // findAll() -> List (jak to sprawdzić?? tak samo jak w repo??)
    }

    @Test
    @Disabled("TODO")
    void shouldThrowExceptionWhenReturnedListIsModifiable() {
        // findAll() -> List (jak to sprawdzić?? tak samo jak w repo??)
    }

    @Test
    @Disabled("TODO")
    void shouldReturnEmptyList() {
        //findAll() przy pustym repo -> pusta lista
    }

    @Test
    @Disabled("TODO")
    void shouldDeleteProduct() {
        // HP
        // czy wykonane find() -> Optional z value
        // czy wykonane delete()
    }

    @Test
    @Disabled("TODO")
    void shouldThrowNoSuchElementExceptionWhenDeletingDontKnownProduct() {
        // delete(nieznany id)
        // czy wykonane find() -> Optional pusty
        // czy nie wykonane delete
    }

    @Test
    @Disabled("TODO")
    void shouldUpdateName() {
        // updateName(HP)
        // czy wykonane find() -> Optional value
        // czy wykonany update
    }

    @Test
    @Disabled("TODO")
    void shouldThrowNoSuchElementExceptionWhenUpdatingNameDontKnownProduct() {
        // updateName(nieznany id)
        // czy wykonane find() -> Optional pusty
        // czy nie wykonane update
    }

    @Test
    @Disabled("TODO")
    void shouldUpdatePrice() {
        // updatePrice(HP)
        // czy wykonane find() -> Optional value
        // czy wykonany update
    }

    @Test
    @Disabled("TODO")
    void shouldThrowNoSuchElementExceptionWhenUpdatingPriceDontKnownProduct() {
        // updatePrice(nieznany id)
        // czy wykonane find() -> Optional pusty
        // czy nie wykonane update
    }

    @Test
    @Disabled("TODO")
    void shouldUpdateStock() {
        // updateStock(HP)
        // czy wykonane find() -> Optional value
        // czy wykonany update
    }

    @Test
    @Disabled("TODO")
    void shouldThrowNoSuchElementExceptionWhenUpdatingStockDontKnownProduct() {
        // updateStock(nieznany id)
        // czy wykonane find() -> Optional pusty
        // czy nie wykonane update
    }
}
