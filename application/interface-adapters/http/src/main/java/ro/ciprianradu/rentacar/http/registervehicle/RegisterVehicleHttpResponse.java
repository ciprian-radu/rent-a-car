package ro.ciprianradu.rentacar.http.registervehicle;

import ro.ciprianradu.rentacar.http.HttpResponse;
import ro.ciprianradu.rentacar.http.HttpStatus;
import ro.ciprianradu.rentacar.usecases.registervehicle.RegisterVehicleOutputData;
import ro.ciprianradu.rentacar.usecases.registervehicle.RegisterVehicleOutputPort;

/**
 * HTTP output port for the Register Vehicle use case
 */
public class RegisterVehicleHttpResponse implements RegisterVehicleOutputPort {

    private HttpResponse httpResponse;

    @Override
    public void present(final RegisterVehicleOutputData registerVehicleOutputData) {
        if (registerVehicleOutputData.isVehicleRegistered()) {
            httpResponse = new HttpResponse(HttpStatus.CREATED);
        } else {
            httpResponse = new HttpResponse(HttpStatus.CONFLICT);
        }
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

}
