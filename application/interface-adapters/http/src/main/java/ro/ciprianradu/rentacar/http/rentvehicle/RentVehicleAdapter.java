package ro.ciprianradu.rentacar.http.rentvehicle;

import ro.ciprianradu.rentacar.http.HttpResponse;
import ro.ciprianradu.rentacar.http.HttpStatus;
import ro.ciprianradu.rentacar.http.dtos.Reservation;
import ro.ciprianradu.rentacar.usecases.rentvehicle.RentVehicleInputData;
import ro.ciprianradu.rentacar.usecases.rentvehicle.RentVehicleInputPort;

/**
 * HTTP adapter for the Rent Vehicle use case
 */
public class RentVehicleAdapter {

    private RentVehicleInputPort inputPort;

    private RentVehicleHttpResponse outputPort = new RentVehicleHttpResponse();

    /**
     * Constructor
     *
     * @param inputPort the input port (cannot be <code>null</code>)
     */
    public RentVehicleAdapter(final RentVehicleInputPort inputPort) {
        if (inputPort == null) {
            throw new IllegalArgumentException("Input port must be given!");
        }
        this.inputPort = inputPort;
    }

    public HttpResponse rentVehicle(final Reservation reservation) {
        HttpResponse httpResponse;

        if (isValidInput(reservation)) {
            final RentVehicleInputData registerVehicleInputData = buildInputData(reservation);
            inputPort.rentVehicle(registerVehicleInputData, outputPort);
            httpResponse = outputPort.getHttpResponse();
        } else {
            httpResponse = new HttpResponse(HttpStatus.BAD_REQUEST, "Invalid input data!");
        }

        return httpResponse;
    }

    private static boolean isValidInput(final Reservation reservation) {
        boolean isValid;

        if (reservation == null) {
            isValid = false;
        } else {
            isValid = reservation.getRenterEmail() != null && reservation.getVehicleType() != null
                && reservation.getPickupDate() != null
                && reservation.getPickupLocationName() != null
                && reservation.getReturnDate() != null
                && reservation.getReturnLocationName() != null;
        }

        return isValid;
    }

    private static RentVehicleInputData buildInputData(final Reservation reservation) {
        return new RentVehicleInputData(reservation.getRenterEmail(), reservation.getVehicleType(),
            reservation.getVehicleBrand(), reservation.getVehicleModel(),
            reservation.getPickupDate(), reservation.getPickupLocationName(),
            reservation.getReturnDate(), reservation.getReturnLocationName());
    }

    public String getRentId() {
        return outputPort.getRentId();
    }

}
