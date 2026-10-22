package pl.tlewand.shop.model;

import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.util.List;

public class Order {
    private final Client client;
    private final List<OrderItem> orderItems;
    private BigDecimal totalOrder;

    public Order(Client client, List<OrderItem> orderItems) {
        Validate.notNull(client, "client can not be null");
        Validate.notNull(orderItems, "orderItems can not be null");
        this.client = client;
        this.orderItems = orderItems;
    }
}
