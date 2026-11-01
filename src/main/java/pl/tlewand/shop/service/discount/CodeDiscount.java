package pl.tlewand.shop.service.discount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;

public class CodeDiscount implements DiscountPolicy {
    @Override
    public BigDecimal allowedDiscount(LocalTime orderTime, BigDecimal totalOrder, Code discountCode) {

        return totalOrder
                .multiply(BigDecimal.valueOf(discountCode.getDiscountPercent()))
                .divide(new BigDecimal("100"))
                .setScale(2,RoundingMode.HALF_UP);
    }
}
