package pl.tlewand.shop.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public enum Gpu {
    NVIDIA_RTX_4060(new BigDecimal("1350.00")),
    NVIDIA_RTX_4070(new BigDecimal("2750.00")),
    NVIDIA_RTX_4080(new BigDecimal("4500.00")),
    AMD_RX_7700(new BigDecimal("1900.00")),
    AMD_RX_7900(new BigDecimal("4200.00"));

    private final BigDecimal price;
}