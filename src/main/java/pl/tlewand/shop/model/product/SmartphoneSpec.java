package pl.tlewand.shop.model.product;

import org.apache.commons.lang3.Validate;

public record SmartphoneSpec(Colour colour, int batteryCapacityMah) {
    public SmartphoneSpec {
        Validate.notNull(colour, "colour cannot be null");
        Validate.isTrue(batteryCapacityMah > 0, "batteryCapacityMah must be > 0");
    }
}
