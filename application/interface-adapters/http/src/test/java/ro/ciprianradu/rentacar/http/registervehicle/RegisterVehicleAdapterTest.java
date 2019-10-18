package ro.ciprianradu.rentacar.http.registervehicle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ro.ciprianradu.rentacar.http.HttpResponse;
import ro.ciprianradu.rentacar.http.HttpStatus;
import ro.ciprianradu.rentacar.http.dtos.Vehicle;
import ro.ciprianradu.rentacar.usecases.registervehicle.RegisterVehicleInputData;
import ro.ciprianradu.rentacar.usecases.registervehicle.RegisterVehicleInputPort;
import ro.ciprianradu.rentacar.usecases.registervehicle.RegisterVehicleOutputData;
import ro.ciprianradu.rentacar.usecases.registervehicle.RegisterVehicleOutputPort;

/**
 * Unit tests for {@link RegisterVehicleAdapter}
 */
class RegisterVehicleAdapterTest {

    @Test
    public void test_RegisterVehicleAdapter_null_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RegisterVehicleAdapter(null);
        });
    }

    @Test
    public void test_RegisterVehicleAdapter_instantiates() {
        final RegisterVehicleAdapter registerVehicleAdapter = new RegisterVehicleAdapter(
            new RegisterVehicleAdapterTest.MockRegisterVehicleInputPort());
        Assertions.assertNotNull(registerVehicleAdapter);
    }

    @Test
    public void test_registerVehicle_nullVehicle_returnsBadRequest() {
        final RegisterVehicleAdapter registerVehicleAdapter = new RegisterVehicleAdapter(
            new RegisterVehicleAdapterTest.MockRegisterVehicleInputPort());
        final HttpResponse httpResponse = registerVehicleAdapter.registerVehicle(null);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatus());
    }

    @Test
    public void test_registerVehicle_nullId_returnsBadRequest() {
        final RegisterVehicleAdapter registerVehicleAdapter = new RegisterVehicleAdapter(
            new RegisterVehicleAdapterTest.MockRegisterVehicleInputPort());
        final Vehicle vehicle = new Vehicle();
        vehicle.setId(null);
        vehicle.setType("type");
        vehicle.setBrand("brand");
        vehicle.setModel("model");
        vehicle.setRate(BigDecimal.ONE);
        vehicle.setLocation("location");
        final HttpResponse httpResponse = registerVehicleAdapter.registerVehicle(vehicle);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatus());
    }

    @Test
    public void test_registerVehicle_idWithWhitespace_returnsBadRequest() {
        final RegisterVehicleAdapter registerVehicleAdapter = new RegisterVehicleAdapter(
            new RegisterVehicleAdapterTest.MockRegisterVehicleInputPort());
        final Vehicle vehicle = new Vehicle();
        vehicle.setId("a whitespace");
        vehicle.setType("type");
        vehicle.setBrand("brand");
        vehicle.setModel("model");
        vehicle.setRate(BigDecimal.ONE);
        vehicle.setLocation("location");
        final HttpResponse httpResponse = registerVehicleAdapter.registerVehicle(vehicle);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatus());
    }

    @Test
    public void test_registerVehicle_nullType_returnsBadRequest() {
        final RegisterVehicleAdapter registerVehicleAdapter = new RegisterVehicleAdapter(
            new RegisterVehicleAdapterTest.MockRegisterVehicleInputPort());
        final Vehicle vehicle = new Vehicle();
        vehicle.setId("id");
        vehicle.setType(null);
        vehicle.setBrand("brand");
        vehicle.setModel("model");
        vehicle.setRate(BigDecimal.ONE);
        vehicle.setLocation("location");
        final HttpResponse httpResponse = registerVehicleAdapter.registerVehicle(vehicle);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatus());
    }

    @Test
    public void test_registerVehicle_nullBrand_returnsBadRequest() {
        final RegisterVehicleAdapter registerVehicleAdapter = new RegisterVehicleAdapter(
            new RegisterVehicleAdapterTest.MockRegisterVehicleInputPort());
        final Vehicle vehicle = new Vehicle();
        vehicle.setId("id");
        vehicle.setType("type");
        vehicle.setBrand(null);
        vehicle.setModel("model");
        vehicle.setRate(BigDecimal.ONE);
        vehicle.setLocation("location");
        final HttpResponse httpResponse = registerVehicleAdapter.registerVehicle(vehicle);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatus());
    }

    @Test
    public void test_registerVehicle_nullModel_returnsBadRequest() {
        final RegisterVehicleAdapter registerVehicleAdapter = new RegisterVehicleAdapter(
            new RegisterVehicleAdapterTest.MockRegisterVehicleInputPort());
        final Vehicle vehicle = new Vehicle();
        vehicle.setId("id");
        vehicle.setType("type");
        vehicle.setBrand("brand");
        vehicle.setModel(null);
        vehicle.setRate(BigDecimal.ONE);
        vehicle.setLocation("location");
        final HttpResponse httpResponse = registerVehicleAdapter.registerVehicle(vehicle);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatus());
    }

    @Test
    public void test_registerVehicle_zeroRate_returnsBadRequest() {
        final RegisterVehicleAdapter registerVehicleAdapter = new RegisterVehicleAdapter(
            new RegisterVehicleAdapterTest.MockRegisterVehicleInputPort());
        final Vehicle vehicle = new Vehicle();
        vehicle.setId("id");
        vehicle.setType("type");
        vehicle.setBrand("brand");
        vehicle.setModel("model");
        vehicle.setRate(BigDecimal.ZERO);
        vehicle.setLocation("location");
        final HttpResponse httpResponse = registerVehicleAdapter.registerVehicle(vehicle);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatus());
    }

    @Test
    public void test_registerVehicle_negativeRate_returnsBadRequest() {
        final RegisterVehicleAdapter registerVehicleAdapter = new RegisterVehicleAdapter(
            new RegisterVehicleAdapterTest.MockRegisterVehicleInputPort());
        final Vehicle vehicle = new Vehicle();
        vehicle.setId("id");
        vehicle.setType("type");
        vehicle.setBrand("brand");
        vehicle.setModel("model");
        vehicle.setRate(BigDecimal.valueOf(-1));
        vehicle.setLocation("location");
        final HttpResponse httpResponse = registerVehicleAdapter.registerVehicle(vehicle);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatus());
    }

    @Test
    public void test_registerVehicle_nullLocation_returnsBadRequest() {
        final RegisterVehicleAdapter registerVehicleAdapter = new RegisterVehicleAdapter(
            new RegisterVehicleAdapterTest.MockRegisterVehicleInputPort());
        final Vehicle vehicle = new Vehicle();
        vehicle.setId("id");
        vehicle.setType("type");
        vehicle.setBrand("brand");
        vehicle.setModel("model");
        vehicle.setRate(BigDecimal.ONE);
        vehicle.setLocation(null);
        final HttpResponse httpResponse = registerVehicleAdapter.registerVehicle(vehicle);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatus());
    }

    @Test
    public void test_registerVehicle_locationWithWhitespace_returnsBadRequest() {
        final RegisterVehicleAdapter registerVehicleAdapter = new RegisterVehicleAdapter(
            new RegisterVehicleAdapterTest.MockRegisterVehicleInputPort());
        final Vehicle vehicle = new Vehicle();
        vehicle.setId("id");
        vehicle.setType("type");
        vehicle.setBrand("brand");
        vehicle.setModel("model");
        vehicle.setRate(BigDecimal.ONE);
        vehicle.setLocation("a location");
        final HttpResponse httpResponse = registerVehicleAdapter.registerVehicle(vehicle);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatus());
    }

    @Test
    public void test_registerVehicle_returnsCreated() {
        final RegisterVehicleAdapter registerVehicleAdapter = new RegisterVehicleAdapter(
            new RegisterVehicleAdapterTest.MockRegisterVehicleInputPort());
        final Vehicle vehicle = new Vehicle();
        vehicle.setId("id");
        vehicle.setType("type");
        vehicle.setBrand("brand");
        vehicle.setModel("model");
        vehicle.setRate(BigDecimal.ONE);
        vehicle.setLocation("location");
        final HttpResponse httpResponse = registerVehicleAdapter.registerVehicle(vehicle);
        Assertions.assertEquals(HttpStatus.CREATED, httpResponse.getStatus());
    }

    @Test
    public void test_registerVehicle_sameVehicle_returnsConflict() {
        final RegisterVehicleAdapter registerVehicleAdapter = new RegisterVehicleAdapter(
            new RegisterVehicleAdapterTest.MockRegisterVehicleInputPort());
        final Vehicle vehicle = new Vehicle();
        vehicle.setId("id");
        vehicle.setType("type");
        vehicle.setBrand("brand");
        vehicle.setModel("model");
        vehicle.setRate(BigDecimal.ONE);
        vehicle.setLocation("location");
        HttpResponse httpResponse = registerVehicleAdapter.registerVehicle(vehicle);
        Assertions.assertEquals(HttpStatus.CREATED, httpResponse.getStatus());
        httpResponse = registerVehicleAdapter.registerVehicle(vehicle);
        Assertions.assertEquals(HttpStatus.CONFLICT, httpResponse.getStatus());
    }

    private static class MockRegisterVehicleInputPort implements RegisterVehicleInputPort {

        private List<String> registeredVehicles = new ArrayList<>();

        @Override
        public void registerVehicle(final RegisterVehicleInputData inputData,
            final RegisterVehicleOutputPort outputPort) {
            boolean registered;
            if (registeredVehicles.contains(inputData.getId())) {
                registered = false;
            } else {
                registeredVehicles.add(inputData.getId());
                registered = true;
            }
            outputPort.present(new RegisterVehicleOutputData(registered));
        }

    }

}