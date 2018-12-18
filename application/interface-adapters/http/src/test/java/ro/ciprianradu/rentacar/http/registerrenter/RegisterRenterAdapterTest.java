package ro.ciprianradu.rentacar.http.registerrenter;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ro.ciprianradu.rentacar.http.HttpResponse;
import ro.ciprianradu.rentacar.http.HttpStatus;
import ro.ciprianradu.rentacar.http.dtos.Renter;
import ro.ciprianradu.rentacar.usecases.registerrenter.RegisterRenterInputData;
import ro.ciprianradu.rentacar.usecases.registerrenter.RegisterRenterInputPort;
import ro.ciprianradu.rentacar.usecases.registerrenter.RegisterRenterOutputData;
import ro.ciprianradu.rentacar.usecases.registerrenter.RegisterRenterOutputPort;

/**
 * Unit tests for {@link RegisterRenterAdapter}
 */
class RegisterRenterAdapterTest {

    @Test
    public void test_RegisterRenterAdapter_null_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RegisterRenterAdapter(null);
        });
    }

    @Test
    public void test_RegisterRenterAdapter_instantiates() {
        final RegisterRenterAdapter registerRenterAdapter = new RegisterRenterAdapter(new MockRegisterRenterInputPort());
        Assertions.assertNotNull(registerRenterAdapter);
    }

    @Test
    public void test_registerRenter_nullRenter_returnsBadRequest() {
        final RegisterRenterAdapter registerRenterAdapter = new RegisterRenterAdapter(new MockRegisterRenterInputPort());
        final HttpResponse httpResponse = registerRenterAdapter.registerRenter(null);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatus());
    }

    @Test
    public void test_registerRenter_nullRenterFirstName_returnsBadRequest() {
        final RegisterRenterAdapter registerRenterAdapter = new RegisterRenterAdapter(new MockRegisterRenterInputPort());
        final Renter renter = new Renter();
        renter.setFirstName(null);
        renter.setLastName("Doe");
        renter.setEmail("john.doe@email.com");
        final HttpResponse httpResponse = registerRenterAdapter.registerRenter(renter);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatus());
    }

    @Test
    public void test_registerRenter_nullRenterLastName_returnsBadRequest() {
        final RegisterRenterAdapter registerRenterAdapter = new RegisterRenterAdapter(new MockRegisterRenterInputPort());
        final Renter renter = new Renter();
        renter.setFirstName("John");
        renter.setLastName(null);
        renter.setEmail("john.doe@email.com");
        final HttpResponse httpResponse = registerRenterAdapter.registerRenter(renter);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatus());
    }

    @Test
    public void test_registerRenter_invalidEmail_returnsBadRequest() {
        final RegisterRenterAdapter registerRenterAdapter = new RegisterRenterAdapter(new MockRegisterRenterInputPort());
        final Renter renter = new Renter();
        renter.setFirstName("John");
        renter.setLastName("Doe");
        renter.setEmail("john.doe@email");
        final HttpResponse httpResponse = registerRenterAdapter.registerRenter(renter);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatus());
    }

    @Test
    public void test_registerRenter_returnsCreated() {
        final RegisterRenterAdapter registerRenterAdapter = new RegisterRenterAdapter(new MockRegisterRenterInputPort());
        final Renter renter = new Renter();
        renter.setFirstName("John");
        renter.setLastName("Doe");
        renter.setEmail("john.doe@email.com");
        final HttpResponse httpResponse = registerRenterAdapter.registerRenter(renter);
        Assertions.assertEquals(HttpStatus.CREATED, httpResponse.getStatus());
    }

    @Test
    public void test_registerRenter_sameRenter_returnsConflict() {
        final RegisterRenterAdapter registerRenterAdapter = new RegisterRenterAdapter(new MockRegisterRenterInputPort());
        final Renter renter = new Renter();
        renter.setFirstName("John");
        renter.setLastName("Doe");
        renter.setEmail("john.doe@email.com");
        HttpResponse httpResponse = registerRenterAdapter.registerRenter(renter);
        Assertions.assertEquals(HttpStatus.CREATED, httpResponse.getStatus());
        httpResponse = registerRenterAdapter.registerRenter(renter);
        Assertions.assertEquals(HttpStatus.CONFLICT, httpResponse.getStatus());
    }
    
    private static class MockRegisterRenterInputPort implements RegisterRenterInputPort {

        private List<String> registeredRenters = new ArrayList<>();

        @Override
        public void registerRenter(final RegisterRenterInputData inputData, final RegisterRenterOutputPort outputPort) {
            boolean registered;
            if (registeredRenters.contains(inputData.getEmail())) {
                registered = false;
            } else {
                registeredRenters.add(inputData.getEmail());
                registered = true;
            }
            outputPort.present(new RegisterRenterOutputData(registered));
        }

    }

}