package pl.tlewand.shop.service.discount;

import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;

/// Discount strategy interface.
///
/// Strategy pattern. Used by [DiscountService], which selects the best discount from the provided list and does not require any changes when adding another strategy.
/// Additional strategies should be added to the [pl.tlewand.shop.service.discount] package as classes that implement this interface and passed to the [DiscountService] constructor.
@FunctionalInterface
public interface DiscountPolicy {
    BigDecimal calculateDiscountValue(LocalTime orderTime, BigDecimal totalOrder, Code discountCode);

    default void validate(LocalTime orderTime, BigDecimal totalOrder, Code discountCode) {
        Validate.notNull(orderTime, "orderTime can not be null");
        Validate.notNull(totalOrder, "totalOrder can not be null");
        Validate.notNull(discountCode, "discountCode can not be null");
        Validate.isTrue(totalOrder.signum() > -1,"totalOrder value cannot be less than 0");
    }

    default BigDecimal calculatePercentageOf(BigDecimal totalOrder, int discountPercent) {
        return totalOrder
                .multiply(BigDecimal.valueOf(discountPercent))
                .divide(new BigDecimal("100"))
                .setScale(2, RoundingMode.HALF_UP);
    }
}