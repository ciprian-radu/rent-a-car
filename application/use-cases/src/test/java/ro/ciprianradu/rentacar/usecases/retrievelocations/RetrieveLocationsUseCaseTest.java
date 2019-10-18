package ro.ciprianradu.rentacar.usecases.retrievelocations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.ciprianradu.rentacar.entity.Location;
import ro.ciprianradu.rentacar.usecases.gateways.GatewaysAccessor;
import ro.ciprianradu.rentacar.usecases.gateways.InMemoryLocationGateway;
import ro.ciprianradu.rentacar.usecases.gateways.LocationGateway;

/**
 * Unit tests for {@link RetrieveLocationsUseCase}
 */
class RetrieveLocationsUseCaseTest {

    private GatewaysAccessor gatewaysAccessor = new GatewaysAccessor();

    private LocationGateway locationGateway = new InMemoryLocationGateway();

    @BeforeEach
    void setupContext() {
        gatewaysAccessor.setLocationGateway(locationGateway);
    }

    @Test
    void test_retrieveLocations_noLocations_returnsEmptyList() {
        final RetrieveLocationsUseCase retrieveLocationsUseCase = new RetrieveLocationsUseCase(
            gatewaysAccessor);
        retrieveLocationsUseCase
            .retrieveLocations(new RetrieveLocationsInputData(), retrieveLocationsOutputData -> {
                Assertions.assertTrue(retrieveLocationsOutputData.getLocations().isEmpty());
            });
    }

    @Test
    void test_retrieveLocations_locations_returnsAllLocations() {
        int numberOfLocations = 10;
        for (int i = 0; i < numberOfLocations; i++) {
            locationGateway.save(new Location());
        }
        final RetrieveLocationsUseCase retrieveLocationsUseCase = new RetrieveLocationsUseCase(
            gatewaysAccessor);
        retrieveLocationsUseCase
            .retrieveLocations(new RetrieveLocationsInputData(), retrieveLocationsOutputData -> {
                Assertions.assertEquals(numberOfLocations,
                    retrieveLocationsOutputData.getLocations().size());
            });
    }

}