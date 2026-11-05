package pl.tlewand.shop.service.discount;

import java.math.BigDecimal;
import java.time.LocalTime;

public class NightDiscount implements DiscountPolicy {

    private final static int DISCOUNT_PERCENT = 15;
    private final LocalTime startTime = LocalTime.of(0, 0);
    private final LocalTime endTime = LocalTime.of(6, 0);

    @Override
    public BigDecimal calculateDiscountValue(LocalTime orderTime, BigDecimal totalOrder, Code discountCode) {
        validate(orderTime, totalOrder, discountCode);
        if (orderTime.isAfter(startTime) && orderTime.isBefore(endTime)) {
            return calculatePercentageOf(totalOrder, DISCOUNT_PERCENT);
        }
        return BigDecimal.ZERO.setScale(2);
    }
}
