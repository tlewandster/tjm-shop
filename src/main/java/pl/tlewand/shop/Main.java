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
import pl.tlewand.shop.service.ProductService;
import pl.tlewand.shop.service.StockService;
import pl.tlewand.shop.service.discount.Code;
import pl.tlewand.shop.service.discount.CodeDiscount;
import pl.tlewand.shop.service.discount.DiscountService;
import pl.tlewand.shop.service.discount.NightDiscount;
import pl.tlewand.shop.storage.FileStorage;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);

    static void main() {
        ProductRepository productRepository = new InMemoryProductRepository();
        ProductService productService = new ProductService(productRepository);
        OrderProcessor orderProcessor = new OrderProcessor(productService);
        StockService stockService = new StockService(new FileStorage());
        Path dataDir = Path.of("data");
        Path stockStorageFile = dataDir.resolve("stock.txt");

        productService.add(new Computer(1, "Lenovo Legion 5", new BigDecimal("5999.00"), 5, new ComputerConfig(Cpu.AMD_RYZEN_7, Gpu.NVIDIA_RTX_4060, Ram._16GB)));
        productService.add(new Computer(2, "ASUS ROG Strix G16", new BigDecimal("8499.00"), 3, new ComputerConfig(Cpu.INTEL_I7, Gpu.NVIDIA_RTX_4070, Ram._32GB)));
        productService.add(new Computer(3, "MSI Titan Pro", new BigDecimal("12999.00"), 2, new ComputerConfig(Cpu.AMD_RYZEN_9, Gpu.NVIDIA_RTX_4080, Ram._64GB)));

        productService.add(new Smartphone(4, "Samsung Galaxy S25", new BigDecimal("3999.00"), 10, Colour.BLACK, 4000));
        productService.add(new Smartphone(5, "iPhone 17", new BigDecimal("4499.00"), 8, Colour.WHITE, 3600));
        productService.add(new Smartphone(6, "Xiaomi 15", new BigDecimal("2799.00"), 12, Colour.BLUE, 5400));

        productService.add(new Electronics(7, "Etui", new BigDecimal("59.99"), 50));
        productService.add(new Electronics(8, "Ładowarka", new BigDecimal("149.00"), 30));
        productService.add(new Electronics(9, "Mysz bezprzewodowa", new BigDecimal("179.00"), 20));

        Client client = (new Client(1, "Jan Kunefał", "ul. Krótka 5, 00-001 Warszawa"));
        Cart cart = new Cart();
        DiscountService discountService = new DiscountService(List.of(new CodeDiscount(), new NightDiscount()));
        try {
            stockService.load(stockStorageFile, productService);
        } catch (IOException e) {
            System.out.println("Problem reading file " + stockStorageFile + ". " + e.getMessage());
        }

        run(productService, orderProcessor, cart, client, discountService);
        try {
            stockService.save(stockStorageFile, productService);
        } catch (IOException e) {
            System.out.println("Problem writing to file " + stockStorageFile + ". " + e.getMessage());
        }
    }

    private static void run(ProductService productService, OrderProcessor orderProcessor, Cart cart, Client client, DiscountService discountService) {
        int choice = 0;
        do {
            System.out.println("""
                    1. Product List
                    2. Add to Cart
                    3. Remove from cart
                    4. View Cart
                    5. Change your computer's config
                    6. Place order
                    0. Quit
                    """);

            System.out.println("Your choice: ");
            try {
                choice = SCANNER.nextInt();
                SCANNER.nextLine();
                switch (choice) {
                    case 1 -> productList(productService);
                    case 2 -> addToCart(cart, productService);
                    case 3 -> removeFromCart(cart);
                    case 4 -> viewCart(cart);
                    case 5 -> changeConfig(cart);
                    case 6 -> placeOrder(cart, client, orderProcessor, discountService);
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

    private static void removeFromCart(Cart cart) {
        viewCart(cart);
        System.out.println("Which item remove from cart?");
        long itemId = SCANNER.nextLong();
        cart.removeItem(itemId);
        System.out.println("Removed");
    }

    private static void placeOrder(Cart cart, Client client, OrderProcessor orderProcessor, DiscountService discountService) {
        Code userCode;
        System.out.println("Enter discount code: ");
        try {
            userCode = Code.validate(SCANNER.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid code");
            userCode = Code.NO_CODE;
        }
        Order order = cart.checkout(client);
        order.applyDiscount(discountService.getBestDiscountValue(order.getZonedOrderedAt().toLocalTime(), order.getTotalOrder(), userCode));
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
        }
        System.out.println("Select the computer on which you want to change configuration:");
        computers.forEach(System.out::println);
        long id = SCANNER.nextLong();
        computers.stream()
                .filter(cartItem -> cartItem.getItemId() == id)
                .findFirst()
                .ifPresentOrElse(cartItem -> applyNewConfigTo(cart, cartItem), () -> System.out.printf("No computer with id %d in your cart\n\n", id));
    }

    private static void applyNewConfigTo(Cart cart, CartItem selectedItem) {
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
        int cpuChoice = SCANNER.nextInt();
        return switch (cpuChoice) {
            case 1 -> Cpu.INTEL_I5;
            case 2 -> Cpu.INTEL_I7;
            case 3 -> Cpu.AMD_RYZEN_5;
            case 4 -> Cpu.AMD_RYZEN_7;
            case 5 -> Cpu.AMD_RYZEN_9;
            default -> throw new IllegalStateException("Unexpected value: " + cpuChoice);
        };
    }

    private static Gpu getNewGpu() {
        System.out.println("Select GPU:");
        Arrays.stream(Gpu.values()).forEach(gpu -> System.out.println(gpu.ordinal() + 1 + ". " + gpu.name()));
        int gpuChoice = SCANNER.nextInt();
        return switch (gpuChoice) {
            case 1 -> Gpu.NVIDIA_RTX_4060;
            case 2 -> Gpu.NVIDIA_RTX_4070;
            case 3 -> Gpu.NVIDIA_RTX_4080;
            case 4 -> Gpu.AMD_RX_7700;
            case 5 -> Gpu.AMD_RX_7900;
            default -> throw new IllegalStateException("Unexpected value: " + gpuChoice);
        };
    }

    private static Ram getNewRam() {
        System.out.println("Select RAM:");
        Arrays.stream(Ram.values()).forEach(ram -> System.out.println(ram.ordinal() + 1 + ". " + ram.name()));
        int ramChoice = SCANNER.nextInt();
        return switch (ramChoice) {
            case 1 -> Ram._16GB;
            case 2 -> Ram._32GB;
            case 3 -> Ram._64GB;
            default -> throw new IllegalStateException("Unexpected value: " + ramChoice);
        };
    }

    private static void viewCart(Cart cart) {
        cart.getItems()
                .forEach(System.out::println);
        System.out.println("Total cart value: " + cart.calculateTotalPrice());
    }

    private static void addToCart(Cart cart, ProductService productService) {
        System.out.println("Which product add to cart?");
        long productId = SCANNER.nextLong();
        System.out.println("How many?");
        int qty = SCANNER.nextInt();
        Product product = productService.find(productId);
        ComputerConfig config = product instanceof Computer computer ? new ComputerConfig(computer.getConfig().cpu(), computer.getConfig().gpu(), computer.getConfig().ram()) : null;
        cart.addItem(product, config, qty);
        System.out.printf("Added to cart %d pcs.:\n%s\n", qty, product.toString());
    }

    private static void productList(ProductService productService) {
        productService.findAll()
                .forEach(System.out::println);
        System.out.println();
    }
}
