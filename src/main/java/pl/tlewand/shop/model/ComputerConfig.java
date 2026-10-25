package pl.tlewand.shop.model;

public record ComputerConfig(Cpu cpu, Gpu gpu, Ram ram) {
    @Override
    public String toString() {
        return "CPU: %S, GPU: %S, RAM: %S".formatted(
                cpu().toString().replace("_"," "),
                gpu().toString().replace("_"," "),
                ram().toString().replace("_"," ").trim());
    }
}
