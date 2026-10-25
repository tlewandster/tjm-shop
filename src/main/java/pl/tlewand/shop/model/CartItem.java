package pl.tlewand.shop.model;

import lombok.Getter;
import org.apache.commons.lang3.Validate;

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

    void increaseQtyBy(int qty) {
        this.qty += qty;
    }

    public BigDecimal calculateUnitItemPrice() {
        BigDecimal price = product.getPrice();
        if (product instanceof Computer computer) {
            BigDecimal cpuPriceDifference = itemConfig.cpu().getPrice().subtract(computer.getCpu().getPrice());
            BigDecimal gpuPriceDifference = itemConfig.gpu().getPrice().subtract(computer.getGpu().getPrice());
            BigDecimal ramPriceDifference = itemConfig.ram().getPrice().subtract(computer.getRam().getPrice());
            BigDecimal totalConfigPriceDifference = cpuPriceDifference.add(gpuPriceDifference).add(ramPriceDifference);
            price = product.getPrice().add(totalConfigPriceDifference);
        }
        return price;
    }

    @Override
    public String toString() {
        String description = "";
        if (product instanceof Computer computer) {
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
}
