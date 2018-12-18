package ro.ciprianradu.rentacar.usecases.registervehicle;

/**
 * Defines the output interface exposed the {@link RegisterVehicleUseCase}. It's the output boundary of mentioned use case.
 */
public interface RegisterVehicleOutputPort {

    /**
     * Presents the result of the {@link RegisterVehicleUseCase}
     *
     * @param registerVehicleOutputData the output data of the {@link RegisterVehicleUseCase} (cannot be <code>null</code>)
     */
    void present(RegisterVehicleOutputData registerVehicleOutputData);

}
