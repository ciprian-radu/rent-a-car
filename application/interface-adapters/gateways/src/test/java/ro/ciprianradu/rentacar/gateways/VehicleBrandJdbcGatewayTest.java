package ro.ciprianradu.rentacar.gateways;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import ro.ciprianradu.rentacar.usecases.Context;
import ro.ciprianradu.rentacar.entity.VehicleBrand;
import ro.ciprianradu.rentacar.usecases.gateways.VehicleBrandGateway;

/**
 * Unit tests for {@link VehicleBrandGateway}
 */
class VehicleBrandJdbcGatewayTest {

    @BeforeEach
    void setup() {
        final VehicleBrandGateway vehicleBrandGateway = new VehicleBrandJdbcGateway(JdbcTemplateFactory.createJdbcTemplate());
        Context.vehicleBrandGateway = vehicleBrandGateway;
    }

    @Test
    void test_save_duplicateName_doesNotSave() {
        final VehicleBrand vehicleBrand = new VehicleBrand();
        vehicleBrand.setName("VehicleBrand 1");
        Context.vehicleBrandGateway.save(vehicleBrand);
        Assertions.assertThrows(DuplicateKeyException.class, () -> Context.vehicleBrandGateway.save(vehicleBrand));
    }

    @Test
    void test_findByName_missingName_empty() {
        final Optional<VehicleBrand> vehicleBrandOptional = Context.vehicleBrandGateway.findByBrand("name");
        Assertions.assertFalse(vehicleBrandOptional.isPresent());
    }

    @Test
    void test_findByName_name_returnsVehicleBrand() {
        final VehicleBrand vehicleBrand = new VehicleBrand();
        vehicleBrand.setName("VehicleBrand 1");
        Context.vehicleBrandGateway.save(vehicleBrand);
        final Optional<VehicleBrand> vehicleBrandOptional = Context.vehicleBrandGateway.findByBrand("VehicleBrand 1");
        Assertions.assertTrue(vehicleBrandOptional.isPresent());
    }

}