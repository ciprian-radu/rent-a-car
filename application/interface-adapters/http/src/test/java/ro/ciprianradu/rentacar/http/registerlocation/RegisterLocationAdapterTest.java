package ro.ciprianradu.rentacar.http.registerlocation;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ro.ciprianradu.rentacar.http.HttpResponse;
import ro.ciprianradu.rentacar.http.HttpStatus;
import ro.ciprianradu.rentacar.http.dtos.Location;
import ro.ciprianradu.rentacar.usecases.registerlocation.RegisterLocationInputData;
import ro.ciprianradu.rentacar.usecases.registerlocation.RegisterLocationInputPort;
import ro.ciprianradu.rentacar.usecases.registerlocation.RegisterLocationOutputData;
import ro.ciprianradu.rentacar.usecases.registerlocation.RegisterLocationOutputPort;

/**
 * Unit tests for {@link RegisterLocationAdapter}
 */
class RegisterLocationAdapterTest {

    @Test
    public void test_RegisterLocationAdapter_null_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RegisterLocationAdapter(null);
        });
    }

    @Test
    public void test_RegisterLocationAdapter_instantiates() {
        final RegisterLocationAdapter registerLocationAdapter = new RegisterLocationAdapter(
            new MockRegisterLocationInputPort());
        Assertions.assertNotNull(registerLocationAdapter);
    }

    @Test
    public void test_registerLocation_nullLocation_returnsBadRequest() {
        final RegisterLocationAdapter registerLocationAdapter = new RegisterLocationAdapter(
            new MockRegisterLocationInputPort());
        final HttpResponse httpResponse = registerLocationAdapter.registerLocation(null);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatus());
    }

    @Test
    public void test_registerLocation_nullLocationName_returnsBadRequest() {
        final RegisterLocationAdapter registerLocationAdapter = new RegisterLocationAdapter(
            new MockRegisterLocationInputPort());
        final Location location = new Location();
        final HttpResponse httpResponse = registerLocationAdapter.registerLocation(location);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatus());
    }

    @Test
    public void test_registerLocation_locationName_returnsCreated() {
        final RegisterLocationAdapter registerLocationAdapter = new RegisterLocationAdapter(
            new MockRegisterLocationInputPort());
        final Location location = new Location();
        location.setName("name");
        final HttpResponse httpResponse = registerLocationAdapter.registerLocation(location);
        Assertions.assertEquals(HttpStatus.CREATED, httpResponse.getStatus());
    }

    @Test
    public void test_registerLocation_sameLocationName_returnsCreated() {
        final RegisterLocationAdapter registerLocationAdapter = new RegisterLocationAdapter(
            new MockRegisterLocationInputPort());
        final Location location = new Location();
        location.setName("name");
        HttpResponse httpResponse = registerLocationAdapter.registerLocation(location);
        Assertions.assertEquals(HttpStatus.CREATED, httpResponse.getStatus());
        httpResponse = registerLocationAdapter.registerLocation(location);
        Assertions.assertEquals(HttpStatus.CONFLICT, httpResponse.getStatus());
    }

    private static class MockRegisterLocationInputPort implements RegisterLocationInputPort {

        private List<String> registeredLoations = new ArrayList<>();

        @Override
        public void registerLocation(final RegisterLocationInputData inputData,
            final RegisterLocationOutputPort outputPort) {
            boolean registered;
            if (registeredLoations.contains(inputData.getName())) {
                registered = false;
            } else {
                registeredLoations.add(inputData.getName());
                registered = true;
            }
            outputPort.present(new RegisterLocationOutputData(registered));
        }

    }

}