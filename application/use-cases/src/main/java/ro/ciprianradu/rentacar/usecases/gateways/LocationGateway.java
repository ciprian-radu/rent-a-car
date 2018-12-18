package ro.ciprianradu.rentacar.usecases.gateways;

import java.util.Optional;
import ro.ciprianradu.rentacar.entity.Location;

/**
 * Data Access Interface for {@link Location}s
 */
public interface LocationGateway {

    Location save(Location location);

    Optional<Location> findByName(String name);

    Iterable<Location> findAll();

}
