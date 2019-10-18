package ro.ciprianradu.rentacar.usecases.retrievelocations;

import java.util.List;
import ro.ciprianradu.rentacar.entity.Location;
import ro.ciprianradu.rentacar.usecases.gateways.GatewaysAccessor;

/**
 * Allows users of the system to retrieve locations from where rented vehicles may be picked up or
 * returned.
 */
public class RetrieveLocationsUseCase implements RetrieveLocationsInputPort {

    private final GatewaysAccessor gatewaysAccessor;

    /**
     * Constructor
     *
     * @param gatewaysAccessor the Gateways Accessor (cannot be <code>null</code>)
     */
    public RetrieveLocationsUseCase(final GatewaysAccessor gatewaysAccessor) {
        if (gatewaysAccessor == null) {
            throw new IllegalArgumentException("Gateways Accessor is mandatory!");
        }
        this.gatewaysAccessor = gatewaysAccessor;
    }

    @Override
    public void retrieveLocations(final RetrieveLocationsInputData inputData,
        final RetrieveLocationsOutputPort outputPort) {
        final Iterable<Location> locations = gatewaysAccessor.getLocationGateway().findAll();

        final RetrieveLocationsOutputData retrieveLocationsOutputData = new RetrieveLocationsOutputData();
        final List<RetrieveLocationsOutputData.Location> outputLocations = retrieveLocationsOutputData
            .getLocations();
        locations.forEach(l -> {
            final RetrieveLocationsOutputData.Location outputLocation = retrieveLocationsOutputData.new Location();
            outputLocation.setName(l.getName());
            outputLocation.setAddress(l.getAddress());
            outputLocations.add(outputLocation);
        });

        outputPort.present(retrieveLocationsOutputData);
    }

}
