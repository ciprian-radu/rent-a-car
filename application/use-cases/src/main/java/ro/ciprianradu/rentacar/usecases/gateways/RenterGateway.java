package ro.ciprianradu.rentacar.usecases.gateways;

import java.util.Optional;
import ro.ciprianradu.rentacar.entity.Renter;

/**
 * Data Access Interface for {@link Renter}s
 */
public interface RenterGateway {

    Renter save(Renter renter);

    Optional<Renter> findByEmail(String email);

}
