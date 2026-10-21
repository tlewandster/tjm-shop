package pl.tlewand.shop.model;

import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.util.*;

public class Cart {
    private final Map<Integer, CartItem> items = new LinkedHashMap<>();
    private int itemIdCounter = 0;

    public void addItem(Product product, ComputerConfig config, int qty) {
        Validate.notNull(product, "Product cannot be null");
        Validate.isTrue(qty > 0, "Quantity must be > 0");
        if (product instanceof Computer && config == null) {
            throw new IllegalArgumentException("Computer must have configuration");
        }
        if (!(product instanceof Computer) && config != null) {
            throw new IllegalArgumentException("Only computer can have configuration");
        }

        int summaryQtySameItemsInCart = items.values().stream()
                .filter(cartItem -> cartItem.getProduct().getId() == product.getId())
                .mapToInt(CartItem::getQty).sum();
        if ((summaryQtySameItemsInCart + qty) > product.getStock()) {
            throw new IllegalStateException("Qty exceeds stock");
        }

        Optional<CartItem> sameItemInCart = items.values().stream()
                .filter((cartItem) ->
                        cartItem.getProduct().getId() == product.getId() &&
                                Objects.equals(cartItem.getItemConfig(), config))
                .findFirst();
        if (sameItemInCart.isPresent()) {
            sameItemInCart.get().increaseQtyBy(qty);
            return;
        }
        itemIdCounter++;
        CartItem cartItem = new CartItem(itemIdCounter, product, config, qty);
        items.put(itemIdCounter, cartItem);
    }

    public void removeItem(int itemId) {
        if (items.remove(itemId) == null) {
            throw new NoSuchElementException("Item " + itemId + " is not in cart");
        }
    }

    public void clear() {
        items.clear();
    }

    List<CartItem> getItems() {
        return List.copyOf(items.values());
    }

    public BigDecimal totalPrice() {
        return items.values().stream()
                .map(cartItem ->
                        cartItem.calculateUnitItemPrice()
                                .multiply(BigDecimal.valueOf(cartItem.getQty())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public List<OrderItem> checkout() {
        Validate.validState(!items.isEmpty(),"Cart cannot be empty at checkout");
        List<OrderItem> orderItems = items.values().stream()
                .map(cartItem -> new OrderItem(
                        cartItem.getProduct().getId(),
                        cartItem.getProduct().getName(),
                        cartItem.calculateUnitItemPrice(),
                        cartItem.getQty(),
                        cartItem.getItemConfig()
                ))
                .toList();
        clear();
        return orderItems;
    }
}
