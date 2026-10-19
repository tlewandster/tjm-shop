package pl.tlewand.shop.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ComputerTest {

    @ParameterizedTest
    @CsvSource("1,Computer,10000,100,INTEL_I5,NVIDIA_RTX_4060,_16GB")
    void shouldCreateComputerSuccessfully(long id, String name, BigDecimal price, int stock, Cpu cpu, Gpu gpu, Ram ram) {
        Computer computer = new Computer(id, name, price, stock, cpu, gpu, ram);

        assertThat(computer.getCpu()).isEqualTo(cpu);
        assertThat(computer.getGpu()).isEqualTo(gpu);
        assertThat(computer.getRam()).isEqualTo(ram);
    }

    @ParameterizedTest
    @CsvSource({
            "1,Computer,10000,100,,NVIDIA_RTX_4060,_16GB",
            "1,Computer,10000,100,INTEL_I5,,_16GB",
            "1,Computer,10000,100,INTEL_I5,NVIDIA_RTX_4060,"
    })
    void shouldThrowNullPointerException(long id, String name, BigDecimal price, int stock, Cpu cpu, Gpu gpu, Ram ram) {
        assertThatThrownBy(() -> new Computer(id, name, price, stock, cpu, gpu, ram))
                .isInstanceOf(NullPointerException.class);
    }
}
