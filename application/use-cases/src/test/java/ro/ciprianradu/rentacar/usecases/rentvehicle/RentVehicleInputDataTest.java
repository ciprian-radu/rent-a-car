package ro.ciprianradu.rentacar.usecases.rentvehicle;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link RentVehicleInputData}
 */
class RentVehicleInputDataTest {

    @Test
    void test_RentVehicleInputData_nullRenterEmail_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new RentVehicleInputData(null, "type", "brand", "model", ZonedDateTime.now(),
                "Sibiu", ZonedDateTime.now(), "Sibiu"));
    }

    @Test
    void test_RentVehicleInputData_emptyRenterEmail_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new RentVehicleInputData("", "type", "brand", "model", ZonedDateTime.now(),
                "Sibiu", ZonedDateTime.now(), "Sibiu"));
    }

    @Test
    void test_RentVehicleInputData_nullVehicleId_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new RentVehicleInputData("email", null, "brand", "model", ZonedDateTime.now(),
                "Sibiu", ZonedDateTime.now(), "Sibiu"));
    }

    @Test
    void test_RentVehicleInputData_emptyVehicleId_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new RentVehicleInputData("email", "", "brand", "model", ZonedDateTime.now(),
                "Sibiu", ZonedDateTime.now(), "Sibiu"));
    }

    @Test
    void test_RentVehicleInputData_nullPickupDate_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new RentVehicleInputData("email", "type", "brand", "model", null, "Sibiu",
                ZonedDateTime.now(), "Sibiu"));
    }

//    @Test
//    void test_RentVehicleInputData_pastPickupDate_shouldThrowException() {
//        ZonedDateTime pastPickupDate = ZonedDateTime.now().minus(1, ChronoUnit.SECONDS);
//        Assertions
//            .assertThrows(IllegalArgumentException.class, () -> new RentVehicleInputData("email", "type", "brand", "model", pastPickupDate, "Sibiu", ZonedDateTime.now(), "Sibiu"));
//    }

    @Test
    void test_RentVehicleInputData_pickupDateAfterReturnDate_shouldThrowException() {
        ZonedDateTime pickupDate = ZonedDateTime.now();
        ZonedDateTime returnDate = ZonedDateTime.now().minus(1, ChronoUnit.SECONDS);
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new RentVehicleInputData("email", "type", "brand", "model", pickupDate, "Sibiu",
                returnDate, "Sibiu"));
    }

    @Test
    void test_RentVehicleInputData_nullReturnDate_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new RentVehicleInputData("email", "type", "brand", "model", ZonedDateTime.now(),
                "Sibiu", null, "Sibiu"));
    }

    @Test
    void test_RentVehicleInputData_nullPickupLocation_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new RentVehicleInputData("email", "type", "brand", "model", ZonedDateTime.now(),
                null, ZonedDateTime.now().plus(1, ChronoUnit.DAYS), "Sibiu"));
    }

    @Test
    void test_RentVehicleInputData_emptyPickupLocation_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new RentVehicleInputData("email", "type", "brand", "model", ZonedDateTime.now(),
                "", ZonedDateTime.now().plus(1, ChronoUnit.DAYS), "Sibiu"));
    }

    @Test
    void test_RentVehicleInputData_nullReturnLocation_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new RentVehicleInputData("email", "type", "brand", "model", ZonedDateTime.now(),
                "Sibiu", ZonedDateTime.now().plus(1, ChronoUnit.DAYS), null));
    }

    @Test
    void test_RentVehicleInputData_emptyReturnLocation_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new RentVehicleInputData("email", "type", "brand", "model", ZonedDateTime.now(),
                "Sibiu", ZonedDateTime.now().plus(1, ChronoUnit.DAYS), ""));
    }

    @Test
    void test_RentVehicleInputData_shouldThrowException() {
        final ZonedDateTime now = ZonedDateTime.now();
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new RentVehicleInputData("email", "type", "brand", "model", now, "Sibiu",
                now.plus(1, ChronoUnit.HOURS).minus(1, ChronoUnit.MICROS), "Sibiu"));
    }

    @Test
    void test_RentVehicleInputData_shouldInstantiate() {
        Assertions.assertNotNull(
            new RentVehicleInputData("email", "type", "brand", "model", ZonedDateTime.now(),
                "Sibiu", ZonedDateTime.now().plus(1, ChronoUnit.DAYS), "Sibiu"));
    }

}