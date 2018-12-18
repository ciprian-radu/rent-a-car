package ro.ciprianradu.rentacar.usecases.registerlocation;

/**
 * Defines the input interface exposed the {@link RegisterLocationUseCase}. It's the input boundary of mentioned use case.
 */
public interface RegisterLocationInputPort {

    /**
     * Allows users of the system to register locations from where rented vehicles may be picked up or returned.
     * Only one location with a given name may exist. Otherwise, the registration process will not succeed.
     *
     * @param inputData the input data for this operation (assumed not <code>null</code> and with non empty data)
     * @param outputPort the output port where the result of this operation will be presented (assumed not <code>null</code>)
     */
    void registerLocation(RegisterLocationInputData inputData, RegisterLocationOutputPort outputPort);

}
