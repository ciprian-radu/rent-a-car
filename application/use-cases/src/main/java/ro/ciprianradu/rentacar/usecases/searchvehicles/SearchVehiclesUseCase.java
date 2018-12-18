package ro.ciprianradu.rentacar.usecases.searchvehicles;

import java.util.List;
import java.util.Optional;
import ro.ciprianradu.rentacar.usecases.Context;
import ro.ciprianradu.rentacar.entity.Reservation;
import ro.ciprianradu.rentacar.entity.Vehicle;

/**
 * Allows users of the system to search for vehicles available for renting. For each kind (type, brand and model) of available vehicle we will get one item.
 */
public class SearchVehiclesUseCase implements SearchVehiclesInputPort {

    @Override
    public void searchVehicles(final SearchVehiclesInputData inputData, final SearchVehiclesOutputPort outputPort) {
        final List<Vehicle> vehicles = Context.vehicleGateway.search(null, null, null, null);

        final SearchVehiclesOutputData outputData = new SearchVehiclesOutputData();
        vehicles.stream().filter(v -> inputData.getPickupLocationName().equals(v.getLocation())).forEach(v -> {
            final Optional<Reservation> overlappingReservationOptional = Context.reservationGateway
                .findByOverlappingRentPeriod(v.getId(), inputData.getPickupDate(), inputData.getReturnDate());
            if (!overlappingReservationOptional.isPresent()) {
                outputData.add(v);
            }
        });
        outputPort.present(outputData);
    }

}
