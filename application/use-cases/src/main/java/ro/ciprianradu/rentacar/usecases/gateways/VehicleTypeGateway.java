package ro.ciprianradu.rentacar.usecases.gateways;

import java.util.Optional;
import ro.ciprianradu.rentacar.entity.VehicleType;

/**
 * Data Access Interface for {@link VehicleType}s
 */
public interface VehicleTypeGateway {

    VehicleType save(VehicleType vehicleType);

    Optional<VehicleType> findByType(String vehicleType);

}
