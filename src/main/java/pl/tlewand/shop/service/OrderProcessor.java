package pl.tlewand.shop.service;

import org.apache.commons.lang3.Validate;
import pl.tlewand.shop.exception.OutOfStockException;
import pl.tlewand.shop.model.order.Invoice;
import pl.tlewand.shop.model.order.Order;
import pl.tlewand.shop.model.order.OrderItem;
import pl.tlewand.shop.model.product.Product;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderProcessor {
    private final ProductManager productManager;
    private long invoiceNumber = 0;

    public OrderProcessor(ProductManager productManager) {
        Validate.notNull(productManager, "productManager can not be null");
        this.productManager = productManager;
    }

    public Invoice process(Order order) {
        validateStock(order);
        simulateLongOrderProcessing(200);
        reduceStocks(order);
        return generateInvoice(order);
    }

    private void simulateLongOrderProcessing(long timeInMillis) {
        try {
            Thread.sleep(timeInMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void validateStock(Order order) {
        getStreamIdQtyFrom(order).forEach(entry -> {
            Product product = productManager.find(entry.getKey());
            int stock = product.getStock();
            Integer requestedQty = entry.getValue();
            if (requestedQty > stock) {
                throw new OutOfStockException("Requested quantity (%d pcs. %s) exceeds the current stock (%d)".formatted(requestedQty, product.getName(), stock));
            }
        });
    }

    private void reduceStocks(Order order) {
        getStreamIdQtyFrom(order).forEach(entry -> {
            Long productId = entry.getKey();
            Integer qty = entry.getValue();
            int newStock = productManager.find(productId).getStock() - qty;
            productManager.updateStock(productId, newStock);
        });
    }

    private Stream<Map.Entry<Long, Integer>> getStreamIdQtyFrom(Order order) {
        return order.getOrderItems().stream()
                .collect(Collectors.groupingBy(OrderItem::productId, Collectors.summingInt(OrderItem::qty)))
                .entrySet().stream();
    }

    private Invoice generateInvoice(Order order) {
        return new Invoice(++invoiceNumber, order);
    }
}
