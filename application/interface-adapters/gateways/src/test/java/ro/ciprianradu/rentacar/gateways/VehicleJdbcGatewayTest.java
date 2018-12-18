package ro.ciprianradu.rentacar.gateways;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import ro.ciprianradu.rentacar.entity.*;
import ro.ciprianradu.rentacar.usecases.Context;
import ro.ciprianradu.rentacar.usecases.gateways.*;

/**
 * Unit tests for {@link VehicleGateway}
 */
class VehicleJdbcGatewayTest {

    @BeforeEach
    void setup() {
        final VehicleGateway vehicleGateway = new VehicleJdbcGateway(JdbcTemplateFactory.createJdbcTemplate());
        Context.vehicleGateway = vehicleGateway;
        final VehicleTypeGateway vehicleTypeGateway = new VehicleTypeJdbcGateway(JdbcTemplateFactory.createJdbcTemplate());
        Context.vehicleTypeGateway = vehicleTypeGateway;
        final VehicleBrandGateway vehicleBrandGateway = new VehicleBrandJdbcGateway(JdbcTemplateFactory.createJdbcTemplate());
        Context.vehicleBrandGateway = vehicleBrandGateway;
        final VehicleModelGateway vehicleModelGateway = new VehicleModelJdbcGateway(JdbcTemplateFactory.createJdbcTemplate());
        Context.vehicleModelGateway = vehicleModelGateway;
        final LocationGateway locationGateway = new LocationJdbcGateway(JdbcTemplateFactory.createJdbcTemplate());
        Context.locationGateway = locationGateway;
    }

    @Test
    void test_save_vehicle_saves() {
        final Vehicle vehicle = setupVehicle();
        Context.vehicleGateway.save(vehicle);
        Assertions.assertNotNull(vehicle);
    }

    @Test
    void test_save_duplicateId_doesNotSave() {
        final Vehicle vehicle = setupVehicle();
        Context.vehicleGateway.save(vehicle);
        Assertions.assertThrows(DuplicateKeyException.class, () -> Context.vehicleGateway.save(vehicle));
    }

    @Test
    void test_save_typeDoesNotExist_doesNotSave() {
        final Vehicle vehicle = setupVehicle();
        Context.vehicleGateway.save(vehicle);
        Assertions.assertThrows(DuplicateKeyException.class, () -> Context.vehicleGateway.save(vehicle));
    }

    @Test
    void test_findById_missingId_empty() {
        final Optional<Vehicle> vehicleOptional = Context.vehicleGateway.findById("license plate");
        Assertions.assertFalse(vehicleOptional.isPresent());
    }

    @Test
    void test_findById_id_returnsVehicle() {
        final Vehicle vehicle = setupVehicle();
        Context.vehicleGateway.save(vehicle);
        final Optional<Vehicle> vehicleOptional = Context.vehicleGateway.findById("license plate");
        Assertions.assertTrue(vehicleOptional.isPresent());
    }

    @Test
    void test_findById_id_returnsVehicleWithModel() {
        final Vehicle vehicle = setupVehicle();
        Context.vehicleGateway.save(vehicle);
        final Optional<Vehicle> vehicleOptional = Context.vehicleGateway.findById("license plate");
        final Vehicle foundVehicle = vehicleOptional.get();
        final VehicleModel model = foundVehicle.getModel();
        Assertions.assertEquals("model", model.getName());
        Assertions.assertFalse(model.isAc());
        Assertions.assertTrue(model.isAutomaticTransmission());
        Assertions.assertEquals(5, model.getSeats());
    }

    @Test
    void test_search_returnsVehicles() {
        final Vehicle vehicle1 = setupVehicle("number 1", "type", "brand", "model", BigDecimal.ONE);
        Context.vehicleGateway.save(vehicle1);
        final Vehicle vehicle2 = setupVehicle("number 2", "type", "brand", "model", BigDecimal.TEN);
        Context.vehicleGateway.save(vehicle2);
        final List<Vehicle> vehicles = Context.vehicleGateway
            .search(vehicle1.getType().getType(), vehicle1.getBrand().getName(), vehicle1.getModel().getName(), BigDecimal.valueOf(5));
        Assertions.assertEquals(1, vehicles.size());
    }

    @Test
    void test_search_noCriteria_returnsAllVehicles() {
        final Vehicle vehicle1 = setupVehicle("number 1", "type", "brand", "model", BigDecimal.ONE);
        Context.vehicleGateway.save(vehicle1);
        final Vehicle vehicle2 = setupVehicle("number 2", "type", "brand", "model", BigDecimal.TEN);
        Context.vehicleGateway.save(vehicle2);
        final List<Vehicle> vehicles = Context.vehicleGateway.search(null, null, null, null);
        Assertions.assertEquals(2, vehicles.size());
    }

