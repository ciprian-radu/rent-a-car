package ro.ciprianradu.rentacar.usecases.registerrenter;

/**
 * Defines the input interface exposed the {@link RegisterRenterUseCase}. It's the input boundary of
 * mentioned use case.
 */
public interface RegisterRenterInputPort {

    /**
     * Allows users of the system to register themselves as vehicle renters. Each Renter must have a
     * unique e-mail address. Using an existing e-mail will lead to a failed registration process.
     *
     * @param inputData the input data for this operation (assumed not <code>null</code> and with
     * non empty data)
     * @param outputPort the output port where the result of this operation will be presented
     * (assumed not <code>null</code>)
     */
    void registerRenter(RegisterRenterInputData inputData,
        final RegisterRenterOutputPort outputPort);

}
