package ro.ciprianradu.rentacar.rest.controllers;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.ciprianradu.rentacar.http.HttpResponse;
import ro.ciprianradu.rentacar.http.dtos.Location;
import ro.ciprianradu.rentacar.http.registerlocation.RegisterLocationAdapter;
import ro.ciprianradu.rentacar.usecases.registerlocation.RegisterLocationInputPort;

/**
 * Exposes the Register Location Use Case as a REST API.
 */
@RestController
class RegisterLocationController {

    static final String ENDPOINT = "/locations";

    private RegisterLocationAdapter adapter;

    public RegisterLocationController(final RegisterLocationInputPort inputPort) {
        adapter = new RegisterLocationAdapter(inputPort);
    }

    @PostMapping(value = ENDPOINT)
    public ResponseEntity registerLocation(@RequestBody Location location) {
        ResponseEntity responseEntity;
        final HttpResponse httpResponse = adapter.registerLocation(location);
        switch (httpResponse.getStatus()) {
            case CREATED:
                responseEntity = ResponseEntity
                    .created(URI.create(ENDPOINT + "/" + location.getName())).body(location);
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
