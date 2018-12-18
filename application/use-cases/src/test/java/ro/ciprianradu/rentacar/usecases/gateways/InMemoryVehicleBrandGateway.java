package ro.ciprianradu.rentacar.usecases.gateways;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ro.ciprianradu.rentacar.entity.VehicleBrand;

/**
 *
 */
public class InMemoryVehicleBrandGateway extends EntityGateway<VehicleBrand> implements VehicleBrandGateway {

    @Override
    public Optional<VehicleBrand> findByBrand(final String brand) {
        final List<VehicleBrand> vehicleBrands = new ArrayList<>();
        findAll().forEach(vt -> {
            if (brand.equals(vt.getName())) {
                vehicleBrands.add(vt);
            }
        });
        VehicleBrand foundVehicleBrand = null;
        if (!vehicleBrands.isEmpty()) {
            foundVehicleBrand = vehicleBrands.get(0);
        }
        return Optional.ofNullable(foundVehicleBrand);
    }
}
