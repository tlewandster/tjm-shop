package pl.tlewand.shop;

import pl.tlewand.shop.exception.OutOfStockException;
import pl.tlewand.shop.model.cart.Cart;
import pl.tlewand.shop.model.cart.CartItem;
import pl.tlewand.shop.model.order.Client;
import pl.tlewand.shop.model.order.Invoice;
import pl.tlewand.shop.model.order.Order;
import pl.tlewand.shop.model.product.*;
import pl.tlewand.shop.repository.InMemoryProductRepository;
import pl.tlewand.shop.repository.ProductRepository;
import pl.tlewand.shop.service.OrderProcessor;
import pl.tlewand.shop.service.ProductManager;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

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

        run(productManager, orderProcessor, cart, client);
    }

    private static void run(ProductManager productManager, OrderProcessor orderProcessor, Cart cart, Client client) {
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
            try {
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1 -> productList(productManager);
                    case 2 -> addToCart(cart, productManager);
                    case 3 -> viewCart(cart);
                    case 4 -> changeConfig(cart);
                    case 5 -> placeOrder(cart, client, orderProcessor);
                    case 0 -> System.out.println("Thank you for shopping with us. Bye.");
                    default -> System.out.println("Incorrect option\n");
                }
            } catch (OutOfStockException e) {
                System.out.println("Out of stock: " + e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (choice != 0);
    }

    private static void placeOrder(Cart cart, Client client, OrderProcessor orderProcessor) {
        Order order = cart.checkout(client);
        Invoice invoice = orderProcessor.process(order);
        System.out.println(invoice);
    }

    private static void changeConfig(Cart cart) {
        List<CartItem> computers = cart.getItems().stream()
                .filter(cartItem -> cartItem.getItemConfig() != null)
                .toList();

        if (computers.isEmpty()) {
            System.out.println("You don't have any computers in your cart");
            return;
        } else {
            System.out.println("Select the computer on which you want to change configuration:");
            computers.forEach(cartItem -> System.out.println(cartItem.toString()));
        }
        long id = sc.nextLong();
        // Select a new configuration; the current one is...
        CartItem selectedItem = computers.stream()
                .filter(cartItem -> cartItem.getItemId() == id)
                .findFirst()
                .get();
        // Select a new configuration; the current one is...
        System.out.println("Select a new configuration. Current one is " + selectedItem.getItemConfig());
        ComputerConfig newConfig = selectNewConfig();
        cart.removeItem(selectedItem.getItemId());
        cart.addItem(selectedItem.getProduct(), newConfig, selectedItem.getQty());
    }

    private static ComputerConfig selectNewConfig() {
        Cpu newCpu = getNewCpu();
        Gpu newGpu = getNewGpu();
        Ram newRam = getNewRam();
        return new ComputerConfig(newCpu, newGpu, newRam);
    }

    private static Cpu getNewCpu() {
        System.out.println("Select CPU:");
        Arrays.stream(Cpu.values()).forEach(cpu -> System.out.println(cpu.ordinal() + 1 + ". " + cpu.name()));
        int cpuChoice = sc.nextInt();
        Cpu newCpu = switch (cpuChoice) {
            case 1 -> Cpu.INTEL_I5;
            case 2 -> Cpu.INTEL_I7;
            case 3 -> Cpu.AMD_RYZEN_5;
            case 4 -> Cpu.AMD_RYZEN_7;
            case 5 -> Cpu.AMD_RYZEN_9;
            default -> throw new IllegalStateException("Unexpected value: " + cpuChoice);
        };
        return newCpu;
    }

    private static Gpu getNewGpu() {
        System.out.println("Select GPU:");
        Arrays.stream(Gpu.values()).forEach(gpu -> System.out.println(gpu.ordinal() + 1 + ". " + gpu.name()));
        int gpuChoice = sc.nextInt();
        Gpu newGpu = switch (gpuChoice) {
            case 1 -> Gpu.NVIDIA_RTX_4060;
            case 2 -> Gpu.NVIDIA_RTX_4070;
            case 3 -> Gpu.NVIDIA_RTX_4080;
            case 4 -> Gpu.AMD_RX_7700;
            case 5 -> Gpu.AMD_RX_7900;
            default -> throw new IllegalStateException("Unexpected value: " + gpuChoice);
        };
        return newGpu;
    }

    private static Ram getNewRam() {
        System.out.println("Select RAM:");
        Arrays.stream(Ram.values()).forEach(ram -> System.out.println(ram.ordinal() + 1 + ". " + ram.name()));
        int ramChoice = sc.nextInt();
        Ram newRam = switch (ramChoice) {
            case 1 -> Ram._16GB;
            case 2 -> Ram._32GB;
            case 3 -> Ram._64GB;
            default -> throw new IllegalStateException("Unexpected value: " + ramChoice);
        };
        return newRam;
    }

    private static void viewCart(Cart cart) {
        cart.getItems()
                .forEach(cartItem -> System.out.println(cartItem.toString()));
        System.out.println("Total cart value: " + cart.totalPrice());
    }

    private static void addToCart(Cart cart, ProductManager productManager) {
        System.out.println("Which product add to  cart?");
        long productId = sc.nextLong();
        System.out.println("How many?");
        int qty = sc.nextInt();
        Product product = productManager.find(productId);
        ComputerConfig config = product instanceof Computer computer ? new ComputerConfig(computer.getCpu(), computer.getGpu(), computer.getRam()) : null;
        cart.addItem(product, config, qty);
        System.out.printf("Added to cart %d pcs.:\n%s\n", qty, product.toString());
    }

    private static void productList(ProductManager productManager) {
        productManager.findAll()
                .forEach(product -> System.out.println(product.toString()));
        System.out.println();
    }
}
