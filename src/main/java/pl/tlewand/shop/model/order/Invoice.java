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
        String invoiceItems = getInvoiceItems();
        String orderDate = getOrderDate();
        return getFormattedInvoiceText(orderDate, invoiceItems);
    }

    private String getInvoiceItems() {
        return order.getOrderItems().stream()
                .map(OrderItem::toString)
                .collect(Collectors.joining("\n"));
    }

    private String getOrderDate() {
        return order.getZonedOrderedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    private String getFormattedInvoiceText(String orderDate, String invoiceItems) {
        return new StringBuilder()
                .append("Invoice no. ").append(invoiceNumber)
                .append(" dated ").append(orderDate).append("\n")
                .append("Buyer: ").append(order.getClient()).append("\n")
                .append("Items: ").append(invoiceItems).append("\n")
                .append("Total order value: ").append(order.getTotalOrder()).append("\n")
                .append("Discount value: ").append(order.getDiscountAmount()).append("\n")
                .append("Total due: ").append(order.getTotalDue()).append("\n")
                .toString();
    }
}