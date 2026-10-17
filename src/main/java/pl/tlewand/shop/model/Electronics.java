package pl.tlewand.shop.model;

import java.math.BigDecimal;

public class Electronics extends Product{
    public Electronics(long id, String name, BigDecimal price, int stock) {
        super(id, name, price, stock);
    }
}
