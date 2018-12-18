package ro.ciprianradu.rentacar.usecases.searchvehicles;

/**
 * Defines the input interface exposed the {@link SearchVehiclesUseCase}. It's the input boundary of mentioned use case.
 */
public interface SearchVehiclesOutputPort {

    /**
     * Presents the result of the {@link SearchVehiclesUseCase}
     *
     * @param outputData the output data of the {@link SearchVehiclesUseCase} (cannot be <code>null</code>)
     */
    void present(SearchVehiclesOutputData outputData);

}
