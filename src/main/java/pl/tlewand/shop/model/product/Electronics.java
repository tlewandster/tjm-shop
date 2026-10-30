package pl.tlewand.shop.model.product;

import java.math.BigDecimal;

public class Electronics extends Product{
    public Electronics(long id, String name, BigDecimal price, int stock) {
        super(id, name, price, stock);
    }

    @Override
    public String toString() {
        return "%d - %s - %s PLN - %d in stock".formatted(getId(), getName(), getPrice(), getStock());
    }
}
