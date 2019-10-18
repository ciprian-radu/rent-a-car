package ro.ciprianradu.rentacar.rest.controllers;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.ciprianradu.rentacar.http.HttpResponse;
import ro.ciprianradu.rentacar.http.dtos.Renter;
import ro.ciprianradu.rentacar.http.registerrenter.RegisterRenterAdapter;
import ro.ciprianradu.rentacar.usecases.registerrenter.RegisterRenterInputPort;

/**
 * Exposes the Register Renter Use Case as a REST API.
 */
@RestController
class RegisterRenterController {

    static final String ENDPOINT = "/renters";

    private RegisterRenterAdapter adapter;

    public RegisterRenterController(final RegisterRenterInputPort inputPort) {
        adapter = new RegisterRenterAdapter(inputPort);
    }

    @PostMapping(value = ENDPOINT)
    public ResponseEntity registerRenter(@RequestBody Renter renter) {
        ResponseEntity responseEntity;
        final HttpResponse httpResponse = adapter.registerRenter(renter);
        switch (httpResponse.getStatus()) {
            case CREATED:
                responseEntity = ResponseEntity
                    .created(URI.create(ENDPOINT + "/" + renter.getEmail())).body(renter);
                break;
            case CONFLICT:
                responseEntity = ResponseEntity.status(HttpStatus.CONFLICT).build();
                break;
            case BAD_REQUEST:
                responseEntity = ResponseEntity.badRequest().body(httpResponse);
                break;
            default:
                responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(httpResponse);
        }

        return responseEntity;
    }

}
