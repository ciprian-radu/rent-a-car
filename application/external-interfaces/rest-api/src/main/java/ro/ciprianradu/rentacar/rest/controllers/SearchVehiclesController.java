package ro.ciprianradu.rentacar.rest.controllers;

import java.time.ZonedDateTime;
import java.util.Set;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.ciprianradu.rentacar.http.HttpResponse;
import ro.ciprianradu.rentacar.http.dtos.VehicleCategory;
import ro.ciprianradu.rentacar.http.searchvehicles.SearchVehiclesAdapter;
import ro.ciprianradu.rentacar.http.searchvehicles.SearchVehiclesHttpResponse;
import ro.ciprianradu.rentacar.usecases.searchvehicles.SearchVehiclesInputPort;

/**
 * Exposes the Search Vehicles Use Case as a REST API.
 */
@RestController
class SearchVehiclesController {

    static final String ENDPOINT = "/vehicles";

    private SearchVehiclesAdapter adapter;

    public SearchVehiclesController(final SearchVehiclesInputPort inputPort) {
        adapter = new SearchVehiclesAdapter(inputPort);
    }

    @GetMapping(value = ENDPOINT)
    public ResponseEntity<?> searchVehicles(@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime pickupDate, @RequestParam String pickupLocation,
        @RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime returnDate, @RequestParam String returnLocation) {
        ResponseEntity responseEntity;
        final SearchVehiclesHttpResponse searchVehiclesHttpResponse = adapter.searchVehicles(pickupDate, pickupLocation, returnDate, returnLocation);
        final HttpResponse httpResponse = searchVehiclesHttpResponse.getHttpResponse();
        final Set<VehicleCategory> vehicleCategories = searchVehiclesHttpResponse.getVehicleCategories();
        switch (httpResponse.getStatus()) {
            case OK:
                responseEntity = ResponseEntity.ok(vehicleCategories);
                break;
            default:
                responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(httpResponse);
        }

        return responseEntity;
    }

}
