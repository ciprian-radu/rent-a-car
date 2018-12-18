package ro.ciprianradu.rentacar.http.retrievelocations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ro.ciprianradu.rentacar.http.HttpStatus;
import ro.ciprianradu.rentacar.usecases.retrievelocations.RetrieveLocationsInputData;
import ro.ciprianradu.rentacar.usecases.retrievelocations.RetrieveLocationsInputPort;
import ro.ciprianradu.rentacar.usecases.retrievelocations.RetrieveLocationsOutputData;
import ro.ciprianradu.rentacar.usecases.retrievelocations.RetrieveLocationsOutputPort;

/**
 * Unit tests for {@link RetrieveLocationsAdapter}
 */
class RetrieveLocationsAdapterTest {

    @Test
    public void test_RetrieveLocationsAdapter_null_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            new RetrieveLocationsAdapter(null)
        );
    }

    @Test
    public void test_RetrieveLocationsAdapter_instantiates() {
        final RetrieveLocationsAdapter registerLocationAdapter = new RetrieveLocationsAdapter(new MockRetrieveLocationsInputPort());
        Assertions.assertNotNull(registerLocationAdapter);
    }

    @Test
    public void test_retrieveLocations_returnsOk() {
        final RetrieveLocationsAdapter registerLocationAdapter = new RetrieveLocationsAdapter(new MockRetrieveLocationsInputPort());
        final RetrieveLocationsHttpResponse httpResponse = registerLocationAdapter.retrieveLocations();
        Assertions.assertEquals(HttpStatus.OK, httpResponse.getHttpResponse().getStatus());
    }

    @Test
    public void test_retrieveLocations_returnsLocations() {
        final RetrieveLocationsAdapter registerLocationAdapter = new RetrieveLocationsAdapter(new MockRetrieveLocationsInputPort());
        final RetrieveLocationsHttpResponse httpResponse = registerLocationAdapter.retrieveLocations();
        Assertions.assertFalse(httpResponse.getLocations().isEmpty());
    }

    private static class MockRetrieveLocationsInputPort implements RetrieveLocationsInputPort {

        @Override
        public void retrieveLocations(final RetrieveLocationsInputData inputData, final RetrieveLocationsOutputPort outputPort) {
            final RetrieveLocationsOutputData outputData = new RetrieveLocationsOutputData();
            outputData.getLocations().add(outputData.new Location());
            outputPort.present(outputData);
        }

    }
}