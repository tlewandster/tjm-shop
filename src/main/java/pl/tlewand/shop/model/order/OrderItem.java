package pl.tlewand.shop.model.order;

import pl.tlewand.shop.model.product.ComputerConfig;

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

    @Override
    public String toString() {
        return "%s (%s) - %d x %s -> %s".formatted(
                name(),
                config(),
                qty(),
                price(),
                price().multiply(BigDecimal.valueOf(qty()))
        );
    }
}
