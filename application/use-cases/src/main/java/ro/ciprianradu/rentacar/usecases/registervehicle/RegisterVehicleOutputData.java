package ro.ciprianradu.rentacar.usecases.registervehicle;

/**
 * Output data of the {@link RegisterVehicleUseCase}
 */
public class RegisterVehicleOutputData {

    private boolean vehicleRegistered;

    public RegisterVehicleOutputData(final boolean vehicleRegistered) {
        this.vehicleRegistered = vehicleRegistered;
    }

    public boolean isVehicleRegistered() {
        return vehicleRegistered;
    }
}
