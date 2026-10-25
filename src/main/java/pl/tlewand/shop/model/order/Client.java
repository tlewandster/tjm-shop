package pl.tlewand.shop.model.order;

import org.apache.commons.lang3.Validate;

public record Client(int id, String name, String address) {
    public Client {
        Validate.isTrue(id > 0, "Id must be >0");
        Validate.notNull(name, "Name can not be null");
        Validate.notNull(address, "Address can not be null");
    }

    @Override
    public String toString() {
        return "Name: %s, Address: %s".formatted(name, address);
    }
}
