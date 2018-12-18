package ro.ciprianradu.rentacar.usecases.registerrenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link RegisterRenterInputData}
 */
class RegisterRenterInputDataTest {

    @Test
    void test_RegisterRenterInputData_nullFirstName_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RegisterRenterInputData(null, "last", "e-mail"));
    }

    @Test
    void test_RegisterRenterInputData_emptyFirstName_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RegisterRenterInputData("", "last", "e-mail"));
    }

    @Test
    void test_RegisterRenterInputData_nullLastName_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RegisterRenterInputData("first", null, "e-mail"));
    }

    @Test
    void test_RegisterRenterInputData_emptyLastName_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RegisterRenterInputData("first", "", "e-mail"));
    }

    @Test
    void test_RegisterRenterInputData_nullEmail_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RegisterRenterInputData("first", "last", null));
    }

    @Test
    void test_RegisterRenterInputData_emptyEmail_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RegisterRenterInputData("first", "last", ""));
    }

    @Test
    void test_RegisterRenterInputData_shouldInstantiate() {
        final RegisterRenterInputData registerLocationInputData = new RegisterRenterInputData("name", "last", "e-mail");
        Assertions.assertNotNull(registerLocationInputData);
    }

}