package ro.ciprianradu.rentacar.http.searchvehicles;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ro.ciprianradu.rentacar.http.HttpStatus;
import ro.ciprianradu.rentacar.usecases.searchvehicles.SearchVehiclesInputData;
import ro.ciprianradu.rentacar.usecases.searchvehicles.SearchVehiclesInputPort;
import ro.ciprianradu.rentacar.usecases.searchvehicles.SearchVehiclesOutputData;
import ro.ciprianradu.rentacar.usecases.searchvehicles.SearchVehiclesOutputData.VehicleCategory;
import ro.ciprianradu.rentacar.usecases.searchvehicles.SearchVehiclesOutputPort;

/**
 * Unit tests for {@link SearchVehiclesAdapter}
 */
class SearchVehiclesAdapterTest {

    @Test
    public void test_SearchVehiclesAdapter_null_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new SearchVehiclesAdapter(null);
        });
    }

    @Test
    public void test_SearchVehiclesAdapter_instantiates() {
        final SearchVehiclesAdapter searchVehiclesAdapter = new SearchVehiclesAdapter(new MockSearchVehiclesInputPort());
        Assertions.assertNotNull(searchVehiclesAdapter);
    }

    @Test
    public void test_searchVehicles_returnsVehicleCategories() {
        final ZonedDateTime pickupDate = ZonedDateTime.now();
        final SearchVehiclesAdapter searchVehiclesAdapter = new SearchVehiclesAdapter(new MockSearchVehiclesInputPort());
        final SearchVehiclesHttpResponse searchVehiclesHttpResponse = searchVehiclesAdapter
            .searchVehicles(pickupDate, "pickup", pickupDate.plusHours(1), "return");
        Assertions.assertFalse(searchVehiclesHttpResponse.getVehicleCategories().isEmpty());
    }

    @Test
    public void test_searchVehicles_invalidReturnDate_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            final ZonedDateTime pickupDate = ZonedDateTime.now();
            final ZonedDateTime returnDate = pickupDate; // At least one rent hour is required.
            final SearchVehiclesAdapter searchVehiclesAdapter = new SearchVehiclesAdapter(new MockSearchVehiclesInputPort());
            searchVehiclesAdapter.searchVehicles(pickupDate, "pickup", returnDate, "return");
        });
    }

    @Test
    public void test_searchVehicles_invalidInput_returnsBadRequest() {
        final ZonedDateTime pickupDate = ZonedDateTime.now();
        final SearchVehiclesAdapter searchVehiclesAdapter = new SearchVehiclesAdapter(new MockSearchVehiclesInputPort());
        final SearchVehiclesHttpResponse searchVehiclesHttpResponse = searchVehiclesAdapter
            .searchVehicles(pickupDate, "pickup", null, "return");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, searchVehiclesHttpResponse.getHttpResponse().getStatus());
    }

    private static class MockSearchVehiclesInputPort implements SearchVehiclesInputPort {

        @Mock
        private final SearchVehiclesOutputData searchVehiclesOutputData = new SearchVehiclesOutputData();

        private Set<VehicleCategory> vehicleCategories = new HashSet<>();

        public MockSearchVehiclesInputPort() {
            setup();
        }

        private void setup() {
            createVhicleCategories();
            MockitoAnnotations.initMocks(this);
            Mockito.when(searchVehiclesOutputData.getVehicleCategories()).thenReturn(vehicleCategories);
        }

        private void createVhicleCategories() {
            VehicleCategory vehicleCategory = searchVehiclesOutputData.new VehicleCategory();
            vehicleCategory.setType("type");
            vehicleCategory.setBrand("brand-1");
            vehicleCategory.setModel("model-1");
            vehicleCategories.add(vehicleCategory);

            vehicleCategory = searchVehiclesOutputData.new VehicleCategory();
            vehicleCategory.setType("type");
            vehicleCategory.setBrand("brand-2");
            vehicleCategory.setModel("model-2");
            vehicleCategories.add(vehicleCategory);

            vehicleCategory = searchVehiclesOutputData.new VehicleCategory();
            vehicleCategory.setType("type");
            vehicleCategory.setBrand("brand-2");
            vehicleCategory.setModel("model-1");
            vehicleCategories.add(vehicleCategory);

            vehicleCategory = searchVehiclesOutputData.new VehicleCategory();
            vehicleCategory.setType("type");
            vehicleCategory.setBrand("brand-1");
            vehicleCategory.setModel("model-1");
            vehicleCategories.add(vehicleCategory);
        }

        @Override
        public void searchVehicles(final SearchVehiclesInputData inputData, final SearchVehiclesOutputPort outputPort) {
            outputPort.present(searchVehiclesOutputData);
        }

    }

}