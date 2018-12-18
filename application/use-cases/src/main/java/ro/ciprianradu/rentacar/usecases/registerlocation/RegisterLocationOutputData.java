package ro.ciprianradu.rentacar.usecases.registerlocation;

/**
 * Output data of the {@link RegisterLocationUseCase}
 */
public class RegisterLocationOutputData {

    private boolean locationRegistered;

    public RegisterLocationOutputData(final boolean locationRegistered) {
        this.locationRegistered = locationRegistered;
    }

    public boolean isLocationRegistered() {
        return locationRegistered;
    }
}
