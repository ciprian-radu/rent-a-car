package ro.ciprianradu.rentacar.usecases.gateways;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ro.ciprianradu.rentacar.entity.Location;

/**
 *
 */
public class InMemoryLocationGateway extends EntityGateway<Location> implements LocationGateway {

    @Override
    public Optional<Location> findByName(final String name) {
        final List<Location> locations = new ArrayList<>();
        findAll().forEach(l -> {
            if (name.equals(l.getName())) {
                locations.add(l);
            }
        });
        Location foundLocation = null;
        if (!locations.isEmpty()) {
            foundLocation = locations.get(0);
        }
        return Optional.ofNullable(foundLocation);
    }
}
