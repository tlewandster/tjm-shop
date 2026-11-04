package pl.tlewand.shop.model.product;

import java.math.BigDecimal;

public record ComputerConfig(Cpu cpu, Gpu gpu, Ram ram) {

    public BigDecimal calculatePrice() {
        return cpu.getPrice().add(gpu.getPrice()).add(ram.getPrice());
    }

    @Override
    public String toString() {
        return "CPU: %S, GPU: %S, RAM: %S".formatted(
                cpu().toString().replace("_"," "),
                gpu().toString().replace("_"," "),
                ram().toString().replace("_"," ").trim());
    }
}
