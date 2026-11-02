package pl.tlewand.shop.service.discount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;

public class CodeDiscount implements DiscountPolicy {
    @Override
    public BigDecimal allowedDiscount(LocalTime orderTime, BigDecimal totalOrder, Code discountCode) {
        validate(orderTime, totalOrder, discountCode);
        int discountPercent = discountCode.getDiscountPercent();
        return calculateDiscount(totalOrder, discountPercent);
    }
}
