package pl.tlewand.shop.model.product;

import lombok.Getter;
import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;

@Getter
public class Computer extends Product {
    @Getter
    private final Cpu cpu;
    private final Gpu gpu;
    private final Ram ram;

    public Computer(long id, String name, BigDecimal price, int stock, Cpu cpu, Gpu gpu, Ram ram) {
        Validate.notNull(cpu, "CPU cannot be null");
        Validate.notNull(gpu, "GPU cannot be null");
        Validate.notNull(ram, "RAM cannot be null");
        super(id, name, price, stock);
        this.cpu = cpu;
        this.gpu = gpu;
        this.ram = ram;
    }

    @Override
    public String toString() {
        return "%d - Computer %s (%s, %s, %s) - %s PLN - %d in stock".formatted(getId(), getName(), getCpu(), getGpu(), getRam(), getPrice(), getStock());
    }
}
