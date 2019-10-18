package ro.ciprianradu.rentacar.auth.client.controller.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 *
 */
public class RentDto {

    @NotEmpty
    @Email
    private String renterEmail;

    @NotEmpty
    private String vehicleType;

    @NotEmpty
    private String vehicleBrand;

    @NotEmpty
    private String vehicleModel;

    @NotNull
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private ZonedDateTime pickupDate;

    @NotEmpty
    private String pickupLocationName;

    @NotNull
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private ZonedDateTime returnDate;

    @NotEmpty
    private String returnLocationName;

    public String getRenterEmail() {
        return renterEmail;
    }

    public void setRenterEmail(final String renterEmail) {
        this.renterEmail = renterEmail;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(final String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(final String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(final String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public ZonedDateTime getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(final ZonedDateTime pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getPickupLocationName() {
        return pickupLocationName;
    }

    public void setPickupLocationName(final String pickupLocationName) {
        this.pickupLocationName = pickupLocationName;
    }

    public ZonedDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(final ZonedDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnLocationName() {
        return returnLocationName;
    }

    public void setReturnLocationName(final String returnLocationName) {
        this.returnLocationName = returnLocationName;
    }

    @Override
    public String toString() {
        return "RentDto{" + "renterEmail='" + renterEmail + '\'' + ", vehicleType='" + vehicleType
            + '\'' + ", vehicleBrand='" + vehicleBrand + '\'' + ", vehicleModel='" + vehicleModel
            + '\'' + ", pickupDate='" + pickupDate + '\'' + ", pickupLocationName='"
            + pickupLocationName + '\'' + ", returnDate='" + returnDate + '\''
            + ", returnLocationName='" + returnLocationName + '\'' + '}';
    }
}
