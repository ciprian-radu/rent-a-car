package ro.ciprianradu.rentacar.usecases.rentvehicle;

/**
 * Output data for the {@link RentVehicleUseCase}
 */
public class RentVehicleOutputData {

    private boolean vehicleRented;

    private String rentId;

    /**
     * Constructor
     *
     * @param vehicleRented whether or not the vehicle was rented
     * @param rentId the ID of the rent record (may be <code>null</code> if vehicle was not rented)
     */
    public RentVehicleOutputData(final boolean vehicleRented, final String rentId) {
        this.vehicleRented = vehicleRented;
        this.rentId = rentId;
    }

    public boolean isVehicleRented() {
        return vehicleRented;
    }

    public String getRentId() {
        return rentId;
    }
}
