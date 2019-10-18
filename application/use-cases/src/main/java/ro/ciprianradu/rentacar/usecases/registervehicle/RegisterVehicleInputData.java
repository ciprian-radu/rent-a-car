package ro.ciprianradu.rentacar.usecases.registervehicle;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Input data for the {@link RegisterVehicleUseCase}
 */
public class RegisterVehicleInputData {

    private String id;

    private String type;

    private String brand;

    private String model;

    private BigDecimal rate;

    private String location;

    /**
     * Constructor
     *
     * @param id the ID (license plate number) of this vehicle (must not be empty nor
     * <code>null</code>)
     * @param type the type of this vehicle (must not be empty nor <code>null</code>)
     * @param brand the brand of this vehicle (must not be empty nor <code>null</code>)
     * @param model the model of this vehicle (must not be empty nor <code>null</code>)
     * @param rate how much it costs (in EURO) to reserve this vehicle for one day (must be
     * positive)
     * @param location the name of the location to which this vehicle is associated to (must not be
     * empty nor <code>null</code>). This is the pickup location. A vehicle may be returned to any
     * location.
     */
    public RegisterVehicleInputData(final String id, final String type, final String brand,
        final String model, final BigDecimal rate, final String location) {
        if (Objects.isNull(id) || id.isEmpty()) {
            throw new IllegalArgumentException("Vehicle ID is mandatory!");
        }
        if (Objects.isNull(type) || type.isEmpty()) {
            throw new IllegalArgumentException("Vehicle type is mandatory!");
        }
        if (Objects.isNull(brand) || brand.isEmpty()) {
            throw new IllegalArgumentException("Vehicle brand is mandatory!");
        }
        if (Objects.isNull(model) || model.isEmpty()) {
            throw new IllegalArgumentException("Vehicle model is mandatory!");
        }
        if (Objects.isNull(rate) || rate.signum() <= 0) {
            throw new IllegalArgumentException("Rate must be positive!");
        }
        if (Objects.isNull(location) || location.isEmpty()) {
            throw new IllegalArgumentException("Vehicle location is mandatory!");
        }
        this.id = id;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.rate = rate;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public String getLocation() {
        return location;
    }
}
