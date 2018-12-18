package ro.ciprianradu.rentacar.auth.client.controller.dto;

/**
 *
 */
public class RentStatusDto {

    private boolean status;

    private int rentId;

    private RentDto rentDto;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(final boolean status) {
        this.status = status;
    }

    public int getRentId() {
        return rentId;
    }

    public void setRentId(final int rentId) {
        this.rentId = rentId;
    }

    public RentDto getRentDto() {
        return rentDto;
    }

    public void setRentDto(final RentDto rentDto) {
        this.rentDto = rentDto;
    }

    @Override
    public String toString() {
        return "RentStatusDto{" +
            "status=" + status +
            ", rentId=" + rentId +
            ", rentDto=" + rentDto +
            '}';
    }

}
