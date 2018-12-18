package ro.ciprianradu.rentacar.http.dtos;

/**
 * Physical location where vehicles can be picked up or returned.
 */
public class Location {

    private String name;

    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}