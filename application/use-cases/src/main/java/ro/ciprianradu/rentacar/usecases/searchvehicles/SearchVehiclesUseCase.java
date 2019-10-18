package ro.ciprianradu.rentacar.usecases.searchvehicles;

import java.util.List;
import java.util.Optional;
import ro.ciprianradu.rentacar.entity.Reservation;
import ro.ciprianradu.rentacar.entity.Vehicle;
import ro.ciprianradu.rentacar.usecases.gateways.GatewaysAccessor;

/**
 * Allows users of the system to search for vehicles available for renting. For each kind (type,
 * brand and model) of available vehicle we will get one item.
 */
public class SearchVehiclesUseCase implements SearchVehiclesInputPort {

    private final GatewaysAccessor gatewaysAccessor;

    /**
     * Constructor
     *
     * @param gatewaysAccessor the Gateways Accessor (cannot be <code>null</code>)
     */
    public SearchVehiclesUseCase(final GatewaysAccessor gatewaysAccessor) {
        if (gatewaysAccessor == null) {
            throw new IllegalArgumentException("Gateways Accessor is mandatory!");
        }
        this.gatewaysAccessor = gatewaysAccessor;
    }

    @Override
    public void searchVehicles(final SearchVehiclesInputData inputData,
        final SearchVehiclesOutputPort outputPort) {
        final List<Vehicle> vehicles = gatewaysAccessor.getVehicleGateway()
            .search(null, null, null, null);

        final SearchVehiclesOutputData outputData = new SearchVehiclesOutputData();
        vehicles.stream().filter(v -> inputData.getPickupLocationName().equals(v.getLocation()))
            .forEach(v -> {
                final Optional<Reservation> overlappingReservationOptional = gatewaysAccessor
                    .getReservationGateway()
                    .findByOverlappingRentPeriod(v.getId(), inputData.getPickupDate(),
                        inputData.getReturnDate());
                if (!overlappingReservationOptional.isPresent()) {
                    outputData.add(v);
                }
            });
        outputPort.present(outputData);
    }

}
