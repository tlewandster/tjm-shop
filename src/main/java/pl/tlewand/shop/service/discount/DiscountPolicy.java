package pl.tlewand.shop.service.discount;

import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;

public interface DiscountPolicy {
    BigDecimal allowedDiscount(LocalTime orderTime, BigDecimal totalOrder, Code discountCode);

    default void validate(LocalTime orderTime, BigDecimal totalOrder, Code discountCode) {
        Validate.notNull(orderTime, "orderTime can not be null");
        Validate.notNull(totalOrder, "totalOrder can not be null");
        Validate.notNull(discountCode, "discountCode can not be null");
        Validate.isTrue(totalOrder.signum() > -1,"totalOrder value cannot be less than 0");
    }

    default BigDecimal calculateDiscount(BigDecimal totalOrder, int discountPercent) {
        return totalOrder
                .multiply(BigDecimal.valueOf(discountPercent))
                .divide(new BigDecimal("100"))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
