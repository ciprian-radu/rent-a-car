package ro.ciprianradu.rentacar.entity;

/**
 * Denotes a physical location, which has a name and a postal address.
 */
public class Location extends Entity {

    private String name;

    private String address;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

}
