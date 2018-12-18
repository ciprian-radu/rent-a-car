package ro.ciprianradu.rentacar.rest.controllers;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import ro.ciprianradu.rentacar.usecases.searchvehicles.SearchVehiclesInputData;
import ro.ciprianradu.rentacar.usecases.searchvehicles.SearchVehiclesInputPort;
import ro.ciprianradu.rentacar.usecases.searchvehicles.SearchVehiclesOutputData;
import ro.ciprianradu.rentacar.usecases.searchvehicles.SearchVehiclesOutputData.VehicleCategory;
import ro.ciprianradu.rentacar.usecases.searchvehicles.SearchVehiclesOutputPort;

/**
 *
 */
class SearchVehiclesControllerTest {

    private static final ZonedDateTime PICKUP_DATE = ZonedDateTime.of(2018, 1, 1, 10, 0, 0, 0, ZoneId.of("UTC"));

    private static final String LOCATION = "SBZ";

    private static final ZonedDateTime RETURN_DATE = ZonedDateTime.of(2018, 1, 3, 10, 0, 0, 0, ZoneId.of("UTC"));

    @Test
    void test_searchVehicles_futureRentPeriod_returnsAllVehicles() {
        final SearchVehiclesController controller = new SearchVehiclesController(new MockSearchVehiclesInputPort());
        // vehicle 0 is rented from day 1 to day 3
        // vehicle 1 is rented from day 2 to day 4
        // vehicle 2 is rented from day 3 to day 5
        //
        // we want to rent in the future from day 10 to day 20
        //
        // => no vehicle is available for renting
        final ResponseEntity<?> responseEntity = controller.searchVehicles(PICKUP_DATE.plusDays(9), LOCATION, RETURN_DATE.plusDays(17), LOCATION);
        Set<ro.ciprianradu.rentacar.http.dtos.VehicleCategory> vehicleCategories = (Set<ro.ciprianradu.rentacar.http.dtos.VehicleCategory>) responseEntity.getBody();
        Assertions.assertEquals(3, vehicleCategories.size());
    }

    @Test
    void test_searchVehicles_differentLocation_returnsNoVehicle() {
        final SearchVehiclesController controller = new SearchVehiclesController(new MockSearchVehiclesInputPort());
        // vehicle 0 is rented from day 1 to day 3
        // vehicle 1 is rented from day 2 to day 4
        // vehicle 2 is rented from day 3 to day 5
        //
        // we want to rent in the future from day 10 to day 20 but from a location where no vehicles are available
        //
        // => no vehicle is available for renting
        final ResponseEntity<?> responseEntity = controller.searchVehicles(PICKUP_DATE, "other", RETURN_DATE.plusDays(2), "other");
        Set<ro.ciprianradu.rentacar.http.dtos.VehicleCategory> vehicleCategories = (Set<ro.ciprianradu.rentacar.http.dtos.VehicleCategory>) responseEntity.getBody();
        Assertions.assertTrue(vehicleCategories.isEmpty());
    }

    @Test
    void test_searchVehicles_noVehicleAvailable_returnsNoVehicle() {
        final SearchVehiclesController controller = new SearchVehiclesController(new MockSearchVehiclesInputPort());
        // vehicle 0 is rented from day 1 to day 3
        // vehicle 1 is rented from day 2 to day 4
        // vehicle 2 is rented from day 3 to day 5
        //
        // we want to rent from day 1 to day 5
        //
        // => no vehicle is available for renting
        final ResponseEntity<?> responseEntity = controller.searchVehicles(PICKUP_DATE, LOCATION, RETURN_DATE.plusDays(2), LOCATION);
        Set<ro.ciprianradu.rentacar.http.dtos.VehicleCategory> vehicleCategories = (Set<ro.ciprianradu.rentacar.http.dtos.VehicleCategory>) responseEntity.getBody();
        Assertions.assertTrue(vehicleCategories.isEmpty());
    }

    @Test
    void test_searchVehicles_vehicle0IsAvailable_returnsVehicle0() {
        final SearchVehiclesController controller = new SearchVehiclesController(new MockSearchVehiclesInputPort());
        // vehicle 0 is rented from day 1 to day 3
        // vehicle 1 is rented from day 2 to day 4
        // vehicle 2 is rented from day 3 to day 5
        //
        // we want to rent from day 4 to day 5
        //
        // => vehicle 0 is available for renting
        final ResponseEntity<?> responseEntity = controller.searchVehicles(PICKUP_DATE.plusDays(3), LOCATION, RETURN_DATE.plusDays(2), LOCATION);
        Set<ro.ciprianradu.rentacar.http.dtos.VehicleCategory> vehicleCategories = (Set<ro.ciprianradu.rentacar.http.dtos.VehicleCategory>) responseEntity.getBody();
        Assertions.assertEquals(1, vehicleCategories.size());
        Assertions.assertEquals("model0", vehicleCategories.iterator().next().getModel());
    }

