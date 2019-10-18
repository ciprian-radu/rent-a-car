package ro.ciprianradu.rentacar.usecases.rentvehicle;

/**
 * Defines the input interface exposed the {@link RentVehicleUseCase}. It's the input boundary of
 * mentioned use case.
 */
public interface RentVehicleInputPort {

    /**
     * Allows users of the system to rent an available vehicle, for a certain period of time.
     *
     * @param inputData the input data for this operation (assumed not <code>null</code> and with
     * non empty data)
     * @param outputPort the output port where the result of this operation will be presented
     * (assumed not <code>null</code>)
     */
    void rentVehicle(RentVehicleInputData inputData, RentVehicleOutputPort outputPort);

}
