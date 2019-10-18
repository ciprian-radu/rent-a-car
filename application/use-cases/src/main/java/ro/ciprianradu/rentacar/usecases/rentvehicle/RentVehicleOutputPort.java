package ro.ciprianradu.rentacar.usecases.rentvehicle;

/**
 * Defines the output interface exposed the {@link RentVehicleUseCase}. It's the output boundary of
 * mentioned use case.
 */
public interface RentVehicleOutputPort {

    /**
     * Presents the result of the {@link RentVehicleUseCase}
     *
     * @param outputData the output data of the {@link RentVehicleUseCase} (cannot be
     * <code>null</code>)
     */
    void present(RentVehicleOutputData outputData);

}
