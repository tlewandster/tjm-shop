package pl.tlewand.shop.model.order;

import org.apache.commons.lang3.Validate;

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
        return """
                Invoice no. %d
                Buyer: %s
                Items:
                %s
                Total order value: %s
                """.formatted(invoiceNumber,order.getClient(),cartItems,order.getTotalOrder());
    }
}
