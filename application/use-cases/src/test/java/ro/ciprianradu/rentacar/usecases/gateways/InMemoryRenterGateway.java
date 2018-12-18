package ro.ciprianradu.rentacar.usecases.gateways;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ro.ciprianradu.rentacar.entity.Renter;

/**
 *
 */
public class InMemoryRenterGateway extends EntityGateway<Renter> implements RenterGateway {

    @Override
    public Optional<Renter> findByEmail(final String email) {
        final List<Renter> renters = new ArrayList<>();
        findAll().forEach(r -> {
            if (email.equals(r.getEmail())) {
                renters.add(r);
            }
        });
        Renter foundRenter = null;
        if (!renters.isEmpty()) {
            foundRenter = renters.get(0);
        }
        return Optional.ofNullable(foundRenter);
    }
}
