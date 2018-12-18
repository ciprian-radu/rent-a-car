package ro.ciprianradu.rentacar.http.registerrenter;

import java.util.regex.Pattern;
import ro.ciprianradu.rentacar.http.HttpResponse;
import ro.ciprianradu.rentacar.http.HttpStatus;
import ro.ciprianradu.rentacar.http.dtos.Renter;
import ro.ciprianradu.rentacar.usecases.registerrenter.RegisterRenterInputData;
import ro.ciprianradu.rentacar.usecases.registerrenter.RegisterRenterInputPort;

/**
 * HTTP adapter for the Register Renter use case
 */
public class RegisterRenterAdapter {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private RegisterRenterInputPort inputPort;

    private RegisterRenterHttpResponse outputPort = new RegisterRenterHttpResponse();

    /**
     * Constructor
     *
     * @param inputPort the input port (cannot be <code>null</code>)
     */
    public RegisterRenterAdapter(final RegisterRenterInputPort inputPort) {
        if (inputPort == null) {
            throw new IllegalArgumentException("Input port must be given!");
        }
        this.inputPort = inputPort;
    }

    public HttpResponse registerRenter(final Renter renter) {
        HttpResponse httpResponse;

        if (isValidInput(renter)) {
            final RegisterRenterInputData registerRenterInputData = buildInputData(renter);
            inputPort.registerRenter(registerRenterInputData, outputPort);
            httpResponse = outputPort.getHttpResponse();
        } else {
            httpResponse = new HttpResponse(HttpStatus.BAD_REQUEST, "Invalid input data!");
        }

        return httpResponse;
    }

    private static boolean isValidInput(final Renter renter) {
        boolean isValid;

        if (renter == null) {
            isValid = false;
        } else {
            isValid = renter.getFirstName() != null && renter.getLastName() != null && isValidEmail(renter.getEmail());
        }

        return isValid;
    }

    private static boolean isValidEmail(String email) {
        return email != null && VALID_EMAIL_ADDRESS_REGEX.matcher(email).find();
    }

    private static RegisterRenterInputData buildInputData(final Renter renter) {
        final RegisterRenterInputData inputData = new RegisterRenterInputData(renter.getFirstName(), renter.getLastName(), renter.getEmail());
        inputData.setTelephoneNumber(renter.getTelephoneNumber());

        return inputData;
    }
}
