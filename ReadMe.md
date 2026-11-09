# Project SHOP

## Business overview

- Individual customers can buy laptops, phones, other electronics and accessories in the store.
- Using the CLI, a customer can:
    - view available products
    - add a selected product to the cart
    - remove a selected product from the cart
    - view the cart contents with its total
    - change the configuration of a selected computer in the cart (CPU, GPU, RAM)
    - place an order, optionally with a discount code, and receive an invoice
    - exit the program
- The store accepts the following discount codes:
    - DISC10 - 10%
    - DISC15 - 15%
    - DISC20 - 20%
    - HALLOWEEN26 - 26%
- The store offers a 15% night-time discount on all purchases made between midnight and 6 a.m.
- If both a discount code and the night-time discount apply, the larger discount is used.
- The system does not allow buying more items than are in stock.
- Changing a computer's configuration adjusts the price by the difference in component prices.
- The product price is locked in when the order is placed and does not change if the catalog price changes later.
- All order times refer to the store's configured time zone.
## Technical overview

- The project uses:
    - Java 26
    - Maven
    - junit-jupiter
    - mockito-core
    - assertj-core
    - lombok
    - commons-lang3
- Packages:
    - `pl.tlewand.shop.exception` - custom exceptions
    - `pl.tlewand.shop.model.cart` - models describing the cart and the items in it
    - `pl.tlewand.shop.model.order` - models describing the order, its items, the customer and the invoice
    - `pl.tlewand.shop.model.product` - models describing products: computers with their configuration, smartphones, other electronics, and enums for computer components
    - `pl.tlewand.shop.repository` - product repositories
    - `pl.tlewand.shop.service.discount` - discount policies and the service that picks the best discount
    - `pl.tlewand.shop.service` - services for order processing, product management and stock management
    - `pl.tlewand.shop.storage` - a class that reads and writes stock levels from/to a file
- All monetary amounts use `BigDecimal`.
- Order times use `Instant` and a defined store time zone.
- Stock levels are loaded from a file at startup and saved to it on exit.
- Orders can be processed synchronously or asynchronously (in parallel threads); invoice numbers use `AtomicLong` and stock validation and reduction are `synchronized` to stay thread-safe.
- When several discounts apply at the same time, the largest one wins.