package pl.tlewand.shop.model;

import java.math.BigDecimal;

public record OrderItem(
        long productId,
        String name,
        BigDecimal price,
        int qty,
        ComputerConfig config) {
    BigDecimal calculateItemPrice() {
        return price.multiply(BigDecimal.valueOf(qty));
    }
}
