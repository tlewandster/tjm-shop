package pl.tlewand.shop.model.cart;

import lombok.Getter;
import org.apache.commons.lang3.Validate;
import pl.tlewand.shop.model.product.Computer;
import pl.tlewand.shop.model.product.ComputerConfig;
import pl.tlewand.shop.model.product.Product;
import pl.tlewand.shop.model.product.Smartphone;

import java.math.BigDecimal;

@Getter
public class CartItem {
    private final long itemId;
    private final Product product;
    private final ComputerConfig itemConfig;
    private int qty;

    CartItem(long itemId, Product product, ComputerConfig itemConfig, int qty) {
        Validate.isTrue(qty > 0, "Quantity must be > 0");
        this.itemId = itemId;
        this.product = product;
        this.itemConfig = itemConfig;
        this.qty = qty;
    }

    public BigDecimal calculateUnitItemPrice() {
        BigDecimal price = product.getPrice();
        if (product instanceof Computer computer) {
            BigDecimal configPriceDifference = itemConfig
                    .calculatePrice().subtract(computer.getConfig().calculatePrice());
            price = product.getPrice().add(configPriceDifference);
        }
        return price;
    }

    @Override
    public String toString() {
        String description = "";
        if (product instanceof Computer) {
            description = "(%s, %s, %s)".formatted(itemConfig.cpu(), itemConfig.gpu(), itemConfig.ram());
        } else if (product instanceof Smartphone smartphone) {
            description = "(%s, %smAh)".formatted(smartphone.getColour(), smartphone.getBatteryCapacityMah());
        }
        return "%d - %s%s - %d x %s - %s".formatted(
                itemId,
                product.getName(),
                description,
                qty,
                calculateUnitItemPrice(),
                calculateUnitItemPrice().multiply(new BigDecimal(qty))
        );
    }

    void increaseQtyBy(int qty) {
        this.qty += qty;
    }
}
