package pl.tlewand.shop.model.cart;

import org.junit.jupiter.api.Test;
import pl.tlewand.shop.model.order.Client;
import pl.tlewand.shop.model.order.Order;
import pl.tlewand.shop.model.order.OrderItem;
import pl.tlewand.shop.model.product.*;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CartTest {
    private static final Electronics ELECTRONICS = new Electronics(1, "Electronics", new BigDecimal("10.00"), 100);
    private static final Computer COMPUTER = new Computer(2, "Computer", new BigDecimal("2000.00"), 200, Cpu.AMD_RYZEN_7, Gpu.NVIDIA_RTX_4060, Ram._16GB);
    private static final ComputerConfig COMPUTER_CONFIG = new ComputerConfig(Cpu.AMD_RYZEN_7, Gpu.NVIDIA_RTX_4060, Ram._16GB);
    private static final Client CLIENT = new Client(1, "Jan Kowalski", "ul.Błotna 69, Podmokle");
    private static final int QTY = 1;
    private final Cart cart = new Cart();

    @Test
    void shouldAddNewItem() {
        cart.addItem(ELECTRONICS, null, QTY);

        assertThat(cart.getItems().size())
                .isEqualTo(1);
    }

    @Test
    void shouldThrowWhenProductNull() {
        assertThatThrownBy(() -> cart.addItem(null, null, QTY))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Product cannot be null");
    }

    @Test
    void shouldThrowWhenQtyNotPositive() {
        assertThatThrownBy(() -> cart.addItem(ELECTRONICS, null, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantity must be > 0");
    }

    @Test
    void shouldThrowWhenComputerWithoutConfig() {
        assertThatThrownBy(() -> cart.addItem(COMPUTER, null, QTY))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Computer must have configuration");
    }

    @Test
    void shouldThrowWhenConfigForNonComputer() {
        assertThatThrownBy(() -> cart.addItem(ELECTRONICS, COMPUTER_CONFIG, QTY))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Only computer can have configuration");
    }

    @Test
    void shouldThrowWhenQtyExceedsStock() {
        int qtyExceedsStock = ELECTRONICS.getStock() + 5;

        assertThatThrownBy(() -> cart.addItem(ELECTRONICS, null, qtyExceedsStock))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Qty exceeds stock");
    }

    @Test
    void shouldThrowWhenSummarizeQtyExceedsStock() {
        cart.addItem(ELECTRONICS, null, 1);
        int qtyExceedsStock = ELECTRONICS.getStock();

        assertThatThrownBy(() -> cart.addItem(ELECTRONICS, null, qtyExceedsStock))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Qty exceeds stock");
    }

    @Test
    void shouldMergeSameProductAndConfig() {
        int firstItemQty = 1;
        int secondItemQty = 2;
        cart.addItem(COMPUTER, COMPUTER_CONFIG, firstItemQty);

        cart.addItem(COMPUTER, COMPUTER_CONFIG, secondItemQty);

        assertThat(cart.getItems().size())
                .isEqualTo(1);
        assertThat(cart.getItems().getFirst().getQty())
                .isEqualTo(firstItemQty + secondItemQty);
    }

    @Test
    void shouldCreateSeparateItemsForDifferentConfig() {
        ComputerConfig firstConfig = new ComputerConfig(Cpu.AMD_RYZEN_7, Gpu.NVIDIA_RTX_4060, Ram._16GB);
        ComputerConfig secondConfig = new ComputerConfig(Cpu.AMD_RYZEN_5, Gpu.AMD_RX_7700, Ram._32GB);
        cart.addItem(COMPUTER, firstConfig, QTY);

        cart.addItem(COMPUTER, secondConfig, QTY);

        assertThat(cart.getItems().size())
                .isEqualTo(2);
    }

    @Test
    void shouldThrowWhenRemovingUnknownItem() {
        int unknownItemId = 50;

        assertThatThrownBy(() -> cart.removeItem(unknownItemId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining(String.valueOf(unknownItemId));
    }

    @Test
    void shouldRemoveItem() {
        cart.addItem(ELECTRONICS, null, QTY);
        cart.addItem(COMPUTER, COMPUTER_CONFIG, QTY);
        long remainingId = cart.getItems().get(0).getItemId();
        long toRemoveId = cart.getItems().get(1).getItemId();


        cart.removeItem(toRemoveId);

        assertThat(cart.getItems())
                .extracting(CartItem::getItemId)
                .containsExactly(remainingId);
    }

    @Test
    void shouldReturnZeroTotalForEmptyCart() {
        assertThat(cart.totalPrice())
                .isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void shouldCalculateTotal() {
        cart.addItem(ELECTRONICS, null, 9);
        cart.addItem(COMPUTER, COMPUTER_CONFIG, 1);
        CartItem firstItem = cart.getItems().get(0);
        CartItem secondItem = cart.getItems().get(1);
        BigDecimal expectedTotalPriceFirstItem = firstItem.getProduct().getPrice().multiply(BigDecimal.valueOf(firstItem.getQty()));
        BigDecimal expectedTotalPriceSecondItem = secondItem.getProduct().getPrice().multiply(BigDecimal.valueOf(secondItem.getQty()));

        BigDecimal totalPrice = cart.totalPrice();

        assertThat(totalPrice)
                .isEqualByComparingTo(expectedTotalPriceFirstItem.add(expectedTotalPriceSecondItem));
    }

    @Test
    void shouldClearCart() {
        cart.addItem(ELECTRONICS, null, QTY);
        cart.addItem(COMPUTER, COMPUTER_CONFIG, QTY);

        cart.clear();

        assertThat(cart.getItems().size())
                .isEqualTo(0);
    }

    @Test
    void shouldReturnUnmodifiableItems() {
        cart.addItem(ELECTRONICS, null, QTY);
        cart.addItem(COMPUTER, COMPUTER_CONFIG, QTY);
        CartItem cartItem = new CartItem(100, ELECTRONICS, null, QTY);

        assertThatThrownBy(() -> cart.getItems().add(cartItem))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void shouldReturnOrderAndClearCartOnCheckout() {
        cart.addItem(ELECTRONICS, null, QTY);
        cart.addItem(COMPUTER, COMPUTER_CONFIG, QTY);

        Order order = cart.checkout(CLIENT);

        assertThat(order.getOrderItems())
                .containsExactly(
                        new OrderItem(ELECTRONICS.getId(), ELECTRONICS.getName(), ELECTRONICS.getPrice(), QTY, null),
                        new OrderItem(COMPUTER.getId(), COMPUTER.getName(), COMPUTER.getPrice(), QTY, COMPUTER_CONFIG)
                );
        assertThat(order.getClient())
                .isSameAs(CLIENT);
        assertThat(cart.getItems())
                .isEmpty();
    }

    @Test
    void shouldThrowOnEmptyCheckout() {
        assertThatThrownBy(() -> cart.checkout(CLIENT))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Cart cannot be empty at checkout");
    }

    @Test
    void shouldHoldPriceAtCheckout() {
        Electronics electronics = new Electronics(1, "Electronics", new BigDecimal("10.00"), 100);
        cart.addItem(electronics, null, QTY);
        BigDecimal priceAtCheckout = cart.getItems().getFirst().calculateUnitItemPrice();
        Order order = cart.checkout(CLIENT);
        electronics.setPrice(electronics.getPrice().multiply(new BigDecimal("2")));

        assertThat(order.getOrderItems().getFirst().price())
                .isEqualByComparingTo(priceAtCheckout);
    }

    @Test
    void shouldThrowNullPointerExceptionWhenClientIsNull() {
        cart.addItem(ELECTRONICS, null, QTY);
        cart.addItem(COMPUTER, COMPUTER_CONFIG, QTY);

        assertThatThrownBy(()-> cart.checkout(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("client cannot be null");

        assertThat(cart.getItems())
                .isNotEmpty();

    }

}
