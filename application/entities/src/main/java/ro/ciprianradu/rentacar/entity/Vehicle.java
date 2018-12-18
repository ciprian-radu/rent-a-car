package ro.ciprianradu.rentacar.entity;

import java.math.BigDecimal;

/**
 * Represents a vehicle, which can be rented.
 */
public class Vehicle extends Entity {

    private VehicleType type;

    private VehicleBrand brand;

    private VehicleModel model;

    /** the name of the location to which this vehicle is associated to */
    private String location;

    /** how much it costs (in EURO) to reserve this vehicle for one day */
    private BigDecimal rate;

    public Vehicle(final String id) {
        super(id);
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(final VehicleType type) {
        this.type = type;
    }

    public VehicleBrand getBrand() {
        return brand;
    }

    public void setBrand(final VehicleBrand brand) {
        this.brand = brand;
    }

    public VehicleModel getModel() {
        return model;
    }

    public void setModel(final VehicleModel model) {
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
}
