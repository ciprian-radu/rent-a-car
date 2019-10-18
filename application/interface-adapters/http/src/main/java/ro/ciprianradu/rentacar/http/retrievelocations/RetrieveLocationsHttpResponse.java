package ro.ciprianradu.rentacar.http.retrievelocations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ro.ciprianradu.rentacar.http.HttpResponse;
import ro.ciprianradu.rentacar.http.HttpStatus;
import ro.ciprianradu.rentacar.http.dtos.Location;
import ro.ciprianradu.rentacar.usecases.retrievelocations.RetrieveLocationsOutputData;
import ro.ciprianradu.rentacar.usecases.retrievelocations.RetrieveLocationsOutputPort;

/**
 * HTTP output port for the Retrieve Locations use case
 */
public class RetrieveLocationsHttpResponse implements RetrieveLocationsOutputPort {

    private HttpResponse httpResponse;

    private List<Location> locations = new ArrayList<>();

    @Override
    public void present(final RetrieveLocationsOutputData retrieveLocationsOutputData) {
        final List<RetrieveLocationsOutputData.Location> locationsList = retrieveLocationsOutputData
            .getLocations();
        this.locations.clear();
        locationsList.forEach(this::addLocation);
        httpResponse = new HttpResponse(HttpStatus.OK);
    }

    private void addLocation(final RetrieveLocationsOutputData.Location l) {
        final Location location = new Location();
        location.setName(l.getName());
        location.setAddress(l.getAddress());
        locations.add(location);
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

    /**
     * @return all the available locations
     */
    public List<Location> getLocations() {
        return Collections.unmodifiableList(locations);
    }
}
