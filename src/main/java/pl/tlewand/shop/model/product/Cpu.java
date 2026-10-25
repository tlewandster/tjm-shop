package pl.tlewand.shop.model.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public enum Cpu {
    INTEL_I5(new BigDecimal("1450.00")),
    INTEL_I7(new BigDecimal("1950.00")),
    AMD_RYZEN_5(new BigDecimal("950.00")),
    AMD_RYZEN_7(new BigDecimal("1850.00")),
    AMD_RYZEN_9(new BigDecimal("2600.00"));

    private final BigDecimal price;
}