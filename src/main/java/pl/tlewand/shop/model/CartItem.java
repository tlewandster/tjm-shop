package pl.tlewand.shop.model;

import lombok.Getter;
import org.apache.commons.lang3.Validate;

public class CartItem {
    private final long itemId;
    @Getter
    private final Product product;
    @Getter
    private final ComputerConfig computerConfig;
    @Getter
    private int qty;

    public CartItem(long itemId, Product product, ComputerConfig computerConfig, int qty) {
        Validate.isTrue(qty > 0, "Quantity must be > 0");
        this.itemId = itemId;
        this.product = product;
        this.computerConfig = computerConfig;
        this.qty = qty;
    }

    void increaseQtyBy(int qty) {
        this.qty += qty;
    }

//    calculateItemPrice(){}
}
