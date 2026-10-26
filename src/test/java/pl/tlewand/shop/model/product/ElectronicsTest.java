package pl.tlewand.shop.model.product;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ElectronicsTest {
    @ParameterizedTest
    @CsvSource({
            "1, Product, 10, 100",
            "1, Product, 0, 0"
    })
    void shouldCreateElectronicsSuccessfully(long id, String name, BigDecimal price, int stock) {
        //when
        Electronics electronics = new Electronics(id, name, price, stock);

        //then
        assertThat(electronics.getId()).isEqualTo(id);
        assertThat(electronics.getName()).isEqualTo(name);
        assertThat(electronics.getPrice()).isEqualByComparingTo(price);
        assertThat(electronics.getStock()).isEqualTo(stock);
    }

}
