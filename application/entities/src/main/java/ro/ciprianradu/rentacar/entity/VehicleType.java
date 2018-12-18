package ro.ciprianradu.rentacar.entity;

/**
 * Represents a type of vehicle. Examples: Economy Car, Sports Car, SUV, Limousine.
 */
public class VehicleType extends Entity {

    private String type;

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

}
