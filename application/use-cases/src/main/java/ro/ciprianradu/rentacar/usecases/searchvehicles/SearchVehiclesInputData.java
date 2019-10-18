package ro.ciprianradu.rentacar.usecases.searchvehicles;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Input data for the {@link SearchVehiclesUseCase} - contains several search criteria that are all
 * mandatory.
 */
public class SearchVehiclesInputData {

    private ZonedDateTime pickupDate;

    private String pickupLocationName;

    private ZonedDateTime returnDate;

    private String returnLocationName;

    public SearchVehiclesInputData(final ZonedDateTime pickupDate, final String pickupLocationName,
        final ZonedDateTime returnDate, final String returnLocationName) {
        if (Objects.isNull(pickupDate)) {
            throw new IllegalArgumentException("Pickup date is mandatory!");
        }
        if (Objects.isNull(returnDate)) {
            throw new IllegalArgumentException("Return date is mandatory!");
        }
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
        this.pickupDate = pickupDate;
        this.pickupLocationName = pickupLocationName;
        this.returnDate = returnDate;
        this.returnLocationName = returnLocationName;
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
