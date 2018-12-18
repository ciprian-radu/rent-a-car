package ro.ciprianradu.rentacar.http.rentvehicle;

import ro.ciprianradu.rentacar.http.HttpResponse;
import ro.ciprianradu.rentacar.http.HttpStatus;
import ro.ciprianradu.rentacar.usecases.rentvehicle.RentVehicleOutputData;
import ro.ciprianradu.rentacar.usecases.rentvehicle.RentVehicleOutputPort;

/**
 * HTTP output port for the Rent Vehicle use case
 */
public class RentVehicleHttpResponse implements RentVehicleOutputPort {

    private HttpResponse httpResponse;

    private String rentId;

    @Override
    public void present(final RentVehicleOutputData rentVehicleOutputData) {
        if (rentVehicleOutputData.isVehicleRented()) {
            httpResponse = new HttpResponse(HttpStatus.CREATED);
            rentId = rentVehicleOutputData.getRentId();
        } else {
            httpResponse = new HttpResponse(HttpStatus.CONFLICT);
        }
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

    public String getRentId() {
        return rentId;
    }

}
