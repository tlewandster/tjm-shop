package pl.tlewand.shop.service.discount;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CodeDiscountTest {
    LocalTime orderTime = LocalTime.of(17, 0);
    BigDecimal totalOrder = new BigDecimal("123.45");
    Code discountCode = Code.DISC15;
    CodeDiscount discount = new CodeDiscount();

    @Test
    void shouldCalculateCodeDiscount() {
        BigDecimal calculated = discount.allowedDiscount(orderTime,totalOrder,discountCode);

        assertThat(calculated)
                .isEqualTo(new BigDecimal("18.52"));
    }

    @Test
    void shouldCalculateCodeDiscountEqualsZero() {
        BigDecimal calculated = discount.allowedDiscount(orderTime,totalOrder,Code.NO_CODE);

        assertThat(calculated)
                .isEqualTo(new BigDecimal("0.00"));
    }

    @Test
    void shouldThrowNullPointerExceptionWhileOrderTimeIsNull() {
        assertThatThrownBy(() -> discount.allowedDiscount(null, totalOrder, discountCode))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("orderTime can not be null");
    }

    @Test
    void shouldThrowNullPointerExceptionWhileTotalOrderIsNull() {
        assertThatThrownBy(() -> discount.allowedDiscount(orderTime, null, discountCode))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("totalOrder can not be null");
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhileTotalOrderLessThanZero() {
        assertThatThrownBy(() -> discount.allowedDiscount(orderTime, new BigDecimal("-100.00"), discountCode))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("totalOrder value cannot be less than 0");
    }
}