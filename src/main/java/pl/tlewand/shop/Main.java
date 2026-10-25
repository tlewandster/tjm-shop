package pl.tlewand.shop;

import pl.tlewand.shop.model.*;
import pl.tlewand.shop.repository.InMemoryProductRepository;
import pl.tlewand.shop.repository.ProductRepository;
import pl.tlewand.shop.service.OrderProcessor;
import pl.tlewand.shop.service.ProductManager;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    static void main(String[] args) {
        ProductRepository productRepository = new InMemoryProductRepository();
        ProductManager productManager = new ProductManager(productRepository);
        OrderProcessor orderProcessor = new OrderProcessor(productManager);

        productManager.add(new Computer(1, "Lenovo Legion 5", new BigDecimal("5999.00"), 5, Cpu.AMD_RYZEN_7, Gpu.NVIDIA_RTX_4060, Ram._16GB));
        productManager.add(new Computer(2, "ASUS ROG Strix G16", new BigDecimal("8499.00"), 3, Cpu.INTEL_I7, Gpu.NVIDIA_RTX_4070, Ram._32GB));
        productManager.add(new Computer(3, "MSI Titan Pro", new BigDecimal("12999.00"), 2, Cpu.AMD_RYZEN_9, Gpu.NVIDIA_RTX_4080, Ram._64GB));

        productManager.add(new Smartphone(4, "Samsung Galaxy S25", new BigDecimal("3999.00"), 10, Colour.BLACK, 4000));
        productManager.add(new Smartphone(5, "iPhone 17", new BigDecimal("4499.00"), 8, Colour.WHITE, 3600));
        productManager.add(new Smartphone(6, "Xiaomi 15", new BigDecimal("2799.00"), 12, Colour.BLUE, 5400));

        productManager.add(new Electronics(7, "Etui", new BigDecimal("59.99"), 50));
        productManager.add(new Electronics(8, "Ładowarka", new BigDecimal("149.00"), 30));
        productManager.add(new Electronics(9, "Mysz bezprzewodowa", new BigDecimal("179.00"), 20));

        Client client = (new Client(1, "Jan Kunefał", "ul. Krótka 5, 00-001 Warszawa"));
        Cart cart = new Cart();

        Scanner scanner = new Scanner(System.in);

        run(scanner, productManager, orderProcessor);
    }

    private static void run(Scanner sc, ProductManager productManager, OrderProcessor orderProcessor) {
        int choice = 0;
        do {
            System.out.println("""
                    1. Product List
                    2. Add to Cart
                    3. View Cart
                    4. Change your computer's config
                    5. Place order
                    0. Quit
                    """);

            System.out.println("Your choice: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 -> productList();
                case 2 -> addToCart();
                case 3 -> viewCart();
                case 4 -> changeConfig();
                case 5 -> placeOrder();
                case 0 -> System.out.println("Thank you for shopping with us. Bye.");
                default -> System.out.println("Incorrect option\n");
            }
        } while (choice != 0);
    }

    private static void placeOrder() {

    }

    private static void changeConfig() {

    }

    private static void viewCart() {

    }

    private static void addToCart() {

    }

    private static void productList() {

    }
}
