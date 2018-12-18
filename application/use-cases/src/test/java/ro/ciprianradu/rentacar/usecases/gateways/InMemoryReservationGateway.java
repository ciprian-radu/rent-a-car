package ro.ciprianradu.rentacar.usecases.gateways;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ro.ciprianradu.rentacar.entity.Reservation;

/**
 *
 */
public class InMemoryReservationGateway extends EntityGateway<Reservation> implements ReservationGateway {

    private static boolean isAfterOrEqual(final ZonedDateTime left, final ZonedDateTime right) {
        return left.isAfter(right) || left.isEqual(right);
    }

    private static boolean isBeforeOrEqual(final ZonedDateTime left, final ZonedDateTime right) {
        return left.isBefore(right) || left.isEqual(right);
    }

    @Override
    public Iterable<Reservation> findAllByVehicle(final String vehicleId) {
        List<Reservation> reservations = new ArrayList<>();
        for (Reservation r : findAll()) {
            if (r.getVehicle().getId().equals(vehicleId)) {
                reservations.add(r);
            }
        }

        return reservations;
    }

    @Override
    public Optional<Reservation> findByOverlappingRentPeriod(final String vehicleId, final ZonedDateTime pickupDate, final ZonedDateTime returnDate) {
        final Iterable<Reservation> reservations = findAllByVehicle(vehicleId);
        final List<Reservation> filteredReservations = new ArrayList<>();
        reservations.forEach(r -> {
            if ((isAfterOrEqual(pickupDate, r.getPickupDate()) && isBeforeOrEqual(pickupDate, r.getReturnDate())) ||
                (isAfterOrEqual(returnDate, r.getPickupDate()) && isBeforeOrEqual(returnDate, r.getReturnDate()))) {
                filteredReservations.add(r);
            }
        });
        Reservation mostRecentReservation = null;
        for (final Reservation r : filteredReservations) {
            if (mostRecentReservation == null) {
                mostRecentReservation = r;
            } else {
                if (r.getReturnDate().isAfter(mostRecentReservation.getReturnDate())) {
                    mostRecentReservation = r;
                }
            }
        }

        return Optional.ofNullable(mostRecentReservation);
    }

}
