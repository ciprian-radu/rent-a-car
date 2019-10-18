package ro.ciprianradu.rentacar.usecases.gateways;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import ro.ciprianradu.rentacar.entity.Vehicle;

/**
 * Data Access Interface for {@link Vehicle}s
 */
public interface VehicleGateway {

    Vehicle save(Vehicle vehicle);

    Optional<Vehicle> findById(String id);

    /**
     * Search vehicles by several criteria. All criteria are optional. Obviously, leaving all
     * criteria <code>null</code> will retrieve all results.
     *
     * @param type the vehicle type
     * @param brand the vehicle brand
     * @param model the vehicle model
     * @param maximumRate the maximum rent rate
     * @return a list with all vehicles that match the search criteria (may be empty but not
     * <code>null</code>)
     */
    List<Vehicle> search(String type, String brand, String model, BigDecimal maximumRate);

}
