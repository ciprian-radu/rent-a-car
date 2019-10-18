package ro.ciprianradu.rentacar.rest.controllers;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.ciprianradu.rentacar.http.dtos.Vehicle;
import ro.ciprianradu.rentacar.usecases.registervehicle.RegisterVehicleInputData;
import ro.ciprianradu.rentacar.usecases.registervehicle.RegisterVehicleInputPort;
import ro.ciprianradu.rentacar.usecases.registervehicle.RegisterVehicleOutputData;
import ro.ciprianradu.rentacar.usecases.registervehicle.RegisterVehicleOutputPort;

/**
 *
 */
class RegisterVehicleControllerTest {

    @Test
    void test_registerVehicle_noVehicle_doesNotRegister() {
        final RegisterVehicleController controller = new RegisterVehicleController(
            new MockRegisterVehicleInputPort());
        final ResponseEntity<?> responseEntity = controller.registerVehicle(null);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void test_registerVehicle_noVehicleId_doesNotRegister() {
        final RegisterVehicleController controller = new RegisterVehicleController(
            new MockRegisterVehicleInputPort());
        final Vehicle vehicle = new Vehicle();
        vehicle.setType("Economy Car");
        vehicle.setBrand("Opel");
        vehicle.setModel("Astra");
        vehicle.setRate(BigDecimal.TEN);
        final ResponseEntity<?> responseEntity = controller.registerVehicle(vehicle);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void test_registerVehicle_whitespaceId_doesNotRegister() {
        final RegisterVehicleController controller = new RegisterVehicleController(
            new MockRegisterVehicleInputPort());
        final Vehicle vehicle = new Vehicle();
        vehicle.setId("license plate");
        vehicle.setType("Economy Car");
        vehicle.setBrand("Opel");
        vehicle.setModel("Astra");
        vehicle.setRate(BigDecimal.TEN);
        final ResponseEntity<?> responseEntity = controller.registerVehicle(vehicle);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void test_registerVehicle_noVehicleType_doesNotRegister() {
        final RegisterVehicleController controller = new RegisterVehicleController(
            new MockRegisterVehicleInputPort());
        final Vehicle vehicle = new Vehicle();
        vehicle.setId("license-plate");
        vehicle.setBrand("Opel");
        vehicle.setModel("Astra");
        vehicle.setRate(BigDecimal.TEN);
        final ResponseEntity<?> responseEntity = controller.registerVehicle(vehicle);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void test_registerVehicle_noVehicleBrand_doesNotRegister() {
        final RegisterVehicleController controller = new RegisterVehicleController(
            new MockRegisterVehicleInputPort());
        final Vehicle vehicle = new Vehicle();
        vehicle.setId("license-plate");
        vehicle.setType("Economy Car");
        vehicle.setModel("Astra");
        vehicle.setRate(BigDecimal.TEN);
        final ResponseEntity<?> responseEntity = controller.registerVehicle(vehicle);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void test_registerVehicle_noVehicleModel_doesNotRegister() {
        final RegisterVehicleController controller = new RegisterVehicleController(
            new MockRegisterVehicleInputPort());
        final Vehicle vehicle = new Vehicle();
        vehicle.setId("license-plate");
        vehicle.setType("Economy Car");
        vehicle.setBrand("Opel");
        vehicle.setRate(BigDecimal.TEN);
        final ResponseEntity<?> responseEntity = controller.registerVehicle(vehicle);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void test_registerVehicle_noVehicleRate_doesNotRegister() {
        final RegisterVehicleController controller = new RegisterVehicleController(
            new MockRegisterVehicleInputPort());
        final Vehicle vehicle = new Vehicle();
        vehicle.setId("license-plate");
        vehicle.setType("Economy Car");
        vehicle.setBrand("Opel");
        vehicle.setModel("Astra");
        final ResponseEntity<?> responseEntity = controller.registerVehicle(vehicle);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void test_registerVehicle_validVehicle_registers() {
        final RegisterVehicleController controller = new RegisterVehicleController(
            new MockRegisterVehicleInputPort());
        final Vehicle vehicle = new Vehicle();
        vehicle.setId("license-plate");
        vehicle.setType("Economy Car");
        vehicle.setBrand("Opel");
        vehicle.setModel("Astra");
        vehicle.setRate(BigDecimal.TEN);
        vehicle.setLocation("SBZ");
        final ResponseEntity<?> responseEntity = controller.registerVehicle(vehicle);
        Assertions.assertEquals(ResponseEntity
            .created(URI.create(RegisterVehicleController.ENDPOINT + "/" + vehicle.getId()))
            .body(vehicle), responseEntity);
    }

    @Test
    void test_registerVehicle_duplicateRenter_doesNotRegister() {
        final RegisterVehicleController controller = new RegisterVehicleController(
            new MockRegisterVehicleInputPort());
        final Vehicle vehicle = new Vehicle();
        vehicle.setId("license-plate");
        vehicle.setType("Economy Car");
        vehicle.setBrand("Opel");
        vehicle.setModel("Astra");
        vehicle.setRate(BigDecimal.TEN);
        vehicle.setLocation("SBZ");
        controller.registerVehicle(vehicle);
        ResponseEntity<?> responseEntity = controller.registerVehicle(vehicle);
        Assertions.assertEquals(ResponseEntity.status(HttpStatus.CONFLICT).build(), responseEntity);
    }

    private static class MockRegisterVehicleInputPort implements RegisterVehicleInputPort {

        private List<String> registeredRenters = new ArrayList<>();

        @Override
        public void registerVehicle(final RegisterVehicleInputData inputData,
            final RegisterVehicleOutputPort outputPort) {
            boolean registered;
            if (registeredRenters.contains(inputData.getId())) {
                registered = false;
            } else {
                registeredRenters.add(inputData.getId());
                registered = true;
            }
            outputPort.present(new RegisterVehicleOutputData(registered));
        }

    }

}