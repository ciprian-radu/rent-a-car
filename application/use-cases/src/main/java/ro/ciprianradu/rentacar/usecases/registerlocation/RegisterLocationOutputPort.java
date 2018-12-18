package ro.ciprianradu.rentacar.usecases.registerlocation;

/**
 * Defines the output interface exposed the {@link RegisterLocationUseCase}. It's the output boundary of mentioned use case.
 */
public interface RegisterLocationOutputPort {

    /**
     * Presents the result of the {@link RegisterLocationUseCase}
     *
     * @param registerLocationOutputData the output data of the {@link RegisterLocationUseCase} (cannot be <code>null</code>)
     */
    void present(RegisterLocationOutputData registerLocationOutputData);

}
