package ro.ciprianradu.rentacar.usecases.registerrenter;

import java.util.Optional;
import ro.ciprianradu.rentacar.entity.Renter;
import ro.ciprianradu.rentacar.usecases.gateways.GatewaysAccessor;

/**
 * Allows users of the system to register themselves as vehicle renters. Each {@link Renter} must
 * have a unique e-mail address. Using an existing e-mail will lead to a failed registration
 * process.
 */
public class RegisterRenterUseCase implements RegisterRenterInputPort {

    private final GatewaysAccessor gatewaysAccessor;

    /**
     * Constructor
     *
     * @param gatewaysAccessor the Gateways Accessor (cannot be <code>null</code>)
     */
    public RegisterRenterUseCase(final GatewaysAccessor gatewaysAccessor) {
        if (gatewaysAccessor == null) {
            throw new IllegalArgumentException("Gateways Accessor is mandatory!");
        }
        this.gatewaysAccessor = gatewaysAccessor;
    }

    @Override
    public void registerRenter(final RegisterRenterInputData inputData,
        final RegisterRenterOutputPort outputPort) {
        Renter renter = new Renter();
        renter.setFirstName(inputData.getFirstName());
        renter.setLastName(inputData.getLastName());
        renter.setEmail(inputData.getEmail());
        renter.setTelephoneNumber(inputData.getTelephoneNumber());

        boolean renterRegistered = false;
        final Optional<Renter> optionalRenter = gatewaysAccessor.getRenterGateway()
            .findByEmail(renter.getEmail());
        if (!optionalRenter.isPresent()) {
            gatewaysAccessor.getRenterGateway().save(renter);
            renterRegistered = true;
        }

        final RegisterRenterOutputData registerRenterOutputData = new RegisterRenterOutputData(
            renterRegistered);
        outputPort.present(registerRenterOutputData);
    }

}
