package ro.ciprianradu.rentacar.usecases.retrievelocations;

import java.util.List;
import ro.ciprianradu.rentacar.usecases.Context;
import ro.ciprianradu.rentacar.entity.Location;

/**
 * Allows users of the system to retrieve locations from where rented vehicles may be picked up or returned.
 */
public class RetrieveLocationsUseCase implements RetrieveLocationsInputPort {

    @Override
    public void retrieveLocations(final RetrieveLocationsInputData inputData, final RetrieveLocationsOutputPort outputPort) {
        final Iterable<Location> locations = Context.locationGateway.findAll();

        final RetrieveLocationsOutputData retrieveLocationsOutputData = new RetrieveLocationsOutputData();
        final List<RetrieveLocationsOutputData.Location> outputLocations = retrieveLocationsOutputData.getLocations();
        locations.forEach(l -> {
            final RetrieveLocationsOutputData.Location outputLocation = retrieveLocationsOutputData.new Location();
            outputLocation.setName(l.getName());
            outputLocation.setAddress(l.getAddress());
            outputLocations.add(outputLocation);
        });

        outputPort.present(retrieveLocationsOutputData);
    }

}
