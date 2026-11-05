package pl.tlewand.shop.model.product;

import lombok.Getter;
import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;

@Getter
public class Computer extends Product {
    private final ComputerConfig config;

    public Computer(long id, String name, BigDecimal price, int stock, ComputerConfig config) {
        Validate.notNull(config, "config cannot be null");
        super(id, name, price, stock);
        this.config = config;
    }

    @Override
    public String toString() {
        return "%d - Computer %s (%s, %s, %s) - %s PLN - %d in stock"
                .formatted(getId(), getName(), config.cpu(), config.gpu(), config.ram(), getPrice(), getStock());
    }
}