    @Test
    void test_searchVehicles_vehicle0and1AreAvailable_returnsVehicles0And1() {
        final SearchVehiclesController controller = new SearchVehiclesController(new MockSearchVehiclesInputPort());
        // vehicle 0 is rented from day 1 to day 3
        // vehicle 1 is rented from day 2 to day 4
        // vehicle 2 is rented from day 3 to day 5
        //
        // we want to rent from day 5 to day 6
        //
        // => vehicle 0 and 1 are available for renting
        final ResponseEntity<?> responseEntity = controller.searchVehicles(PICKUP_DATE.plusDays(4), LOCATION, RETURN_DATE.plusDays(3), LOCATION);
        Set<ro.ciprianradu.rentacar.http.dtos.VehicleCategory> vehicleCategories = (Set<ro.ciprianradu.rentacar.http.dtos.VehicleCategory>) responseEntity.getBody();
        Assertions.assertEquals(2, vehicleCategories.size());
    }

    @Test
    void test_searchVehicles_vehicle2IsAvailable_returnsVehicle2() {
        final SearchVehiclesController controller = new SearchVehiclesController(new MockSearchVehiclesInputPort());
        // vehicle 0 is rented from day 1 to day 3
        // vehicle 1 is rented from day 2 to day 4
        // vehicle 2 is rented from day 3 to day 5
        //
        // we want to rent from day 1 to day 2
        //
        // => vehicle 2 is available for renting
        final ResponseEntity<?> responseEntity = controller.searchVehicles(PICKUP_DATE, LOCATION, RETURN_DATE.minusDays(1), LOCATION);
        Set<ro.ciprianradu.rentacar.http.dtos.VehicleCategory> vehicleCategories = (Set<ro.ciprianradu.rentacar.http.dtos.VehicleCategory>) responseEntity.getBody();
        Assertions.assertEquals(1, vehicleCategories.size());
        Assertions.assertEquals("model2", vehicleCategories.iterator().next().getModel());
    }

    private static class MockSearchVehiclesInputPort implements SearchVehiclesInputPort {

        private final List<MockRent> mockData = new ArrayList<>();

        @Mock
        private SearchVehiclesOutputData outputData;

        public MockSearchVehiclesInputPort() {
            MockitoAnnotations.initMocks(this);
            setupVehicles();
        }

        private static MockRent createMock(int counter, final SearchVehiclesOutputData outputData) {
            final MockRent mockRent = new MockRent();

            final VehicleCategory vehicleCategory = outputData.new VehicleCategory();

            vehicleCategory.setType("type" + counter % 2); // Only two types of vehicles
            vehicleCategory.setBrand("brand"); // Just one brand
            vehicleCategory.setModel("model" + counter);
            vehicleCategory.setRate(BigDecimal.valueOf(counter));

            mockRent.vehicleCategory = vehicleCategory;
            mockRent.pickupDate = PICKUP_DATE.plusDays(counter);
            mockRent.pickupLocation = LOCATION;
            mockRent.returnDate = RETURN_DATE.plusDays(counter);
            mockRent.returnLocation = LOCATION;

            return mockRent;
        }

        private static boolean isAfterOrEqual(final ZonedDateTime left, final ZonedDateTime right) {
            return left.isAfter(right) || left.isEqual(right);
        }

        private static boolean isBeforeOrEqual(final ZonedDateTime left, final ZonedDateTime right) {
            return left.isBefore(right) || left.isEqual(right);
        }

        private void setupVehicles() {
            mockData.add(createMock(0, outputData));
            mockData.add(createMock(1, outputData));
            mockData.add(createMock(2, outputData));
        }

        @Override
        public void searchVehicles(final SearchVehiclesInputData inputData, final SearchVehiclesOutputPort outputPort) {
            Mockito.when(outputData.getVehicleCategories()).thenReturn(mockData.stream().filter(mr ->
                inputData.getPickupLocationName().equals(mr.pickupLocation) && inputData.getReturnLocationName().equals(mr.returnLocation) && (
                    inputData.getPickupDate().isAfter(mr.returnDate) || inputData.getReturnDate().isBefore(mr.pickupDate))
            ).map(md -> md.vehicleCategory).collect(Collectors.toSet()));
            outputPort.present(outputData);
        }

        /**
         * Mocks a rented vehicle.
         */
        private static class MockRent {

            VehicleCategory vehicleCategory;

            ZonedDateTime pickupDate;

            String pickupLocation;

            ZonedDateTime returnDate;

            String returnLocation;

        }

    }

}