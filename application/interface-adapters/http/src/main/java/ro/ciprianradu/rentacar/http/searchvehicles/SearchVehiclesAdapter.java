package ro.ciprianradu.rentacar.http.searchvehicles;

import java.time.ZonedDateTime;
import ro.ciprianradu.rentacar.http.HttpResponse;
import ro.ciprianradu.rentacar.http.HttpStatus;
import ro.ciprianradu.rentacar.usecases.searchvehicles.SearchVehiclesInputData;
import ro.ciprianradu.rentacar.usecases.searchvehicles.SearchVehiclesInputPort;

/**
 * HTTP adapter for the Search Vehicles use case
 */
public class SearchVehiclesAdapter {

    private SearchVehiclesInputPort inputPort;

    private SearchVehiclesHttpResponse outputPort = new SearchVehiclesHttpResponse();

    /**
     * Constructor
     *
     * @param inputPort the input port (cannot be <code>null</code>)
     */
    public SearchVehiclesAdapter(final SearchVehiclesInputPort inputPort) {
        if (inputPort == null) {
            throw new IllegalArgumentException("Input port must be given!");
        }
        this.inputPort = inputPort;
    }

    private static boolean isValidInput(final ZonedDateTime pickupDate, final String pickupLocation, final ZonedDateTime returnDate,
        final String returnLocation) {
        boolean isValid = true;

        if (pickupDate == null || pickupLocation == null || pickupLocation.isEmpty() || returnDate == null || returnLocation == null || returnLocation
            .isEmpty()) {
            isValid = false;
        }

        return isValid;
    }

    /**
     * Search all vehicle types (type, brand, model) that are available for renting within the specified time frame and from the given (pickup) location.
     *
     * @param pickupDate the pickup date (cannot be <code>null</code>)
     * @param pickupLocation the pickup location (cannot be <code>null</code> nor empty)
     * @param returnDate the return date (cannot be <code>null</code>)
     * @param returnLocation the return location (cannot be <code>null</code> nor empty)
     * @return the result of the search
     */
    public SearchVehiclesHttpResponse searchVehicles(final ZonedDateTime pickupDate, final String pickupLocation, final ZonedDateTime returnDate,
        final String returnLocation) {
        HttpResponse httpResponse;

        if (isValidInput(pickupDate, pickupLocation, returnDate, returnLocation)) {
            final SearchVehiclesInputData searchVehiclesInputData = new SearchVehiclesInputData(pickupDate, pickupLocation, returnDate, returnLocation);
            inputPort.searchVehicles(searchVehiclesInputData, outputPort);
        } else {
            outputPort.setHttpResponse(new HttpResponse(HttpStatus.BAD_REQUEST, "Invalid input data!"));
        }

        return outputPort;
    }

}
