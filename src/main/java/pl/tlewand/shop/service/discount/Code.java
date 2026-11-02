package pl.tlewand.shop.service.discount;

import lombok.Getter;

@Getter
public enum Code {
    NO_CODE(0),
    DISC10(10),
    DISC15(15),
    DISC20(20),
    HALLOWEEN26(26);

    private final int discountPercent;

    Code(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public static Code validate(String userCode) {
        if (userCode.isBlank()) return Code.NO_CODE;
        return valueOf(userCode.toUpperCase().trim());
    }
}
