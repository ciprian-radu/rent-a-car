package ro.ciprianradu.rentacar.usecases.retrievelocations;

/**
 * Defines the output interface exposed the {@link RetrieveLocationsUseCase}. It's the output
 * boundary of mentioned use case.
 */
public interface RetrieveLocationsOutputPort {

    /**
     * Presents the result of the {@link RetrieveLocationsUseCase}
     *
     * @param retrieveLocationsOutputData the output data of the {@link RetrieveLocationsUseCase}
     * (cannot be <code>null</code>)
     */
    void present(RetrieveLocationsOutputData retrieveLocationsOutputData);

}
