package ro.ciprianradu.rentacar.http.registerrenter;

import ro.ciprianradu.rentacar.http.HttpResponse;
import ro.ciprianradu.rentacar.http.HttpStatus;
import ro.ciprianradu.rentacar.usecases.registerrenter.RegisterRenterOutputData;
import ro.ciprianradu.rentacar.usecases.registerrenter.RegisterRenterOutputPort;

/**
 * HTTP output port for the Register Renter use case
 */
public class RegisterRenterHttpResponse implements RegisterRenterOutputPort {

    private HttpResponse httpResponse;

    @Override
    public void present(final RegisterRenterOutputData registerRenterOutputData) {
        if (registerRenterOutputData.isRenterRegistered()) {
            httpResponse = new HttpResponse(HttpStatus.CREATED);
        } else {
            httpResponse = new HttpResponse(HttpStatus.CONFLICT);
        }
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

}
