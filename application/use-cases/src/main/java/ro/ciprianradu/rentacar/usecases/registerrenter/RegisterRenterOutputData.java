package ro.ciprianradu.rentacar.usecases.registerrenter;

/**
 * Output data of the {@link RegisterRenterUseCase}
 */
public class RegisterRenterOutputData {

    private boolean renterRegistered;

    public RegisterRenterOutputData(final boolean renterRegistered) {
        this.renterRegistered = renterRegistered;
    }

    public boolean isRenterRegistered() {
        return renterRegistered;
    }

}
