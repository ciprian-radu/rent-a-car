package ro.ciprianradu.rentacar.usecases.searchvehicles;

import java.time.ZonedDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link SearchVehiclesInputData}
 */
class SearchVehiclesInputDataTest {

    @Test
    void test_SearchVehiclesInputData_nullPickupDate_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new SearchVehiclesInputData(null, "SBZ", ZonedDateTime.now(), "SBZ");
        });
    }

    @Test
    void test_SearchVehiclesInputData_nullPickupLocation_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new SearchVehiclesInputData(ZonedDateTime.now(), null, ZonedDateTime.now(), "SBZ");
        });
    }

    @Test
    void test_SearchVehiclesInputData_emptyPickupLocation_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new SearchVehiclesInputData(ZonedDateTime.now(), "", ZonedDateTime.now(), "SBZ");
        });
    }

    @Test
    void test_SearchVehiclesInputData_nullReturnDate_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new SearchVehiclesInputData(ZonedDateTime.now(), "SBZ", null, "SBZ");
        });
    }

    @Test
    void test_SearchVehiclesInputData_nullReturnLocation_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new SearchVehiclesInputData(ZonedDateTime.now(), "SBZ", ZonedDateTime.now(), null);
        });
    }

    @Test
    void test_SearchVehiclesInputData_emptyReturnLocation_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new SearchVehiclesInputData(ZonedDateTime.now(), "SBZ", ZonedDateTime.now(), null);
        });
    }

    @Test
    void test_SearchVehiclesInputData_instantiates() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new SearchVehiclesInputData(ZonedDateTime.now(), "SBZ", ZonedDateTime.now(), "SBZ");
        });
    }

}