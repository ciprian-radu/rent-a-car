package ro.ciprianradu.rentacar.usecases.gateways;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import ro.ciprianradu.rentacar.entity.Vehicle;

/**
 *
 */
public class InMemoryVehicleGateway extends EntityGateway<Vehicle> implements VehicleGateway {

    @Override
    public List<Vehicle> search(final String type, final String brand, final String model,
        final BigDecimal maximumRate) {
        final List<Vehicle> vehicles = new ArrayList<>();

        final Iterable<Vehicle> allVehicles = findAll();

        allVehicles.forEach(v -> {
            Vehicle candidateVehicle = v;
            if (type != null && !v.getType().getType().equals(type)) {
                candidateVehicle = null;
            }
            if (brand != null && !v.getBrand().getName().equals(brand)) {
                candidateVehicle = null;
            }
            if (model != null && !v.getModel().getName().equals(model)) {
                candidateVehicle = null;
            }
            if (maximumRate != null && v.getRate().compareTo(maximumRate) > 0) {
                candidateVehicle = null;
            }
            if (candidateVehicle != null) {
                vehicles.add(candidateVehicle);
            }
        });

        return vehicles;
    }

}
