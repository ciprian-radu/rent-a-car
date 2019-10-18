package ro.ciprianradu.rentacar.auth.client.controller.dto;

import java.math.BigDecimal;

/**
 * Represents a vehicle category, which has at least one vehicle that can be rented.
 */
public class VehicleCategoryDto {

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

    public void setLocation(final String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "VehicleCategoryDto{" + "type='" + type + '\'' + ", brand='" + brand + '\''
            + ", model='" + model + '\'' + ", rate=" + rate + ", location='" + location + '\''
            + '}';
    }
}
