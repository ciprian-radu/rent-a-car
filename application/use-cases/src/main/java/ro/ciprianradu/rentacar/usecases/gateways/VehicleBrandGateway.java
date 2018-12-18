package ro.ciprianradu.rentacar.usecases.gateways;

import java.util.Optional;
import ro.ciprianradu.rentacar.entity.VehicleBrand;

/**
 * Data Access Interface for {@link VehicleBrand}s
 */
public interface VehicleBrandGateway {

    VehicleBrand save(VehicleBrand vehicleBrand);

    Optional<VehicleBrand> findByBrand(String vehicleBrand);

}
