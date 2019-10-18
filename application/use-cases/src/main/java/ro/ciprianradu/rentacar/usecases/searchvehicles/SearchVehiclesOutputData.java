package ro.ciprianradu.rentacar.usecases.searchvehicles;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import ro.ciprianradu.rentacar.entity.Vehicle;

/**
 * Output data for the {@link SearchVehiclesUseCase}
 */
public class SearchVehiclesOutputData {

    private Set<VehicleCategory> vehicleCategories = new HashSet<>();

    void add(Vehicle vehicle) {
        final VehicleCategory v = new VehicleCategory();
        v.setBrand(vehicle.getBrand().getName());
        v.setModel(vehicle.getModel().getName());
        v.setType(vehicle.getType().getType());
        v.setRate(vehicle.getRate());
        v.setLocation(vehicle.getLocation());
        vehicleCategories.add(v);
    }

    /**
     * @return the vehicles that match the given criteria (may be empty but never <code>null</code>)
     */
    public Set<VehicleCategory> getVehicleCategories() {
        return Collections.unmodifiableSet(vehicleCategories);
    }

    public class VehicleCategory {

        private String type;

        private String brand;

        private String model;

        private BigDecimal rate;

        private String location;

        public String getType() {
            return type;
        }

        public void setType(final String type) {
            this.type = type;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(final String brand) {
            this.brand = brand;
        }

        public String getModel() {
            return model;
        }

        public void setModel(final String model) {
            this.model = model;
        }

        public BigDecimal getRate() {
            return rate;
        }

        public void setRate(final BigDecimal rate) {
            this.rate = rate;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(final String location) {
            this.location = location;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof VehicleCategory)) {
                return false;
            }
            final VehicleCategory vehicleCategory = (VehicleCategory) o;
            return Objects.equals(getType(), vehicleCategory.getType()) && Objects
                .equals(getBrand(), vehicleCategory.getBrand()) && Objects
                .equals(getModel(), vehicleCategory.getModel()) && Objects
                .equals(getRate(), vehicleCategory.getRate()) && Objects
                .equals(getLocation(), vehicleCategory.getLocation());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getType(), getBrand(), getModel(), getRate(), getLocation());
        }
    }

}
