package ro.ciprianradu.rentacar.http.dtos;

import java.math.BigDecimal;

/**
 * Represents a certain category of vehicle, which can be rented from the associated (pickup)
 * location. It can be returned to any location. It is like a {@link Vehicle} but without any ID.
 */
public class VehicleCategory {

    private String type;

    private String brand;

    private String model;

    private BigDecimal rate;

    private String location;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return how much it costs (in EURO) to reserve this vehicle for one day
     **/
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * @param rate how much it costs (in EURO) to reserve this vehicle for one day
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getLocation() {
        return location;
    }

    /**
     * @param location the name of the (pickup) location to which this vehicle is associated to
     */
    public void setLocation(final String location) {
        this.location = location;
    }
}
