package ro.ciprianradu.rentacar.usecases.gateways;

/**
 * Provides a single point of access to all available Gateways. A Gateway is a generic way to access
 * a persistence mechanism for an Entity.
 */
public class GatewaysAccessor {

    private LocationGateway locationGateway;

    private RenterGateway renterGateway;

    private ReservationGateway reservationGateway;

    private VehicleBrandGateway vehicleBrandGateway;

    private VehicleGateway vehicleGateway;

    private VehicleModelGateway vehicleModelGateway;

    private VehicleTypeGateway vehicleTypeGateway;

    public LocationGateway getLocationGateway() {
        return locationGateway;
    }

    public void setLocationGateway(LocationGateway locationGateway) {
        this.locationGateway = locationGateway;
    }

    public RenterGateway getRenterGateway() {
        return renterGateway;
    }

    public void setRenterGateway(RenterGateway renterGateway) {
        this.renterGateway = renterGateway;
    }

    public ReservationGateway getReservationGateway() {
        return reservationGateway;
    }

    public void setReservationGateway(ReservationGateway reservationGateway) {
        this.reservationGateway = reservationGateway;
    }

    public VehicleBrandGateway getVehicleBrandGateway() {
        return vehicleBrandGateway;
    }

    public void setVehicleBrandGateway(VehicleBrandGateway vehicleBrandGateway) {
        this.vehicleBrandGateway = vehicleBrandGateway;
    }

    public VehicleGateway getVehicleGateway() {
        return vehicleGateway;
    }

    public void setVehicleGateway(VehicleGateway vehicleGateway) {
        this.vehicleGateway = vehicleGateway;
    }

    public VehicleModelGateway getVehicleModelGateway() {
        return vehicleModelGateway;
    }

    public void setVehicleModelGateway(VehicleModelGateway vehicleModelGateway) {
        this.vehicleModelGateway = vehicleModelGateway;
    }

    public VehicleTypeGateway getVehicleTypeGateway() {
        return vehicleTypeGateway;
    }

    public void setVehicleTypeGateway(VehicleTypeGateway vehicleTypeGateway) {
        this.vehicleTypeGateway = vehicleTypeGateway;
    }

}
