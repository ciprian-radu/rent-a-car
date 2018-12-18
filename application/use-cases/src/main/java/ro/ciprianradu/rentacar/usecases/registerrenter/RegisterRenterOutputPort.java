package ro.ciprianradu.rentacar.usecases.registerrenter;

/**
 * Defines the output interface exposed the {@link RegisterRenterUseCase}. It's the output boundary of mentioned use case.
 */
public interface RegisterRenterOutputPort {

    /**
     * Presents the result of the {@link RegisterRenterUseCase}
     *
     * @param registerRenterOutputData the output data of the {@link RegisterRenterUseCase} (cannot be <code>null</code>)
     */
    void present(RegisterRenterOutputData registerRenterOutputData);

}
