package ro.ciprianradu.rentacar.entity;

/**
 * Represents a vehicle brand. Examples: BMW, Ford, AUDI.
 */
public class VehicleBrand extends Entity {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
