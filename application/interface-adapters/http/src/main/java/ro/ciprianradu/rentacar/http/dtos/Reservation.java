package ro.ciprianradu.rentacar.http.dtos;

import java.time.ZonedDateTime;

/**
 * Holds the information about a reservation made for a vehicle.
 */
public class Reservation {

    private String id;

    private String renterEmail;

    private String vehicleType;

    private String vehicleBrand;

    private String vehicleModel;

    private ZonedDateTime pickupDate;

    private String pickupLocationName;

    private ZonedDateTime returnDate;

    private String returnLocationName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRenterEmail() {
        return renterEmail;
    }

    public void setRenterEmail(String renterEmail) {
        this.renterEmail = renterEmail;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setVehicleBrand(final String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleModel(final String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public ZonedDateTime getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(ZonedDateTime pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getPickupLocationName() {
        return pickupLocationName;
    }

    public void setPickupLocationName(String pickupLocationName) {
        this.pickupLocationName = pickupLocationName;
    }

    public ZonedDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(ZonedDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnLocationName() {
        return returnLocationName;
    }

    public void setReturnLocationName(String returnLocationName) {
        this.returnLocationName = returnLocationName;
    }
}
