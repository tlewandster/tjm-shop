package pl.tlewand.shop.service.discount;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DiscountServiceTest {

    @Mock
    DiscountPolicy codeDiscount;

    @Mock
    DiscountPolicy nightDiscount;

    DiscountService discountService;

    LocalTime orderTime = LocalTime.of(3, 0);
    BigDecimal totalOrder = new BigDecimal("123.45");
    Code discountCode = Code.HALLOWEEN26;

    @Test
    void shouldGetBestDiscountValue() {
        discountService = new DiscountService(List.of(codeDiscount,nightDiscount));
        when(codeDiscount.allowedDiscount(any(),any(),any())).thenReturn(new BigDecimal("50.00"));
        when(nightDiscount.allowedDiscount(any(),any(),any())).thenReturn(new BigDecimal("100.00"));

        BigDecimal result = discountService.getBestDiscountValue(orderTime, totalOrder, discountCode);

        assertThat(result)
                .isEqualTo("100.00");
    }
}