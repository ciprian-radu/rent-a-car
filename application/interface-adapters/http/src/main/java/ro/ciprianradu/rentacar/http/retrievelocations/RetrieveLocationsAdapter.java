package ro.ciprianradu.rentacar.http.retrievelocations;

import ro.ciprianradu.rentacar.usecases.retrievelocations.RetrieveLocationsInputData;
import ro.ciprianradu.rentacar.usecases.retrievelocations.RetrieveLocationsInputPort;

/**
 * HTTP adapter for the Retrieve Locations use case
 */
public class RetrieveLocationsAdapter {

    private RetrieveLocationsInputPort inputPort;

    private RetrieveLocationsHttpResponse outputPort = new RetrieveLocationsHttpResponse();

    /**
     * Constructor
     *
     * @param inputPort the input port (cannot be <code>null</code>)
     */
    public RetrieveLocationsAdapter(final RetrieveLocationsInputPort inputPort) {
        if (inputPort == null) {
            throw new IllegalArgumentException("Input port must be given!");
        }
        this.inputPort = inputPort;
    }

    public RetrieveLocationsHttpResponse retrieveLocations() {
        final RetrieveLocationsInputData registerLocationInputData = buildInputData();
        inputPort.retrieveLocations(registerLocationInputData, outputPort);

        return outputPort;
    }

    private RetrieveLocationsInputData buildInputData() {
        return new RetrieveLocationsInputData();
    }

}
