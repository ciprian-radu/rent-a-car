package ro.ciprianradu.rentacar.http.registerlocation;

import ro.ciprianradu.rentacar.http.HttpResponse;
import ro.ciprianradu.rentacar.http.HttpStatus;
import ro.ciprianradu.rentacar.http.dtos.Location;
import ro.ciprianradu.rentacar.usecases.registerlocation.RegisterLocationInputData;
import ro.ciprianradu.rentacar.usecases.registerlocation.RegisterLocationInputPort;

/**
 * HTTP adapter for the Register location use case
 */
public class RegisterLocationAdapter {

    private RegisterLocationInputPort inputPort;

    private RegisterLocationHttpResponse outputPort = new RegisterLocationHttpResponse();

    /**
     * Constructor
     *
     * @param inputPort the input port (cannot be <code>null</code>)
     */
    public RegisterLocationAdapter(final RegisterLocationInputPort inputPort) {
        if (inputPort == null) {
            throw new IllegalArgumentException("Input port must be given!");
        }
        this.inputPort = inputPort;
    }

    public HttpResponse registerLocation(final Location location) {
        HttpResponse httpResponse;

        if (isValidInput(location)) {
            final RegisterLocationInputData registerLocationInputData = buildInputData(location);
            inputPort.registerLocation(registerLocationInputData, outputPort);
            httpResponse = outputPort.getHttpResponse();
        } else {
            httpResponse = new HttpResponse(HttpStatus.BAD_REQUEST, "Invalid input data!");
        }

        return httpResponse;
    }

    private boolean isValidInput(final Location location) {
        return location != null && location.getName() != null;
    }

    private RegisterLocationInputData buildInputData(final Location location) {
        return new RegisterLocationInputData(location.getName(), location.getAddress());
    }

}
