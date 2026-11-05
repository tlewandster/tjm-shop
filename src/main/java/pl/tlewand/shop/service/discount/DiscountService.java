package pl.tlewand.shop.service.discount;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

public class DiscountService {
    private final List<DiscountPolicy> discountPolicies;

    public DiscountService(List<DiscountPolicy> discountPolicies) {
        this.discountPolicies = List.copyOf(discountPolicies);
    }

    public BigDecimal getBestDiscountValue(LocalTime orderTime, BigDecimal totalOrder, Code discountCode){
        return discountPolicies.stream()
                .map(discountPolicy -> discountPolicy.calculateDiscountValue(orderTime,totalOrder,discountCode))
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }
}
