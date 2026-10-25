package pl.tlewand.shop.model.product;

import lombok.Getter;
import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;

@Getter
public abstract class Product {
    private final long id;
    private String name;
    private BigDecimal price;
    private int stock;

    protected Product(long id, String name, BigDecimal price, int stock) {
        Validate.isTrue(id > 0, "Id should be > 0");
        validateName(name);
        validatePrice(price);
        validateStock(stock);
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    private static void validateStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be less than zero");
        }
    }

    private static void validatePrice(BigDecimal price) {
        Validate.notNull(price, "Price cannot be null");
        if (price.signum() == -1) {
            throw new IllegalArgumentException("Price cannot be less than zero");
        }
    }

    private static void validateName(String name) {
        Validate.notBlank(name, "name cannot be null or blank");
    }

    public void setName(String name) {
        validateName(name);
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        validatePrice(price);
        this.price = price;
    }

    public void setStock(int stock) {
        validateStock(stock);
        this.stock = stock;
    }
}
