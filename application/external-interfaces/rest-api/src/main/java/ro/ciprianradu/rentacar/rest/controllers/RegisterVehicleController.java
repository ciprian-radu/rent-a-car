package ro.ciprianradu.rentacar.rest.controllers;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.ciprianradu.rentacar.http.HttpResponse;
import ro.ciprianradu.rentacar.http.dtos.Vehicle;
import ro.ciprianradu.rentacar.http.registervehicle.RegisterVehicleAdapter;
import ro.ciprianradu.rentacar.usecases.registervehicle.RegisterVehicleInputPort;

/**
 * Exposes the Register Vehicle Use Case as a REST API.
 */
@RestController
class RegisterVehicleController {

    static final String ENDPOINT = "/vehicles";

    private RegisterVehicleAdapter adapter;

    public RegisterVehicleController(final RegisterVehicleInputPort inputPort) {
        adapter = new RegisterVehicleAdapter(inputPort);
    }

    @PostMapping(value = ENDPOINT)
    public ResponseEntity<?> registerVehicle(@RequestBody Vehicle vehicle) {
        ResponseEntity responseEntity;
        final HttpResponse httpResponse = adapter.registerVehicle(vehicle);
        switch (httpResponse.getStatus()) {
            case CREATED:
                responseEntity = ResponseEntity.created(URI.create(ENDPOINT + "/" + vehicle.getId())).body(vehicle);
                break;
            case CONFLICT:
                responseEntity = ResponseEntity.status(HttpStatus.CONFLICT).build();
                break;
            case BAD_REQUEST:
                responseEntity = ResponseEntity.badRequest().body(httpResponse);
                break;
            default:
                responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(httpResponse);
        }

        return responseEntity;
    }

}
