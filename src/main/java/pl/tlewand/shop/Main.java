package pl.tlewand.shop;

import pl.tlewand.shop.model.*;
import pl.tlewand.shop.repository.InMemoryProductRepository;
import pl.tlewand.shop.repository.ProductRepository;
import pl.tlewand.shop.service.OrderProcessor;
import pl.tlewand.shop.service.ProductManager;

import java.math.BigDecimal;

public class Main {
    static void main(String[] args) {
        ProductRepository productRepository = new InMemoryProductRepository();
        ProductManager productManager = new ProductManager(productRepository);
        OrderProcessor orderProcessor = new OrderProcessor(productManager);

        new Computer(1, "Lenovo Legion 5", new BigDecimal("5999.00"), 5, Cpu.AMD_RYZEN_7, Gpu.NVIDIA_RTX_4060, Ram._16GB);
        new Computer(2, "ASUS ROG Strix G16", new BigDecimal("8499.00"), 3, Cpu.INTEL_I7, Gpu.NVIDIA_RTX_4070, Ram._32GB);
        new Computer(3, "MSI Titan Pro", new BigDecimal("12999.00"), 2, Cpu.AMD_RYZEN_9, Gpu.NVIDIA_RTX_4080, Ram._64GB);

        new Smartphone(4, "Samsung Galaxy S25", new BigDecimal("3999.00"), 10, Colour.BLACK, 4000);
        new Smartphone(5, "iPhone 17", new BigDecimal("4499.00"), 8, Colour.WHITE, 3600);
        new Smartphone(6, "Xiaomi 15", new BigDecimal("2799.00"), 12, Colour.BLUE, 5400);

        new Electronics(7, "Etui", new BigDecimal("59.99"), 50);
        new Electronics(8, "Ładowarka", new BigDecimal("149.00"), 30);
        new Electronics(9, "Mysz bezprzewodowa", new BigDecimal("179.00"), 20);

        new Client(1, "Jan Kunefał", "ul. Krótka 5, 00-001 Warszawa");
        new Client(2, "Anna Krzak", "ul. Długa 12, 10-123 Gdańsk");
        new Client(3, "Piotr Nobody", "ul. Jakaś 7, 20-576 Kraków");
    }
}
