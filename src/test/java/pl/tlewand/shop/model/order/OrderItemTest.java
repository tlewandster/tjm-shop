package pl.tlewand.shop.model.order;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemTest {

    @Test
    void shouldCalculateItemPrice() {
        BigDecimal price = new BigDecimal("10.00");
        int qty = 5;
        OrderItem itemOrder = new OrderItem(1, "name", price, qty, null);

        BigDecimal result = itemOrder.calculateItemPrice();

        assertThat(result)
                .isEqualByComparingTo(new BigDecimal("50.00"));
    }
}