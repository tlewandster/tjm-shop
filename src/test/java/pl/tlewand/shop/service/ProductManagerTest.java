package pl.tlewand.shop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.tlewand.shop.model.Electronics;
import pl.tlewand.shop.model.Product;
import pl.tlewand.shop.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductManagerTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductManager productManager;

    @Test
    void shouldThrowNullPointerExceptionWhenRepoIsNull() {

        assertThatThrownBy(() -> new ProductManager(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Product repository cannot be null");
    }

    @Test
    void shouldAddProduct() {
        long id = 10;
        Electronics product = new Electronics(id, "Product", new BigDecimal("20.00"), 50);
        when(productRepository.find(id)).thenReturn(Optional.empty());

        productManager.add(product);

        verify(productRepository).save(product);
    }

    @Test
    void shouldThrowNullPointerExceptionWhenProductIsNull() {
        assertThatThrownBy(() -> productManager.add(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Product cannot be null");

        verifyNoInteractions(productRepository);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenProductAlreadyExists() {
        long id = 13;
        Product product = new Electronics(id, "Product", new BigDecimal("10.00"), 123);
        when(productRepository.find(id)).thenReturn(Optional.of(product));

        assertThatThrownBy(() -> productManager.add(product))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product with ID %d already exists", id);
        verify(productRepository, never()).save(any());
    }

    @Test
    void shouldFindProduct() {
        long id = 13;
        Product foundProduct = new Electronics(id, "Product", new BigDecimal("10.00"), 123);
        when(productRepository.find(id)).thenReturn(Optional.of(foundProduct));

        Product returnedProduct = productManager.find(id);

        assertThat(returnedProduct)
                .isSameAs(foundProduct);
    }

    @Test
    void shouldThrowNoSuchElementExceptionWhenProductNotFound() {
        long id = 123;
        when(productRepository.find(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productManager.find(id))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No product with ID %d was found", id);
    }

    @Test
    void shouldFindAll() {
        Product product1 = new Electronics(1, "Product1", new BigDecimal("10.00"), 100);
        Product product2 = new Electronics(2, "Product2", new BigDecimal("20.00"), 200);
        when(productRepository.findAll()).thenReturn(new ArrayList<>(List.of(product1, product2)));

        List<Product> all = productManager.findAll();

        assertThat(all).containsExactly(product1, product2);
    }

    @Test
    void shouldReturnUnmodifiableList() {
        Product product1 = new Electronics(1, "Product1", new BigDecimal("10.00"), 100);
        Product product2 = new Electronics(2, "Product2", new BigDecimal("20.00"), 200);
        when(productRepository.findAll()).thenReturn(new ArrayList<>(List.of(product1)));

        List<Product> all = productManager.findAll();

        assertThatThrownBy(() -> all.add(product2))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void shouldDeleteProduct() {
        long id = 20;
        Product product = new Electronics(id, "Product1", new BigDecimal("10.00"), 100);
        when(productRepository.find(id)).thenReturn(Optional.of(product));

        productManager.delete(id);

        verify(productRepository).delete(id);
    }

    @Test
    void shouldThrowNoSuchElementExceptionWhenDeletingUnknownProduct() {
        long id = 666;
        when(productRepository.find(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productManager.delete(id))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No product with ID %d was found", id);
        verify(productRepository, never()).delete(anyLong());
    }

    @Test
    void shouldUpdateName() {
        long id = 7;
        String newName = "newName";
        Product product1 = new Electronics(id, "Product1", new BigDecimal("10.00"), 100);
        when(productRepository.find(id)).thenReturn(Optional.of(product1));

        productManager.updateName(id, newName);

        verify(productRepository).update(product1);
        assertThat(product1.getName())
                .isEqualTo(newName);
    }

    @Test
    void shouldThrowNoSuchElementExceptionWhenUpdatingNameUnknownProduct() {
        long id = 13;
        String newName = "newName";
        when(productRepository.find(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productManager.updateName(id, newName))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No product with ID %d was found", id);
        verify(productRepository, never()).update(any());
    }

    @Test
    void shouldUpdatePrice() {
        long id = 7;
        BigDecimal newPrice = new BigDecimal("100.00");
        Product product1 = new Electronics(id, "Product1", new BigDecimal("10.00"), 100);
        when(productRepository.find(id)).thenReturn(Optional.of(product1));

        productManager.updatePrice(id, newPrice);

        verify(productRepository).update(product1);
        assertThat(product1.getPrice())
                .isEqualByComparingTo(newPrice);
    }


    @Test
    void shouldThrowNoSuchElementExceptionWhenUpdatingPriceUnknownProduct() {
        long id = 13;
        BigDecimal newPrice = new BigDecimal("250.00");
        when(productRepository.find(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productManager.updatePrice(id, newPrice))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No product with ID %d was found", id);
        verify(productRepository, never()).update(any());
    }

    @Test
    void shouldUpdateStock() {
        long id = 7;
        int newStock = 1;
        Product product1 = new Electronics(id, "Product", new BigDecimal("10.00"), 100);
        when(productRepository.find(id)).thenReturn(Optional.of(product1));

        productManager.updateStock(id, newStock);

        verify(productRepository).update(product1);
        assertThat(product1.getStock())
                .isEqualTo(newStock);
    }

    @Test
    void shouldThrowNoSuchElementExceptionWhenUpdatingStockUnknownProduct() {
        long id = 13;
        int newStock = 123;
        when(productRepository.find(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productManager.updateStock(id, newStock))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No product with ID %d was found", id);
        verify(productRepository, never()).update(any());
    }
}
