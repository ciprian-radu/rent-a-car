package ro.ciprianradu.rentacar.usecases.registervehicle;

import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link RegisterVehicleInputData}
 */
class RegisterVehicleInputDataTest {

    @Test
    void test_RegisterVehicleInputData_nullId_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new RegisterVehicleInputData(null, "type", "brand", "model", BigDecimal.ONE,
                "location"));
    }

    @Test
    void test_RegisterVehicleInputData_emptyId_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new RegisterVehicleInputData("", "type", "brand", "model", BigDecimal.ONE,
                "location"));
    }

    @Test
    void test_RegisterVehicleInputData_nullType_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new RegisterVehicleInputData("id", null, "brand", "model", BigDecimal.ONE,
                "location"));
    }

    @Test
    void test_RegisterVehicleInputData_emptyType_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new RegisterVehicleInputData("id", "", "brand", "model", BigDecimal.ONE,
                "location"));
    }

    @Test
    void test_RegisterVehicleInputData_nullBrand_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new RegisterVehicleInputData("id", "type", null, "model", BigDecimal.ONE,
                "location"));
    }

    @Test
    void test_RegisterVehicleInputData_emptyBrand_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new RegisterVehicleInputData("id", "type", "", "model", BigDecimal.ONE,
                "location"));
    }

    @Test
    void test_RegisterVehicleInputData_nullModel_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new RegisterVehicleInputData("id", "type", "brand", null, BigDecimal.ONE,
                "location"));
    }

    @Test
    void test_RegisterVehicleInputData_emptyModel_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new RegisterVehicleInputData("id", "type", "brand", "", BigDecimal.ONE,
                "location"));
    }

    @Test
    void test_RegisterVehicleInputData_nullRate_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new RegisterVehicleInputData("id", "type", "brand", "model", null, "location"));
    }

    @Test
    void test_RegisterVehicleInputData_zeroRate_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new RegisterVehicleInputData("id", "type", "brand", "model", BigDecimal.ZERO,
                "location"));
    }

    @Test
    void test_RegisterVehicleInputData_negativeRate_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new RegisterVehicleInputData("id", "type", "brand", "model",
                BigDecimal.valueOf(-1), "location"));
    }

    @Test
    void test_RegisterVehicleInputData_emptyLocation_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new RegisterVehicleInputData("id", "type", "brand", "model", BigDecimal.ONE, ""));
    }

    @Test
    void test_RegisterVehicleInputData_nullLocation_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new RegisterVehicleInputData("id", "type", "brand", "model", BigDecimal.ONE,
                null));
    }

    @Test
    void test_RegisterVehicleInputData_shouldInstantiate() {
        final RegisterVehicleInputData registerLocationInputData = new RegisterVehicleInputData(
            "id", "type", "brand", "model", BigDecimal.ONE, "location");
        Assertions.assertNotNull(registerLocationInputData);
    }

}