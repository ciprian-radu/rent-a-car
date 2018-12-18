package ro.ciprianradu.rentacar.usecases.rentvehicle;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Input data for the {@link RentVehicleUseCase}
 */
public class RentVehicleInputData {

    private String renterEmail;

    private String vehicleType;

    private String vehicleBrand;

    private String vehicleModel;

    private ZonedDateTime pickupDate;

    private String pickupLocationName;

    private ZonedDateTime returnDate;

    private String returnLocationName;

    /**
     * Constructor
     *
     * @param renterEmail the e-mail address of the person which rents the vehicle (must not be empty nor <code>null</code>)
     * @param vehicleType the type of the vehicle (must not be empty nor <code>null</code>)
     * @param pickupDate the date and time when the vehicle will be picked up (must not be <code>null</code>, after the current time and before {@link
     * #returnDate})
     * @param pickupLocationName the name of pickup location (must not be empty nor <code>null</code>)
     * @param returnDate the date and time when the vehicle will be returned (must not be <code>null</code> and after {@link #pickupDate})
     * @param returnLocationName the name of pickup location (must not be empty nor <code>null</code>)
     */
    public RentVehicleInputData(final String renterEmail, final String vehicleType, final String vehicleBrand, final String vehicleModel,
        final ZonedDateTime pickupDate, final String pickupLocationName, final ZonedDateTime returnDate,
        final String returnLocationName) {
        if (Objects.isNull(renterEmail) || renterEmail.isEmpty()) {
            throw new IllegalArgumentException("Renter e-mail is mandatory!");
        }
        if (Objects.isNull(vehicleType) || vehicleType.isEmpty()) {
            throw new IllegalArgumentException("Vehicle type is mandatory!");
        }
        if (Objects.isNull(vehicleBrand) || vehicleBrand.isEmpty()) {
            throw new IllegalArgumentException("Vehicle brand is mandatory!");
        }
        if (Objects.isNull(vehicleModel) || vehicleModel.isEmpty()) {
            throw new IllegalArgumentException("Vehicle model is mandatory!");
        }
        if (Objects.isNull(pickupDate)) {
            throw new IllegalArgumentException("Pickup date is mandatory!");
        }
        if (Objects.isNull(returnDate)) {
            throw new IllegalArgumentException("Return date is mandatory!");
        }
//        if (pickupDate.isBefore(ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()))) {
//            throw new IllegalArgumentException("Pickup date is in the past!");
//        }
        if (pickupDate.isAfter(returnDate)) {
            throw new IllegalArgumentException("Pickup date must be before return date!");
        }
        if (returnDate.minus(Duration.of(1, ChronoUnit.HOURS)).isBefore(pickupDate)) {
            throw new IllegalArgumentException("Rent period must be at least one hour!");
        }
        if (Objects.isNull(pickupLocationName) || pickupLocationName.isEmpty()) {
            throw new IllegalArgumentException("Pickup location name is mandatory!");
        }
        if (Objects.isNull(returnLocationName) || returnLocationName.isEmpty()) {
            throw new IllegalArgumentException("Return location name is mandatory!");
        }
        this.renterEmail = renterEmail;
        this.vehicleType = vehicleType;
        this.vehicleBrand = vehicleBrand;
        this.vehicleModel = vehicleModel;
        this.pickupDate = pickupDate;
        this.pickupLocationName = pickupLocationName;
        this.returnDate = returnDate;
        this.returnLocationName = returnLocationName;
    }

    public String getRenterEmail() {
        return renterEmail;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public ZonedDateTime getPickupDate() {
        return pickupDate;
    }

    public String getPickupLocationName() {
        return pickupLocationName;
    }

    public ZonedDateTime getReturnDate() {
        return returnDate;
    }

    public String getReturnLocationName() {
        return returnLocationName;
    }
}
