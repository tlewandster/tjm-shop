package pl.tlewand.shop.model;

import org.apache.commons.lang3.Validate;

public record Invoice(long invoiceNumber, Order order) {
    public Invoice {
        Validate.isTrue(invoiceNumber > 0, "invoiceNumber must be >0");
        Validate.notNull(order, "order can not be null");
    }
}
