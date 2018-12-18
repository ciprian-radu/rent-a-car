package ro.ciprianradu.rentacar.usecases.registerrenter;

import java.util.Optional;
import ro.ciprianradu.rentacar.usecases.Context;
import ro.ciprianradu.rentacar.entity.Renter;

/**
 * Allows users of the system to register themselves as vehicle renters. Each {@link Renter} must have a unique e-mail address. Using an existing e-mail will
 * lead to a failed registration process.
 */
public class RegisterRenterUseCase implements RegisterRenterInputPort {

    @Override
    public void registerRenter(final RegisterRenterInputData inputData, final RegisterRenterOutputPort outputPort) {
        Renter renter = new Renter();
        renter.setFirstName(inputData.getFirstName());
        renter.setLastName(inputData.getLastName());
        renter.setEmail(inputData.getEmail());
        renter.setTelephoneNumber(inputData.getTelephoneNumber());

        boolean renterRegistered = false;
        final Optional<Renter> optionalRenter = Context.renterGateway.findByEmail(renter.getEmail());
        if (!optionalRenter.isPresent()) {
            Context.renterGateway.save(renter);
            renterRegistered = true;
        }

        final RegisterRenterOutputData registerRenterOutputData = new RegisterRenterOutputData(renterRegistered);
        outputPort.present(registerRenterOutputData);
    }

}
