package pl.tlewand.shop.service.discount;

import java.math.BigDecimal;
import java.time.LocalTime;

public interface DiscountPolicy {
    BigDecimal allowedDiscount (LocalTime orderTime, BigDecimal totalOrder, Code discountCode );
}
