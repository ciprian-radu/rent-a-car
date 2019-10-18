package ro.ciprianradu.rentacar.usecases.registervehicle;

/**
 * Defines the input interface exposed the {@link RegisterVehicleUseCase}. It's the input boundary
 * of mentioned use case.
 */
public interface RegisterVehicleInputPort {

    /**
     * Allows users of the system to register vehicles which may be rented.
     *
     * @param inputData the input data for this operation (assumed not <code>null</code> and with
     * non empty data)
     * @param outputPort the output port where the result of this operation will be presented
     * (assumed not <code>null</code>)
     */
    void registerVehicle(RegisterVehicleInputData inputData, RegisterVehicleOutputPort outputPort);

}
