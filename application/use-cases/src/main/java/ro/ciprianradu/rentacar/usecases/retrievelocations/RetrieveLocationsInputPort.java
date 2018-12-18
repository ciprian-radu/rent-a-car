package ro.ciprianradu.rentacar.usecases.retrievelocations;

/**
 * Defines the input interface exposed the {@link RetrieveLocationsUseCase}. It's the input boundary of mentioned use case.
 */
public interface RetrieveLocationsInputPort {

    /**
     * Allows users of the system to retrieve all the locations from where rented vehicles may be picked up or returned.
     *
     * @param inputData the input data for this operation (assumed not <code>null</code> and with non empty data)
     * @param outputPort the output port where the result of this operation will be presented (assumed not <code>null</code>)
     */
    void retrieveLocations(RetrieveLocationsInputData inputData, RetrieveLocationsOutputPort outputPort);

}
