package ro.ciprianradu.rentacar.rest.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.ciprianradu.rentacar.http.dtos.Renter;
import ro.ciprianradu.rentacar.usecases.registerrenter.RegisterRenterInputData;
import ro.ciprianradu.rentacar.usecases.registerrenter.RegisterRenterInputPort;
import ro.ciprianradu.rentacar.usecases.registerrenter.RegisterRenterOutputData;
import ro.ciprianradu.rentacar.usecases.registerrenter.RegisterRenterOutputPort;

/**
 *
 */
class RegisterRenterControllerTest {

    @Test
    void test_registerRenter_noRenter_doesNotRegister() {
        final RegisterRenterController controller = new RegisterRenterController(new MockRegisterRenterInputPort());
        Renter renter = new Renter();
        renter.setFirstName("John");
        renter.setLastName("Doe");
        renter.setEmail("john.doe@email.com");
        final ResponseEntity<?> responseEntity = controller.registerRenter(null);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void test_registerRenter_noRenterFirstName_doesNotRegister() {
        final RegisterRenterController controller = new RegisterRenterController(new MockRegisterRenterInputPort());
        Renter renter = new Renter();
        renter.setLastName("Doe");
        renter.setEmail("john.doe@email.com");
        final ResponseEntity<?> responseEntity = controller.registerRenter(renter);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void test_registerRenter_noRenterLastName_doesNotRegister() {
        final RegisterRenterController controller = new RegisterRenterController(new MockRegisterRenterInputPort());
        Renter renter = new Renter();
        renter.setFirstName("John");
        renter.setEmail("john.doe@email.com");
        final ResponseEntity<?> responseEntity = controller.registerRenter(renter);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void test_registerRenter_noRenterEmail_doesNotRegister() {
        final RegisterRenterController controller = new RegisterRenterController(new MockRegisterRenterInputPort());
        Renter renter = new Renter();
        renter.setFirstName("John");
        renter.setLastName("Doe");
        final ResponseEntity<?> responseEntity = controller.registerRenter(renter);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void test_registerRenter_invalidEmail_registers() {
        final RegisterRenterController controller = new RegisterRenterController(new MockRegisterRenterInputPort());
        Renter renter = new Renter();
        renter.setFirstName("John");
        renter.setLastName("Doe");
        renter.setEmail("john.doe@");
        final ResponseEntity<?> responseEntity = controller.registerRenter(renter);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void test_registerRenter_validRenter_registers() {
        final RegisterRenterController controller = new RegisterRenterController(new MockRegisterRenterInputPort());
        Renter renter = new Renter();
        renter.setFirstName("John");
        renter.setLastName("Doe");
        renter.setEmail("john.doe@email.com");
        final ResponseEntity<?> responseEntity = controller.registerRenter(renter);
        Assertions.assertEquals(ResponseEntity.created(URI.create(RegisterRenterController.ENDPOINT + "/" + renter.getEmail())).body(renter), responseEntity);
    }

    @Test
    void test_registerRenter_duplicateRenter_doesNotRegister() {
        final RegisterRenterController controller = new RegisterRenterController(new MockRegisterRenterInputPort());
        Renter renter = new Renter();
        renter.setFirstName("John");
        renter.setLastName("Doe");
        renter.setEmail("john.doe@email.com");
        controller.registerRenter(renter);
        ResponseEntity<?> responseEntity = controller.registerRenter(renter);
        Assertions.assertEquals(ResponseEntity.status(HttpStatus.CONFLICT).build(), responseEntity);
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