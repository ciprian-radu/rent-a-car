package ro.ciprianradu.rentacar.usecases.registerlocation;

import java.util.Optional;
import ro.ciprianradu.rentacar.usecases.Context;
import ro.ciprianradu.rentacar.entity.Location;

/**
 * Allows users of the system to register locations from where rented vehicles may be picked up or returned.
 * Only one location with a given name may exist. Otherwise, the registration process will not succeed.
 */
public class RegisterLocationUseCase implements RegisterLocationInputPort {

    @Override
    public void registerLocation(final RegisterLocationInputData inputData, final RegisterLocationOutputPort outputPort) {
        Location location = new Location();
        location.setName(inputData.getName());
        location.setAddress(inputData.getAddress());

        boolean locationRegistered = false;
        final Optional<Location> locationOptional = Context.locationGateway.findByName(location.getName());
        if (!locationOptional.isPresent()) {
            Context.locationGateway.save(location);
            locationRegistered = true;
        }

        final RegisterLocationOutputData registerLocationOutputData = new RegisterLocationOutputData(locationRegistered);
        outputPort.present(registerLocationOutputData);
    }

}
