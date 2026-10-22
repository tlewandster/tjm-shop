package pl.tlewand.shop.service;

import org.apache.commons.lang3.Validate;
import pl.tlewand.shop.model.Invoice;
import pl.tlewand.shop.model.Order;
import pl.tlewand.shop.model.OrderItem;

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
        if (!areProductsInStock(order)) {
            throw new IllegalStateException("Product out of stock");
        }
        reduceStocks(order);
        return generateInvoice(order);
    }

    private boolean areProductsInStock(Order order) {
        return getStreamIdQtyFrom(order).allMatch((entry) ->
                productManager.find(entry.getKey()).getStock() >= entry.getValue());
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
