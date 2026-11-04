package pl.tlewand.shop.model.product;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ComputerTest {

    @ParameterizedTest
    @CsvSource("1,Computer,10000,100,INTEL_I5,NVIDIA_RTX_4060,_16GB")
    void shouldCreateComputerSuccessfully(long id, String name, BigDecimal price, int stock, Cpu cpu, Gpu gpu, Ram ram) {
        Computer computer = new Computer(id, name, price, stock, new ComputerConfig(cpu, gpu, ram));

        assertThat(computer.getConfig().cpu()).isEqualTo(cpu);
        assertThat(computer.getConfig().gpu()).isEqualTo(gpu);
        assertThat(computer.getConfig().ram()).isEqualTo(ram);
    }

    @ParameterizedTest
    @CsvSource({
            "1,Computer,10000,100,"
    })
    void shouldThrowNullPointerException(long id, String name, BigDecimal price, int stock, ComputerConfig config) {
        assertThatThrownBy(() -> new Computer(id, name, price, stock, config))
                .isInstanceOf(NullPointerException.class);
    }
}
