package pl.tlewand.shop.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.tlewand.shop.model.product.Electronics;
import pl.tlewand.shop.model.product.Product;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoggingProductRepositoryTest {

    @Mock
    private ProductRepository delegate;
    @InjectMocks
    private LoggingProductRepository loggingProductRepository;

    @Test
    void shouldReturnTheSameResultThatDelegate() {
        long id = 1;
        Product product = new Electronics(id, "Product", new BigDecimal(10), 10);
        when(delegate.find(id)).thenReturn(Optional.of(product));

        Optional<Product> result = loggingProductRepository.find(id);

        assertThat(result).contains(product);
    }

    @Test
    void shouldPassSaveToDelegate() {
        Product product = new Electronics(1, "Product", new BigDecimal(10), 10);

        loggingProductRepository.save(product);

        verify(delegate).save(product);
    }
}