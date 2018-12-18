package ro.ciprianradu.rentacar.usecases.gateways;

import java.util.Optional;
import ro.ciprianradu.rentacar.entity.VehicleModel;

/**
 * Data Access Interface for {@link VehicleModel}s
 */
public interface VehicleModelGateway {

    VehicleModel save(VehicleModel vehicle);

    Optional<VehicleModel> findByModel(String vehicleModel);

}
