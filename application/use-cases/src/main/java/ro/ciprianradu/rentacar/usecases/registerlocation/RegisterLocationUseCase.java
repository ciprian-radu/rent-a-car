package ro.ciprianradu.rentacar.usecases.registerlocation;

import java.util.Optional;
import ro.ciprianradu.rentacar.entity.Location;
import ro.ciprianradu.rentacar.usecases.gateways.GatewaysAccessor;

/**
 * Allows users of the system to register locations from where rented vehicles may be picked up or
 * returned. Only one location with a given name may exist. Otherwise, the registration process will
 * not succeed.
 */
public class RegisterLocationUseCase implements RegisterLocationInputPort {

    private final GatewaysAccessor gatewaysAccessor;

    /**
     * Constructor
     *
     * @param gatewaysAccessor the Gateways Accessor (cannot be <code>null</code>)
     */
    public RegisterLocationUseCase(final GatewaysAccessor gatewaysAccessor) {
        if (gatewaysAccessor == null) {
            throw new IllegalArgumentException("Gateways Accessor is mandatory!");
        }
        this.gatewaysAccessor = gatewaysAccessor;
    }

    @Override
    public void registerLocation(final RegisterLocationInputData inputData,
        final RegisterLocationOutputPort outputPort) {
        Location location = new Location();
        location.setName(inputData.getName());
        location.setAddress(inputData.getAddress());

        boolean locationRegistered = false;
        final Optional<Location> locationOptional = gatewaysAccessor.getLocationGateway()
            .findByName(location.getName());
        if (!locationOptional.isPresent()) {
            gatewaysAccessor.getLocationGateway().save(location);
            locationRegistered = true;
        }

        final RegisterLocationOutputData registerLocationOutputData = new RegisterLocationOutputData(
            locationRegistered);
        outputPort.present(registerLocationOutputData);
    }

}
