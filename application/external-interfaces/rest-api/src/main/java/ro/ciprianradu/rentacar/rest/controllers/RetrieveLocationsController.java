package ro.ciprianradu.rentacar.rest.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.ciprianradu.rentacar.http.dtos.Location;
import ro.ciprianradu.rentacar.http.retrievelocations.RetrieveLocationsAdapter;
import ro.ciprianradu.rentacar.http.retrievelocations.RetrieveLocationsHttpResponse;
import ro.ciprianradu.rentacar.usecases.retrievelocations.RetrieveLocationsInputPort;

/**
 * Exposes the Retrieve Locations Use Case as a REST API.
 */
@RestController
class RetrieveLocationsController {

    static final String ENDPOINT = "/locations";

    private RetrieveLocationsAdapter adapter;

    public RetrieveLocationsController(final RetrieveLocationsInputPort inputPort) {
        adapter = new RetrieveLocationsAdapter(inputPort);
    }

    @GetMapping(value = ENDPOINT)
    public ResponseEntity<?> retrieveLocations() {
        ResponseEntity responseEntity;
        final RetrieveLocationsHttpResponse retrieveLocationsHttpResponse = adapter.retrieveLocations();
        final List<Location> locations = retrieveLocationsHttpResponse.getLocations();

        return ResponseEntity.ok(locations);
    }

}
