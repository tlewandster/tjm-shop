package pl.tlewand.shop.model.product;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class SmartphoneTest {

    @ParameterizedTest
    @CsvSource("1,Smartphone,1000,100,BLACK,1")
    void shouldCreateSmartphoneSuccessfully(long id, String name, BigDecimal price, int stock, Colour colour, int batteryCapacityMah) {
        //when
        Smartphone smartphone = new Smartphone(id, name, price, stock, colour, batteryCapacityMah);

        //then
        assertThat(smartphone.getColour()).isEqualTo(colour);
        assertThat(smartphone.getBatteryCapacityMah()).isEqualTo(batteryCapacityMah);
    }

    @ParameterizedTest
    @CsvSource("1,Smartphone,1000,100,,1000")
    void shouldThrowNullPointerException(long id, String name, BigDecimal price, int stock, Colour colour, int batteryCapacityMah) {
        //then
        assertThatThrownBy(()->new Smartphone(id, name, price, stock, colour, batteryCapacityMah))
                .isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "1,Smartphone,1000,100,BLACK,0",
            "1,Smartphone,1000,100,BLACK,-1000",
    })
    void shouldThrowIllegalArgumentException(long id, String name, BigDecimal price, int stock, Colour colour, int batteryCapacityMah) {
        //then
        assertThatThrownBy(()->new Smartphone(id, name, price, stock, colour, batteryCapacityMah))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
