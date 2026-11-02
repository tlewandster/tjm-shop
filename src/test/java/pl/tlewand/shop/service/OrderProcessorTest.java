package pl.tlewand.shop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.tlewand.shop.exception.OutOfStockException;
import pl.tlewand.shop.model.order.Client;
import pl.tlewand.shop.model.order.Invoice;
import pl.tlewand.shop.model.order.Order;
import pl.tlewand.shop.model.order.OrderItem;
import pl.tlewand.shop.model.product.Electronics;
import pl.tlewand.shop.model.product.Product;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderProcessorTest {
    private static final Client CLIENT = new Client(1, "Jan Kowalski", "ul.Błotna 69, Podmokle");
    private static final List<OrderItem> ORDER_ITEMS = List.of(
            new OrderItem(1, "prod1", new BigDecimal("10.50"), 4, null)
    );
    private static final Order ORDER = new Order(CLIENT, ORDER_ITEMS);
    @Mock
    private ProductManager productManager;
    @InjectMocks
    private OrderProcessor orderProcessor;

    @Test
    void shouldProcessOrder() {
        Electronics product = new Electronics(1, "Product", new BigDecimal("20.00"), 50);
        when(productManager.find(1)).thenReturn(product);

        Invoice invoice = orderProcessor.process(ORDER);

        assertThat(invoice.invoiceNumber()).isEqualTo(1);
        verify(productManager).updateStock(1, 46);
    }

    @Test
    void shouldThrowWhenOutOfStock() {
        Electronics product = new Electronics(1, "Product", new BigDecimal("20.00"), 1);
        when(productManager.find(1)).thenReturn(product);

        assertThatThrownBy(() -> orderProcessor.process(ORDER))
                .isInstanceOf(OutOfStockException.class)
                .hasMessageContaining(product.getName());
        verify(productManager, never()).updateStock(anyLong(), anyInt());
    }

    @Test
    void shouldThrowWhenOutOfStockForTwoProductsWithSameId() {
        Electronics product = new Electronics(1, "Product", new BigDecimal("20.00"), 5);
        List<OrderItem> items = List.of(
                new OrderItem(1, "prod1", new BigDecimal("10.50"), 4, null),
                new OrderItem(1, "prod1", new BigDecimal("10.50"), 4, null)
        );
        Order order = new Order(CLIENT, items);
        when(productManager.find(1)).thenReturn(product);

        assertThatThrownBy(() -> orderProcessor.process(order))
                .isInstanceOf(OutOfStockException.class)
                .hasMessageContaining(product.getName());

        verify(productManager, never()).updateStock(anyLong(), anyInt());
    }

    @Test
    void shouldProcessSeriesOfOrdersSyncAndAsync() throws ExecutionException, InterruptedException {
        List<Order> orders = new ArrayList<>();
        Client client = new Client(1,"Jaś Kunefał", "Mars");
        Product product = new Electronics(13,"Electronics",BigDecimal.valueOf(100),10);
        OrderItem orderItem = new OrderItem(1, "prod1", new BigDecimal("10.50"), 4, null);
        IntStream.rangeClosed(1, 100)
                .forEach(i -> orders.add(new Order(client, List.of(orderItem))));
        when(productManager.find(anyLong())).thenReturn(product);

        long syncBefore = System.nanoTime();
        List<Invoice> syncInvoices = orderProcessor.processOrdersSync(orders);
        long syncAfter = System.nanoTime();
        Duration syncDuration = Duration.ofNanos(syncAfter - syncBefore);
        System.out.println("Sync -> " + syncDuration);

        long asyncBefore = System.nanoTime();
        List<Invoice> asyncInvoices = orderProcessor.processOrdersAsync(orders);
        long asyncAfter = System.nanoTime();
        Duration asyncDuration = Duration.ofNanos(asyncAfter - asyncBefore);
        System.out.println("Async -> " + asyncDuration);

        assertThat(syncInvoices.size()).isEqualTo(orders.size());
        assertThat(asyncInvoices.size()).isEqualTo(orders.size());
        assertThat(syncDuration).isGreaterThan(asyncDuration);
    }
}