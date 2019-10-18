package ro.ciprianradu.rentacar.gateways;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import ro.ciprianradu.rentacar.entity.VehicleModel;
import ro.ciprianradu.rentacar.usecases.gateways.VehicleModelGateway;

/**
 * Unit tests for {@link VehicleModelGateway}
 */
class VehicleModelJdbcGatewayTest {

    private VehicleModelGateway vehicleModelGateway = new VehicleModelJdbcGateway(
        JdbcTemplateFactory.createJdbcTemplate());

    @Test
    void test_save_duplicateName_doesNotSave() {
        final VehicleModel vehicleModel = new VehicleModel();
        vehicleModel.setName("model");
        vehicleModelGateway.save(vehicleModel);
        Assertions.assertThrows(DuplicateKeyException.class,
            () -> vehicleModelGateway.save(vehicleModel));
    }

    @Test
    void test_findByModel_missingName_empty() {
        final Optional<VehicleModel> vehicleModelOptional = vehicleModelGateway
            .findByModel("model");
        Assertions.assertFalse(vehicleModelOptional.isPresent());
    }

    @Test
    void test_findByModel_name_returnsVehicleModel() {
        final VehicleModel vehicleModel = new VehicleModel();
        vehicleModel.setName("model");
        vehicleModelGateway.save(vehicleModel);
        final Optional<VehicleModel> vehicleModelOptional = vehicleModelGateway
            .findByModel("model");
        Assertions.assertTrue(vehicleModelOptional.isPresent());
    }

}