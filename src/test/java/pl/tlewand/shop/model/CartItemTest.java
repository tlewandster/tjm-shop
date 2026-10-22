package pl.tlewand.shop.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CartItemTest {
    private static final long ITEM_ID = 1;
    private static final Electronics ELECTRONICS = new Electronics(1, "Electronics", new BigDecimal("10.00"), 100);
    private static final Computer COMPUTER = new Computer(2, "Computer", new BigDecimal("2000.00"), 200, Cpu.AMD_RYZEN_7, Gpu.NVIDIA_RTX_4060, Ram._16GB);
    private static final ComputerConfig COMPUTER_CONFIG = new ComputerConfig(Cpu.AMD_RYZEN_7, Gpu.NVIDIA_RTX_4060, Ram._16GB);
    private static final int QTY = 1;

    @Test
    void shouldCreateCartItemWithComputerConfig() {
        CartItem cartItem = new CartItem(ITEM_ID, COMPUTER, COMPUTER_CONFIG, QTY);

        assertThat(cartItem.getItemId()).isEqualTo(ITEM_ID);
        assertThat(cartItem.getProduct()).isEqualTo(ELECTRONICS);
        assertThat(cartItem.getItemConfig()).isEqualTo(COMPUTER_CONFIG);
        assertThat(cartItem.getQty()).isEqualTo(QTY);
    }

    @Test
    void shouldCreateCartItemWithoutComputerConfig() {
        CartItem cartItem = new CartItem(ITEM_ID, ELECTRONICS, null, QTY);

        assertThat(cartItem.getItemId()).isEqualTo(ITEM_ID);
        assertThat(cartItem.getProduct()).isEqualTo(ELECTRONICS);
        assertThat(cartItem.getItemConfig()).isNull();
        assertThat(cartItem.getQty()).isEqualTo(QTY);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void shouldThrowWhenQtyNotPositive(int nonPositiveQty) {
        assertThatThrownBy(() -> new CartItem(ITEM_ID, ELECTRONICS, COMPUTER_CONFIG, nonPositiveQty))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantity must be > 0");
    }

    @Test
    void shouldIncreaseQty() {
        CartItem cartItem = new CartItem(ITEM_ID, ELECTRONICS, COMPUTER_CONFIG, QTY);
        int addedQty = 2;

        cartItem.increaseQtyBy(addedQty);

        assertThat(cartItem.getQty()).isEqualTo(QTY + addedQty);
    }

    @Test
    void shouldReturnPriceForNonComputer() {
        int qty = 5;
        CartItem cartItem = new CartItem(ITEM_ID, ELECTRONICS, null, qty);

        BigDecimal price = cartItem.calculateUnitItemPrice();

        assertThat(price)
                .isEqualByComparingTo(ELECTRONICS.getPrice());
    }

    @Test
    void shouldReturnComputerPriceForDefaultConfig() {
        int qty = 4;
        CartItem cartItem = new CartItem(ITEM_ID, COMPUTER, COMPUTER_CONFIG, qty);

        BigDecimal price = cartItem.calculateUnitItemPrice();

        assertThat(price)
                .isEqualByComparingTo(COMPUTER.getPrice());
    }

    @Test

    void shouldIncreasePriceForMoreExpensiveComponent() {
        ComputerConfig customConfig = new ComputerConfig(COMPUTER_CONFIG.cpu(), COMPUTER_CONFIG.gpu(), Ram._32GB);
        BigDecimal priceDifference = customConfig.ram().getPrice().subtract(COMPUTER.getRam().getPrice());
        int qty = 2;
        CartItem cartItem = new CartItem(ITEM_ID, COMPUTER, customConfig, qty);

        BigDecimal price = cartItem.calculateUnitItemPrice();

        assertThat(price)
                .isEqualByComparingTo(COMPUTER.getPrice().add(priceDifference));
    }

    @Test
    void shouldDecreasePriceForCheaperComponent() {
        ComputerConfig customConfig = new ComputerConfig(Cpu.AMD_RYZEN_5, COMPUTER_CONFIG.gpu(), COMPUTER_CONFIG.ram());
        BigDecimal priceDifference = customConfig.cpu().getPrice().subtract(COMPUTER.getCpu().getPrice());
        int qty = 2;
        CartItem cartItem = new CartItem(ITEM_ID, COMPUTER, customConfig, qty);

        BigDecimal price = cartItem.calculateUnitItemPrice();

        assertThat(price)
                .isEqualByComparingTo(COMPUTER.getPrice().add(priceDifference));
    }
}
