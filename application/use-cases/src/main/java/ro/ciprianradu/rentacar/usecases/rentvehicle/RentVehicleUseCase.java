package ro.ciprianradu.rentacar.usecases.rentvehicle;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import ro.ciprianradu.rentacar.usecases.Context;
import ro.ciprianradu.rentacar.entity.Location;
import ro.ciprianradu.rentacar.entity.Renter;
import ro.ciprianradu.rentacar.entity.Reservation;
import ro.ciprianradu.rentacar.entity.Vehicle;

/**
 * Allows users of the system to rent an available vehicle, for a certain period of time.
 */
public class RentVehicleUseCase implements RentVehicleInputPort {

    private static Optional<Renter> findRenter(final String renterEmail) {
        return Context.renterGateway.findByEmail(renterEmail);
    }

    private static Optional<Vehicle> findVehicle(final String type, final String brand, final String model, final ZonedDateTime pickupDate,
        final ZonedDateTime returnDate) {
        Optional<Vehicle> vehicleOptional = Optional.empty();

        final List<Vehicle> vehicles = Context.vehicleGateway.search(type, brand, model, null);
        if (!vehicles.isEmpty()) {
            // We simply pick the first available vehicle from the given category.
            for (final Vehicle vehicle : vehicles) {
                final Optional<Reservation> overlappingReservationOptional = Context.reservationGateway
                    .findByOverlappingRentPeriod(vehicle.getId(), pickupDate, returnDate);
                if (!overlappingReservationOptional.isPresent()) {
                    vehicleOptional = Optional.of(vehicle);
                    break;
                }
            }
        }

        return vehicleOptional;
    }

    private static Optional<Location> findLocation(final String name) {
        return Context.locationGateway.findByName(name);
    }

    @Override
    public void rentVehicle(final RentVehicleInputData inputData, final RentVehicleOutputPort outputPort) {
        boolean rented = false;
        final Optional<Renter> renterOptional = findRenter(inputData.getRenterEmail());
        final Optional<Vehicle> vehicleOptional = findVehicle(inputData.getVehicleType(), inputData.getVehicleBrand(), inputData.getVehicleModel(),
            inputData.getPickupDate(), inputData.getReturnDate());
        final Optional<Location> pickupLocationOptional = findLocation(inputData.getPickupLocationName());
        final Optional<Location> returnLocationOptional = findLocation(inputData.getReturnLocationName());
        String rentId = null;
        if (renterOptional.isPresent() && vehicleOptional.isPresent() && pickupLocationOptional.isPresent() && returnLocationOptional.isPresent()) {
            Reservation reservation = new Reservation();
            reservation.setRenter(renterOptional.get());
            reservation.setPickupDate(inputData.getPickupDate());
            reservation.setReturnDate(inputData.getReturnDate());
            reservation.setVehicle(vehicleOptional.get());
            reservation.setPickupLocation(pickupLocationOptional.get());
            reservation.setReturnLocation(returnLocationOptional.get());
            Context.reservationGateway.save(reservation);
            rentId = reservation.getId();
            rented = true;
        }

        final RentVehicleOutputData outputData = new RentVehicleOutputData(rented, rentId);
        outputPort.present(outputData);
    }

}
