package ro.ciprianradu.rentacar.usecases.gateways;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ro.ciprianradu.rentacar.entity.VehicleModel;

/**
 *
 */
public class InMemoryVehicleModelGateway extends EntityGateway<VehicleModel> implements
    VehicleModelGateway {

    @Override
    public Optional<VehicleModel> findByModel(final String model) {
        final List<VehicleModel> vehicleModels = new ArrayList<>();
        findAll().forEach(vt -> {
            if (model.equals(vt.getName())) {
                vehicleModels.add(vt);
            }
        });
        VehicleModel foundVehicleModel = null;
        if (!vehicleModels.isEmpty()) {
            foundVehicleModel = vehicleModels.get(0);
        }
        return Optional.ofNullable(foundVehicleModel);
    }
}
