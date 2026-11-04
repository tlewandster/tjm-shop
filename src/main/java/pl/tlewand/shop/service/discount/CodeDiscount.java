package pl.tlewand.shop.service.discount;

import java.math.BigDecimal;
import java.time.LocalTime;

public class CodeDiscount implements DiscountPolicy {
    @Override
    public BigDecimal calculateDiscountValue(LocalTime orderTime, BigDecimal totalOrder, Code discountCode) {
        validate(orderTime, totalOrder, discountCode);
        int discountPercent = discountCode.getDiscountPercent();
        return calculatePercentageOf(totalOrder, discountPercent);
    }
}
