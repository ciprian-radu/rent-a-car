package ro.ciprianradu.rentacar.gateways;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import ro.ciprianradu.rentacar.usecases.Context;
import ro.ciprianradu.rentacar.entity.VehicleModel;
import ro.ciprianradu.rentacar.usecases.gateways.VehicleModelGateway;

/**
 * Unit tests for {@link VehicleModelGateway}
 */
class VehicleModelJdbcGatewayTest {

    @BeforeEach
    void setup() {
        final VehicleModelGateway vehicleModelGateway = new VehicleModelJdbcGateway(JdbcTemplateFactory.createJdbcTemplate());
        Context.vehicleModelGateway = vehicleModelGateway;
    }

    @Test
    void test_save_duplicateName_doesNotSave() {
        final VehicleModel vehicleModel = new VehicleModel();
        vehicleModel.setName("model");
        Context.vehicleModelGateway.save(vehicleModel);
        Assertions.assertThrows(DuplicateKeyException.class, () -> Context.vehicleModelGateway.save(vehicleModel));
    }

    @Test
    void test_findByModel_missingName_empty() {
        final Optional<VehicleModel> vehicleModelOptional = Context.vehicleModelGateway.findByModel("model");
        Assertions.assertFalse(vehicleModelOptional.isPresent());
    }

    @Test
    void test_findByModel_name_returnsVehicleModel() {
        final VehicleModel vehicleModel = new VehicleModel();
        vehicleModel.setName("model");
        Context.vehicleModelGateway.save(vehicleModel);
        final Optional<VehicleModel> vehicleModelOptional = Context.vehicleModelGateway.findByModel("model");
        Assertions.assertTrue(vehicleModelOptional.isPresent());
    }

}