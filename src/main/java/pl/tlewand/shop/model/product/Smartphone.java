package pl.tlewand.shop.model.product;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Smartphone extends Product {
    private final Colour colour;
    private final int batteryCapacityMah;

    public Smartphone(long id, String name, BigDecimal price, int stock, SmartphoneSpec smartphoneSpec) {
        super(id, name, price, stock);
        this.colour = smartphoneSpec.colour();
        this.batteryCapacityMah = smartphoneSpec.batteryCapacityMah();
    }

    @Override
    public String toString() {
        return "%d - Smartphone %s (%s, %d mAh) - %s PLN - %d in stock"
                .formatted(getId(), getName(), getColour(), getBatteryCapacityMah(), getPrice(), getStock());
    }
}
