package ro.ciprianradu.rentacar.usecases.searchvehicles;

/**
 * Defines the input interface exposed the {@link SearchVehiclesUseCase}. It's the input boundary of
 * mentioned use case.
 */
public interface SearchVehiclesInputPort {

    /**
     * Allows users of the system to search for vehicles available for renting, at the moment of the
     * search.
     *
     * @param inputData the input data for this operation (assumed not <code>null</code>)
     * @param outputPort the output port where the result of this operation will be presented
     * (assumed not <code>null</code>)
     */
    void searchVehicles(SearchVehiclesInputData inputData, SearchVehiclesOutputPort outputPort);

}
