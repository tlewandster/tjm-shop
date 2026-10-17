package pl.tlewand.shop.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ProductTest {
    @ParameterizedTest
    @CsvSource({
            "1, Product, 10, 100",
            "1, Product, 0, 0"
    })
    void shouldCreateProductSuccessfully(long id, String name, BigDecimal price, int stock) {
        //when
        Product product = new TestProduct(id, name, price, stock);

        //then
        assertThat(product.getId()).isEqualTo(id);
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getPrice()).isEqualByComparingTo(price);
        assertThat(product.getStock()).isEqualTo(stock);
    }

    @ParameterizedTest
    @CsvSource({
            "-5, Product, 10, 100",
            "0, Product, 10, 100",
            "1, '', 10, 100",
            "1, ' ', 10, 100",
            "1, Product, '-0.01', 100",
            "1, Product, 10, -1",
    })
    void shouldThrowIllegalArgumentException(long id, String name, BigDecimal price, int stock) {
        //then
        assertThatThrownBy(() -> new TestProduct(id, name, price, stock))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "1, , 10, 100",
            "1, Product, , 100",
    })
    void shouldThrowNullPointerException(long id, String name, BigDecimal price, int stock) {
        //then
        assertThatThrownBy(() -> new TestProduct(id, name, price, stock))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldSetName() {
        //given
        Product product = properlyCreatedProduct();
        String newName = "Other product";

        //when
        product.setName(newName);

        //then
        assertThat(product.getName())
                .isEqualTo(newName);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void shouldThrowExceptionWhileSetNameWithBlankOrEmptyString(String newName) {
        //given
        Product product = properlyCreatedProduct();
        //then
        assertThatThrownBy(() -> product.setName(newName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @NullSource
    void shouldThrowNullPointerExceptionWhileSetNameWithNull(String newName) {
        //given
        Product product = properlyCreatedProduct();
        //then
        assertThatThrownBy(() -> product.setName(newName))
                .isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "10"})
    void shouldSetPrice(String newPrice) {
        //given
        Product product = properlyCreatedProduct();

        //when
        product.setPrice(new BigDecimal(newPrice));

        //then
        assertThat(product.getPrice())
                .isEqualByComparingTo(newPrice);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhileSetPriceWithNegativeValue() {
        //given
        Product product = properlyCreatedProduct();
        BigDecimal newPrice = new BigDecimal("-0.01");
        //then
        assertThatThrownBy(() -> product.setPrice(newPrice))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowNullPointerExceptionWhileSetPriceWithNull() {
        //given
        Product product = properlyCreatedProduct();
        //then
        assertThatThrownBy(() -> product.setPrice(null))
                .isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 10})
    void shouldSetStock(int newStock) {
        //given
        Product product = properlyCreatedProduct();

        //when
        product.setStock(newStock);

        //then
        assertThat(product.getStock())
                .isEqualTo(newStock);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhileSetStockWithNegativeValue() {
        //given
        Product product = properlyCreatedProduct();
        int newStock = -1;

        //then
        assertThatThrownBy(() -> product.setStock(newStock))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private TestProduct properlyCreatedProduct() {
        return new TestProduct(1, "Product", new BigDecimal(1000), 100);
    }

    private static class TestProduct extends Product {
        public TestProduct(long id, String name, BigDecimal price, int stock) {
            super(id, name, price, stock);
        }
    }
}
