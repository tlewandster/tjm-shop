package pl.tlewand.shop.model.order;

import lombok.Getter;
import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class Order {
    private final Client client;
    private final List<OrderItem> orderItems;
    private final BigDecimal totalOrder;

    public Order(Client client, List<OrderItem> orderItems) {
        Validate.notNull(client, "client can not be null");
        Validate.notNull(orderItems, "orderItems can not be null");
        Validate.notEmpty(orderItems,"orderItems can not be empty");
        this.client = client;
        this.orderItems = List.copyOf(orderItems);
        this.totalOrder = calculateTotalOrderPrice(this.orderItems);
    }

    private BigDecimal calculateTotalOrderPrice(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItem::calculateItemPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }
}
