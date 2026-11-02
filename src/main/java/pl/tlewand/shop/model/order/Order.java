package pl.tlewand.shop.model.order;

import lombok.Getter;
import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class Order {
    public static final ZoneId SHOP_ZONE = ZoneId.of("Europe/Warsaw");
    private final Client client;
    private final List<OrderItem> orderItems;
    private final BigDecimal totalOrder;
    private final Instant orderedAt;
    private BigDecimal discountAmount = BigDecimal.ZERO;

    public Order(Client client, List<OrderItem> orderItems) {
        Validate.notNull(client, "client can not be null");
        Validate.notNull(orderItems, "orderItems can not be null");
        Validate.notEmpty(orderItems, "orderItems can not be empty");
        this.client = client;
        this.orderItems = List.copyOf(orderItems);
        this.totalOrder = calculateTotalOrderPrice(this.orderItems);
        this.orderedAt = Instant.now();
    }

    public void applyDiscount(BigDecimal amount) {
        discountAmount = amount;
    }

    public BigDecimal getTotalDue() {
        return totalOrder.subtract(discountAmount);
    }

    private BigDecimal calculateTotalOrderPrice(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItem::calculateItemPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public ZonedDateTime getZonedOrderedAt() {
        return orderedAt.atZone(SHOP_ZONE);
    }
}
