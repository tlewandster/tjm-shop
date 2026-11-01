package pl.tlewand.shop.service.discount;

import lombok.Getter;

@Getter
public enum Code {
    NO_CODE(0),
    DISC10(10),
    DISC20(20),
    HALLOWEEN26(26);

    private final int discountPercent;

    Code(int discountPercent) {
        this.discountPercent = discountPercent;
    }

}
