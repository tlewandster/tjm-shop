package pl.tlewand.shop.model.order;

import org.apache.commons.lang3.Validate;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public record Invoice(long invoiceNumber, Order order) {
    public Invoice {
        Validate.isTrue(invoiceNumber > 0, "invoiceNumber must be >0");
        Validate.notNull(order, "order can not be null");
    }

    @Override
    public String toString() {
        String cartItems = order.getOrderItems().stream()
                .map(OrderItem::toString)
                .collect(Collectors.joining("\n"));

        String orderDate = order.getOrderedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

        return """
                Invoice no. %d dated %s
                Buyer: %s
                Items:
                %s
                Total order value: %s
                """.formatted(invoiceNumber,orderDate, order.getClient(),cartItems,order.getTotalOrder());
    }
}
