package ro.ciprianradu.rentacar.usecases;

import ro.ciprianradu.rentacar.usecases.gateways.*;

/**
 * Access point for all available Data Access Interfaces
 */
public class Context {

    public static LocationGateway locationGateway;

    public static RenterGateway renterGateway;

    public static VehicleGateway vehicleGateway;

    public static VehicleTypeGateway vehicleTypeGateway;

    public static VehicleBrandGateway vehicleBrandGateway;

    public static VehicleModelGateway vehicleModelGateway;

    public static ReservationGateway reservationGateway;

}
