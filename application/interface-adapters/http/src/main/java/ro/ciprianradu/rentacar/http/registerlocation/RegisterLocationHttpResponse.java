package ro.ciprianradu.rentacar.http.registerlocation;

import ro.ciprianradu.rentacar.http.HttpResponse;
import ro.ciprianradu.rentacar.http.HttpStatus;
import ro.ciprianradu.rentacar.usecases.registerlocation.RegisterLocationOutputData;
import ro.ciprianradu.rentacar.usecases.registerlocation.RegisterLocationOutputPort;

/**
 * HTTP output port for the Register Location use case
 */
public class RegisterLocationHttpResponse implements RegisterLocationOutputPort {

    private HttpResponse httpResponse;

    @Override
    public void present(final RegisterLocationOutputData registerLocationOutputData) {
        if (registerLocationOutputData.isLocationRegistered()) {
            httpResponse = new HttpResponse(HttpStatus.CREATED);
        } else {
            httpResponse = new HttpResponse(HttpStatus.CONFLICT);
        }
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

}
