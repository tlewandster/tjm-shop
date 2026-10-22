package pl.tlewand.shop.model;

import lombok.Getter;
import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;

@Getter
public class Smartphone extends Product{
    private final Colour colour;
    private final int batteryCapacityMah;

    public Smartphone(long id, String name, BigDecimal price, int stock, Colour colour, int batteryCapacityMah) {
        Validate.notNull(colour, "Colour cannot be null");
        Validate.isTrue(batteryCapacityMah > 0, "Battery capacity should be > 0");
        super(id, name, price, stock);
        this.colour = colour;
        this.batteryCapacityMah = batteryCapacityMah;
    }
}
