package ro.ciprianradu.rentacar.rest.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.ciprianradu.rentacar.http.dtos.Location;
import ro.ciprianradu.rentacar.usecases.registerlocation.RegisterLocationInputData;
import ro.ciprianradu.rentacar.usecases.registerlocation.RegisterLocationInputPort;
import ro.ciprianradu.rentacar.usecases.registerlocation.RegisterLocationOutputData;
import ro.ciprianradu.rentacar.usecases.registerlocation.RegisterLocationOutputPort;

/**
 *
 */
class RegisterLocationControllerTest {

    @Test
    void test_registerLocation_noLocation_doesNotRegister() {
        final RegisterLocationController controller = new RegisterLocationController(new MockRegisterLocationInputPort());
        final Location location = new Location();
        location.setName("SBZ");
        location.setAddress("Sibiu Airport");
        final ResponseEntity<?> responseEntity = controller.registerLocation(null);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void test_registerLocation_noLocationName_doesNotRegister() {
        final RegisterLocationController controller = new RegisterLocationController(new MockRegisterLocationInputPort());
        final Location location = new Location();
        location.setAddress("Sibiu Airport");
        final ResponseEntity<?> responseEntity = controller.registerLocation(location);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void test_registerLocation_validLocation_registers() {
        final RegisterLocationController controller = new RegisterLocationController(new MockRegisterLocationInputPort());
        final Location location = new Location();
        location.setName("SBZ");
        location.setAddress("Sibiu Airport");
        final ResponseEntity<?> responseEntity = controller.registerLocation(location);
        Assertions.assertEquals(ResponseEntity.created(URI.create(RegisterLocationController.ENDPOINT + "/" + location.getName())).body(location), responseEntity);
    }

    @Test
    void test_registerLocation_duplicateRenter_doesNotRegister() {
        final RegisterLocationController controller = new RegisterLocationController(new MockRegisterLocationInputPort());
        final Location location = new Location();
        location.setName("SBZ");
        location.setAddress("Sibiu Airport");
        controller.registerLocation(location);
        ResponseEntity<?> responseEntity = controller.registerLocation(location);
        Assertions.assertEquals(ResponseEntity.status(HttpStatus.CONFLICT).build(), responseEntity);
    }

    private static class MockRegisterLocationInputPort implements RegisterLocationInputPort {

        private List<String> registeredLocations = new ArrayList<>();

        @Override
        public void registerLocation(final RegisterLocationInputData inputData, final RegisterLocationOutputPort outputPort) {
            boolean registered;
            if (registeredLocations.contains(inputData.getName())) {
                registered = false;
            } else {
                registeredLocations.add(inputData.getName());
                registered = true;
            }
            outputPort.present(new RegisterLocationOutputData(registered));
        }

    }

}