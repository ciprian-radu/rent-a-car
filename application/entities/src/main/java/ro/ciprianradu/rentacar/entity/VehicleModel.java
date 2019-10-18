package ro.ciprianradu.rentacar.entity;

/**
 * Represents characteristics a certain model of a vehicle has.
 */
public class VehicleModel extends Entity {

    /**
     * the name of this model
     */
    private String name;

    /**
     * number of seats
     */
    private int seats;

    /**
     * Air Conditioning (not available by default)
     */
    private boolean ac;

    /**
     * transmission can be manual (default) or automatic
     */
    private boolean automaticTransmission;

    // We may have many more attributes here, like: fuel consumption, number of luggage, mileage.

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(final int seats) {
        this.seats = seats;
    }

    public boolean isAc() {
        return ac;
    }

    public void setAc(final boolean ac) {
        this.ac = ac;
    }

    public boolean isAutomaticTransmission() {
        return automaticTransmission;
    }

    public void setAutomaticTransmission(final boolean automaticTransmission) {
        this.automaticTransmission = automaticTransmission;
    }

}
