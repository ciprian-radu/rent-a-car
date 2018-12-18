package ro.ciprianradu.rentacar.entity;

import java.time.ZonedDateTime;

/**
 * Holds the information about a reservation made for a vehicle.
 */
public class Reservation extends Entity {

    private Renter renter;

    private Vehicle vehicle;

    private ZonedDateTime pickupDate;

    private Location pickupLocation;

    private ZonedDateTime returnDate;

    private Location returnLocation;

    public Renter getRenter() {
        return renter;
    }

    public void setRenter(final Renter renter) {
        this.renter = renter;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(final Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public ZonedDateTime getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(final ZonedDateTime pickupDate) {
        this.pickupDate = pickupDate;
    }

    public Location getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(final Location pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public ZonedDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(final ZonedDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public Location getReturnLocation() {
        return returnLocation;
    }

    public void setReturnLocation(final Location returnLocation) {
        this.returnLocation = returnLocation;
    }

}
