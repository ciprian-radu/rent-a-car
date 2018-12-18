package ro.ciprianradu.rentacar.http.registervehicle;

import ro.ciprianradu.rentacar.http.HttpResponse;
import ro.ciprianradu.rentacar.http.HttpStatus;
import ro.ciprianradu.rentacar.http.dtos.Vehicle;
import ro.ciprianradu.rentacar.usecases.registervehicle.RegisterVehicleInputData;
import ro.ciprianradu.rentacar.usecases.registervehicle.RegisterVehicleInputPort;

/**
 * HTTP adapter for the Register Vehicle use case
 */
public class RegisterVehicleAdapter {

    private RegisterVehicleInputPort inputPort;

    private RegisterVehicleHttpResponse outputPort = new RegisterVehicleHttpResponse();

    /**
     * Constructor
     *
     * @param inputPort the input port (cannot be <code>null</code>)
     */
    public RegisterVehicleAdapter(final RegisterVehicleInputPort inputPort) {
        if (inputPort == null) {
            throw new IllegalArgumentException("Input port must be given!");
        }
        this.inputPort = inputPort;
    }

    private static RegisterVehicleInputData buildInputData(final Vehicle vehicle) {
        return new RegisterVehicleInputData(vehicle.getId(), vehicle.getType(), vehicle.getBrand(), vehicle.getModel(), vehicle.getRate(),
            vehicle.getLocation());
    }

    /**
     * Registers a given vehicle. It must have an ID with no white spaces in it. (The ID is exposed in URLs.)
     *
     * @param vehicle the vehicle to register (cannot be <code>null</code> and must have valid data)
     * @return the response of this operation
     */
    public HttpResponse registerVehicle(final Vehicle vehicle) {
        HttpResponse httpResponse;

        if (isValidInput(vehicle)) {
            final RegisterVehicleInputData registerVehicleInputData = buildInputData(vehicle);
            inputPort.registerVehicle(registerVehicleInputData, outputPort);
            httpResponse = outputPort.getHttpResponse();
        } else {
            httpResponse = new HttpResponse(HttpStatus.BAD_REQUEST, "Invalid input data!");
        }

        return httpResponse;
    }

    private static boolean isValidInput(final Vehicle vehicle) {
        boolean isValid;

        if (vehicle == null) {
            isValid = false;
        } else {
            isValid = isValidId(vehicle.getId()) && vehicle.getType() != null && vehicle.getBrand() != null && vehicle.getModel() != null && vehicle.getRate()
                != null && vehicle.getRate().signum() > 0 && isValidLocation(vehicle.getLocation());
        }

        return isValid;
    }

    private static boolean isValidId(final String id) {
        return id != null && !containsWhiteSpace(id);
    }

    private static boolean containsWhiteSpace(final String text) {
        boolean containsWhitespace = false;

        for (int i = 0; i < text.length(); i++) {
            if (Character.isWhitespace(text.charAt(i))) {
                containsWhitespace = true;
                break;
            }
        }

        return containsWhitespace;
    }

    private static boolean isValidLocation(final String location) {
        return location != null && !containsWhiteSpace(location);
    }

}
