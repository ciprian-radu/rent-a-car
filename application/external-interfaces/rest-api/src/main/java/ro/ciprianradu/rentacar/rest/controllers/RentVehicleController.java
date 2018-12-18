package ro.ciprianradu.rentacar.rest.controllers;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.ciprianradu.rentacar.http.HttpResponse;
import ro.ciprianradu.rentacar.http.dtos.Reservation;
import ro.ciprianradu.rentacar.http.rentvehicle.RentVehicleAdapter;
import ro.ciprianradu.rentacar.usecases.rentvehicle.RentVehicleInputPort;

/**
 * Exposes the Rent Vehicle Use Case as a REST API.
 */
@RestController
class RentVehicleController {

    static final String ENDPOINT = "/reservations";

    private RentVehicleAdapter adapter;

    public RentVehicleController(final RentVehicleInputPort inputPort) {
        adapter = new RentVehicleAdapter(inputPort);
    }

    @PostMapping(value = ENDPOINT)
    public ResponseEntity<?> rentVehicle(@RequestBody Reservation reservation) {
        ResponseEntity responseEntity;
        final HttpResponse httpResponse = adapter.rentVehicle(reservation);
        switch (httpResponse.getStatus()) {
            case CREATED:
                reservation.setId(adapter.getRentId());
                responseEntity = ResponseEntity.created(URI.create(ENDPOINT + "/" + reservation.getId())).body(reservation);
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
