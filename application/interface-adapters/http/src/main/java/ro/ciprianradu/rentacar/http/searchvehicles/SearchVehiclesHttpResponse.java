package ro.ciprianradu.rentacar.http.searchvehicles;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import ro.ciprianradu.rentacar.http.HttpResponse;
import ro.ciprianradu.rentacar.http.HttpStatus;
import ro.ciprianradu.rentacar.usecases.searchvehicles.SearchVehiclesOutputData;
import ro.ciprianradu.rentacar.usecases.searchvehicles.SearchVehiclesOutputData.VehicleCategory;
import ro.ciprianradu.rentacar.usecases.searchvehicles.SearchVehiclesOutputPort;

/**
 * HTTP output port for the Search Vehicles use case
 */
public class SearchVehiclesHttpResponse implements SearchVehiclesOutputPort {

    private HttpResponse httpResponse;

    private Set<ro.ciprianradu.rentacar.http.dtos.VehicleCategory> vehicleCategories = new HashSet<>();

    @Override
    public void present(final SearchVehiclesOutputData searchVehiclesOutputData) {
        final Set<VehicleCategory> vehicleCategories = searchVehiclesOutputData.getVehicleCategories();
        this.vehicleCategories.clear();
        vehicleCategories.forEach(this::addVehicle);
        httpResponse = new HttpResponse(HttpStatus.OK);
    }

    private void addVehicle(final VehicleCategory v) {
        final ro.ciprianradu.rentacar.http.dtos.VehicleCategory vehicleCategory = new ro.ciprianradu.rentacar.http.dtos.VehicleCategory();
        vehicleCategory.setType(v.getType());
        vehicleCategory.setBrand(v.getBrand());
        vehicleCategory.setModel(v.getModel());
        vehicleCategory.setRate(v.getRate());
        vehicleCategory.setLocation(v.getLocation());
        this.vehicleCategories.add(vehicleCategory);
    }

    void setHttpResponse(final HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

    /**
     * @return the vehicleCategories that match the search criteria
     */
    public Set<ro.ciprianradu.rentacar.http.dtos.VehicleCategory> getVehicleCategories() {
        return Collections.unmodifiableSet(vehicleCategories);
    }
}
