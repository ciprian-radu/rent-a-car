package ro.ciprianradu.rentacar.auth.client.controller.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SearchDto {

    @NotEmpty
    @Size(min = 10, max = 10)
    private String pickupDate;

    @NotEmpty
    private String pickupLocationName;

    @NotEmpty
    @Size(min = 10, max = 10)
    private String returnDate;

    @NotEmpty
    private String returnLocationName;

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(final String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getPickupLocationName() {
        return pickupLocationName;
    }

    public void setPickupLocationName(final String pickupLocationName) {
        this.pickupLocationName = pickupLocationName;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(final String returnDate) {
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
        return "SearchDto{" + "pickupDate='" + pickupDate + '\'' + ", pickupLocationName='"
            + pickupLocationName + '\'' + ", returnDate='" + returnDate + '\''
            + ", returnLocationName='" + returnLocationName + '\'' + '}';
    }
}
