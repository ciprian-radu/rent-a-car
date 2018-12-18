package ro.ciprianradu.rentacar.usecases.registerrenter;

import java.util.Objects;

/**
 * Input data for the {@link RegisterRenterUseCase}
 */
public class RegisterRenterInputData {

    private String firstName;

    private String lastName;

    private String email;

    private String telephoneNumber;

    /**
     * Constructor
     *
     * @param firstName the first name of the renter (must not be empty nor <code>null</code>)
     * @param lastName the last name of the renter (must not be empty nor <code>null</code>)
     * @param email the e-mail address of the renter (must not be empty nor <code>null</code>)
     */
    public RegisterRenterInputData(final String firstName, final String lastName, final String email) {
        if (Objects.isNull(firstName) || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name is mandatory!");
        }
        if (Objects.isNull(lastName) || lastName.isEmpty()) {
            throw new IllegalArgumentException("Last name is mandatory!");
        }
        if (Objects.isNull(email) || email.isEmpty()) {
            throw new IllegalArgumentException("E-mail is mandatory!");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(final String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

}
