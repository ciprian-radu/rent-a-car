package ro.ciprianradu.rentacar.gateways;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import ro.ciprianradu.rentacar.entity.VehicleType;
import ro.ciprianradu.rentacar.usecases.gateways.VehicleTypeGateway;

/**
 * Unit tests for {@link VehicleTypeGateway}
 */
class VehicleTypeJdbcGatewayTest {

    private VehicleTypeGateway vehicleTypeGateway = new VehicleTypeJdbcGateway(
        JdbcTemplateFactory.createJdbcTemplate());

    @Test
    void test_save_duplicateType_doesNotSave() {
        final VehicleType vehicleType = new VehicleType();
        vehicleType.setType("Type");
        vehicleTypeGateway.save(vehicleType);
        Assertions
            .assertThrows(DuplicateKeyException.class, () -> vehicleTypeGateway.save(vehicleType));
    }

    @Test
    void test_findByType_missingType_empty() {
        final Optional<VehicleType> vehicleTypeOptional = vehicleTypeGateway.findByType("type");
        Assertions.assertFalse(vehicleTypeOptional.isPresent());
    }

    @Test
    void test_findByType_type_returnsVehicleType() {
        final VehicleType vehicleType = new VehicleType();
        vehicleType.setType("Type");
        vehicleTypeGateway.save(vehicleType);
        final Optional<VehicleType> vehicleTypeOptional = vehicleTypeGateway.findByType("Type");
        Assertions.assertTrue(vehicleTypeOptional.isPresent());
    }

}