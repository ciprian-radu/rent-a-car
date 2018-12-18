package ro.ciprianradu.rentacar.gateways;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import ro.ciprianradu.rentacar.usecases.Context;
import ro.ciprianradu.rentacar.entity.VehicleType;
import ro.ciprianradu.rentacar.usecases.gateways.VehicleTypeGateway;

/**
 * Unit tests for {@link VehicleTypeGateway}
 */
class VehicleTypeJdbcGatewayTest {

    @BeforeEach
    void setup() {
        final VehicleTypeGateway vehicleTypeGateway = new VehicleTypeJdbcGateway(JdbcTemplateFactory.createJdbcTemplate());
        Context.vehicleTypeGateway = vehicleTypeGateway;
    }

    @Test
    void test_save_duplicateType_doesNotSave() {
        final VehicleType vehicleType = new VehicleType();
        vehicleType.setType("Type");
        Context.vehicleTypeGateway.save(vehicleType);
        Assertions.assertThrows(DuplicateKeyException.class, () -> Context.vehicleTypeGateway.save(vehicleType));
    }

    @Test
    void test_findByType_missingType_empty() {
        final Optional<VehicleType> vehicleTypeOptional = Context.vehicleTypeGateway.findByType("type");
        Assertions.assertFalse(vehicleTypeOptional.isPresent());
    }

    @Test
    void test_findByType_type_returnsVehicleType() {
        final VehicleType vehicleType = new VehicleType();
        vehicleType.setType("Type");
        Context.vehicleTypeGateway.save(vehicleType);
        final Optional<VehicleType> vehicleTypeOptional = Context.vehicleTypeGateway.findByType("Type");
        Assertions.assertTrue(vehicleTypeOptional.isPresent());
    }

}