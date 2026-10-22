package pl.tlewand.shop.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {
    private static final List<OrderItem> ORDER_ITEMS = List.of(
            new OrderItem(1, "prod1", new BigDecimal("10.50"), 4, null),
            new OrderItem(2, "prod2", new BigDecimal("20.00"), 5, null)
    );
    private static final Client CLIENT = new Client(1, "Jan Kowalski", "ul.Błotna 69, Podmokle");

    @Test
    void shouldCalculateTotalOrder() {
        BigDecimal expectedTotal = new BigDecimal("142.00");
        Order order = new Order(CLIENT, ORDER_ITEMS);

        assertThat(order.getTotalOrder())
                .isEqualByComparingTo(expectedTotal);
    }

    @Test
    void shouldThrowWhenOrderItemsIsEmpty() {
        List<OrderItem> orderItems = List.of();

        assertThatThrownBy(() -> new Order(CLIENT, orderItems))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("orderItems can not be empty");
    }

    @Test
    void shouldNotChangeWhenSourceListModified() {
        List<OrderItem> orderItems = new ArrayList<>(ORDER_ITEMS);

        Order order = new Order(CLIENT, orderItems);
        orderItems.add(new OrderItem(3, "prod3", new BigDecimal("20.00"), 5, null));

        assertThat(order.getOrderItems())
                .containsExactly(new OrderItem(1, "prod1", new BigDecimal("10.50"), 4, null),
                        new OrderItem(2, "prod2", new BigDecimal("20.00"), 5, null));
    }
}