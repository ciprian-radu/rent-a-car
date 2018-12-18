package ro.ciprianradu.rentacar.usecases.registerlocation;

import java.util.Objects;

/**
 * Input data for the {@link RegisterLocationUseCase}
 */
public class RegisterLocationInputData {

    private String name;

    private String address;

    /**
     * Constructor
     *
     * @param name the location name (must not be empty nor <code>null</code>)
     * @param address
     */
    public RegisterLocationInputData(final String name, final String address) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException("Name is mandatory!");
        }
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

}