    @Test
    void test_search_emptyType_returnsAllVehicles() {
        final Vehicle vehicle1 = setupVehicle("number 1", "type", "brand", "model", BigDecimal.ONE);
        Context.vehicleGateway.save(vehicle1);
        final Vehicle vehicle2 = setupVehicle("number 2", "type", "brand", "model", BigDecimal.TEN);
        Context.vehicleGateway.save(vehicle2);
        final List<Vehicle> vehicles = Context.vehicleGateway.search("", null, null, null);
        Assertions.assertEquals(2, vehicles.size());
    }

    @Test
    void test_search_emptyBrand_returnsAllVehicles() {
        final Vehicle vehicle1 = setupVehicle("number 1", "type", "brand", "model", BigDecimal.ONE);
        Context.vehicleGateway.save(vehicle1);
        final Vehicle vehicle2 = setupVehicle("number 2", "type", "brand", "model", BigDecimal.TEN);
        Context.vehicleGateway.save(vehicle2);
        final List<Vehicle> vehicles = Context.vehicleGateway.search(null, "", null, null);
        Assertions.assertEquals(2, vehicles.size());
    }

    @Test
    void test_search_emptyModel_returnsAllVehicles() {
        final Vehicle vehicle1 = setupVehicle("number 1", "type", "brand", "model", BigDecimal.ONE);
        Context.vehicleGateway.save(vehicle1);
        final Vehicle vehicle2 = setupVehicle("number 2", "type", "brand", "model", BigDecimal.TEN);
        Context.vehicleGateway.save(vehicle2);
        final List<Vehicle> vehicles = Context.vehicleGateway.search(null, null, "", null);
        Assertions.assertEquals(2, vehicles.size());
    }

    private Vehicle setupVehicle() {
        return setupVehicle("license plate", "type", "brand", "model", BigDecimal.TEN, "SBZ");
    }

    private Vehicle setupVehicle(final String id, final String type, final String brand, final String model, final BigDecimal rate) {
        return setupVehicle(id, type, brand, model, rate, "SBZ");
    }

    private Vehicle setupVehicle(final String id, final String type, final String brand, final String model, final BigDecimal rate, final String location) {
        final Vehicle vehicle = new Vehicle(id);
        final VehicleType vehicleType = createVehicleType(type);
        vehicle.setType(vehicleType);
        VehicleBrand vehicleBrand = createVehicleBrand(brand);
        vehicle.setBrand(vehicleBrand);
        VehicleModel vehicleModel = createVehicleModel(model);
        vehicle.setModel(vehicleModel);
        vehicle.setRate(rate);
        vehicle.setLocation(location);
        Location theLocation = new Location();
        theLocation.setName(location);
        saveVehicleDependencies(vehicleType, vehicleBrand, vehicleModel, theLocation);
        return vehicle;
    }

    private void saveVehicleDependencies(final VehicleType vehicleType, final VehicleBrand vehicleBrand, final VehicleModel vehicleModel,
        final Location location) {
        if (!Context.vehicleTypeGateway.findByType(vehicleType.getType()).isPresent()) {
            Context.vehicleTypeGateway.save(vehicleType);
        }
        if (!Context.vehicleBrandGateway.findByBrand(vehicleBrand.getName()).isPresent()) {
            Context.vehicleBrandGateway.save(vehicleBrand);
        }
        if (!Context.vehicleModelGateway.findByModel(vehicleModel.getName()).isPresent()) {
            Context.vehicleModelGateway.save(vehicleModel);
        }
        if (!Context.locationGateway.findByName(location.getName()).isPresent()) {
            Context.locationGateway.save(location);
        }
    }

    private VehicleType createVehicleType(final String type) {
        final VehicleType vehicleType = new VehicleType();
        vehicleType.setType(type);
        return vehicleType;
    }

    private VehicleBrand createVehicleBrand(final String brand) {
        final VehicleBrand vehicleBrand = new VehicleBrand();
        vehicleBrand.setName(brand);
        return vehicleBrand;
    }

    private VehicleModel createVehicleModel(final String model) {
        final VehicleModel vehicleModel = new VehicleModel();
        vehicleModel.setName(model);
        vehicleModel.setAutomaticTransmission(true);
        vehicleModel.setSeats(5);
        return vehicleModel;
    }

}