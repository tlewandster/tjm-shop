package pl.tlewand.shop.model.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public enum Ram {
    _16GB(new BigDecimal("180.00")),
    _32GB(new BigDecimal("480.00")),
    _64GB(new BigDecimal("850.00"));

    private final BigDecimal price;
}