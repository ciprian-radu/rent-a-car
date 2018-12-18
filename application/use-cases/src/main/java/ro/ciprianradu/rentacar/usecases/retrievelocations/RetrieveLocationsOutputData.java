package ro.ciprianradu.rentacar.usecases.retrievelocations;

import java.util.ArrayList;
import java.util.List;

/**
 * Output data of the {@link RetrieveLocationsUseCase}
 */
public class RetrieveLocationsOutputData {

    private List<Location> locations = new ArrayList<>();

    /**
     * @return all the available rent locations
     */
    public List<Location> getLocations() {
        return locations;
    }

    public class Location {

        private String name;

        private String address;

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(final String address) {
            this.address = address;
        }
    }

}
