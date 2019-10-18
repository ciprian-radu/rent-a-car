package ro.ciprianradu.rentacar.usecases.registerlocation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link RegisterLocationInputData}
 */
class RegisterLocationInputDataTest {

    @Test
    void test_RegisterLocationInputData_nullName_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new RegisterLocationInputData(null, "address"));
    }

    @Test
    void test_RegisterLocationInputData_emptyName_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new RegisterLocationInputData("", "address"));
    }

    @Test
    void test_RegisterLocationInputData_name_shouldInstantiate() {
        final RegisterLocationInputData registerLocationInputData = new RegisterLocationInputData(
            "name", "address");
        Assertions.assertNotNull(registerLocationInputData);
    }


}