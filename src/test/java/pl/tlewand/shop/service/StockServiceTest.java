package pl.tlewand.shop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.tlewand.shop.model.product.Electronics;
import pl.tlewand.shop.model.product.Product;
import pl.tlewand.shop.storage.FileStorage;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    private final Path path = Path.of("testdata", "stock.txt");
    @InjectMocks
    private StockService stockService;
    @Mock
    private ProductManager productManager;
    @Mock
    private FileStorage fileStorage;

    @Test
    void shouldUpdateStockFromFile() throws IOException {
        String firstStock = "1;10";
        String secondStock = "2;20";

        when(fileStorage.load(path)).thenReturn(List.of(firstStock,secondStock));

        stockService.load(path, productManager);

        verify(productManager).updateStock(1L,10);
        verify(productManager).updateStock(2L,20);
    }

    @Test
    void shouldSaveStockToFile() throws IOException {
        Product product1 = new Electronics(1, "Product1", new BigDecimal("10.00"), 10);
        Product product2 = new Electronics(2, "Product2", new BigDecimal("20.00"), 20);
        String expectedFileContent = "1;10\n2;20";
        when(productManager.findAll()).thenReturn(List.of(product1,product2));

        stockService.save(path,productManager);

        verify(fileStorage).save(path,expectedFileContent);
    }
}