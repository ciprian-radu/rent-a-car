package ro.ciprianradu.rentacar.usecases.gateways;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ro.ciprianradu.rentacar.entity.VehicleType;

/**
 *
 */
public class InMemoryVehicleTypeGateway extends EntityGateway<VehicleType> implements VehicleTypeGateway {

    @Override
    public Optional<VehicleType> findByType(final String type) {
        final List<VehicleType> vehicleTypes = new ArrayList<>();
        findAll().forEach(vt -> {
            if (type.equals(vt.getType())) {
                vehicleTypes.add(vt);
            }
        });
        VehicleType foundVehicleType = null;
        if (!vehicleTypes.isEmpty()) {
            foundVehicleType = vehicleTypes.get(0);
        }
        return Optional.ofNullable(foundVehicleType);
    }
}
