package ro.ciprianradu.rentacar.usecases.gateways;

import java.time.ZonedDateTime;
import java.util.Optional;
import ro.ciprianradu.rentacar.entity.Reservation;

/**
 * Data Access Interface for {@link Reservation}s
 */
public interface ReservationGateway {

    Reservation save(Reservation reservation);

    Iterable<Reservation> findAllByVehicle(String vehicleId);

    /**
     * Determines if the given vehicle can be rented during the specified time period. It looks for recorded reservations for the same vehicle ID and determines
     * if any of them has a pickup date between the pickup and return dates of the to be submitted time period. If no such reservation is found, the given
     * vehicle can be rented.
     *
     * @param vehicleId the ID of the vehicle to be rented (cannot be <code>null</code> nor empty)
     * @param pickupDate the pickup date (cannot be <code>null</code>)
     * @param returnDate the return date (cannot be <code>null</code>)
     * @return an optional {@link Reservation}, with the most in the future return date
     */
    Optional<Reservation> findByOverlappingRentPeriod(final String vehicleId, final ZonedDateTime pickupDate, final ZonedDateTime returnDate);

}
