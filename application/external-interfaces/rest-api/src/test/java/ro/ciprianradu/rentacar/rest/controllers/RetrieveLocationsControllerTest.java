package ro.ciprianradu.rentacar.rest.controllers;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ro.ciprianradu.rentacar.http.dtos.Location;
import ro.ciprianradu.rentacar.usecases.retrievelocations.RetrieveLocationsInputData;
import ro.ciprianradu.rentacar.usecases.retrievelocations.RetrieveLocationsInputPort;
import ro.ciprianradu.rentacar.usecases.retrievelocations.RetrieveLocationsOutputData;
import ro.ciprianradu.rentacar.usecases.retrievelocations.RetrieveLocationsOutputPort;

/**
 * Unit tests for {@link RetrieveLocationsController}
 */
class RetrieveLocationsControllerTest {

    @Test
    public void test_retrieveLocations_returnsLocations() {
        final RetrieveLocationsController retrieveLocationsController = new RetrieveLocationsController(
            new MockRetrieveLocationsInputPort());
        final ResponseEntity<?> responseEntity = retrieveLocationsController.retrieveLocations();
        final List<Location> locations = (List<Location>) responseEntity.getBody();
        Assertions.assertFalse(locations.isEmpty());
    }

    private static class MockRetrieveLocationsInputPort implements RetrieveLocationsInputPort {

        @Override
        public void retrieveLocations(final RetrieveLocationsInputData inputData,
            final RetrieveLocationsOutputPort outputPort) {
            final RetrieveLocationsOutputData outputData = new RetrieveLocationsOutputData();
            outputData.getLocations().add(outputData.new Location());
            outputPort.present(outputData);
        }

    }

}