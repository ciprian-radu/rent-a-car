package ro.ciprianradu.rentacar.gateways;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import ro.ciprianradu.rentacar.usecases.Context;
import ro.ciprianradu.rentacar.entity.Location;
import ro.ciprianradu.rentacar.usecases.gateways.LocationGateway;

/**
 * Unit tests for {@link LocationGateway}
 */
class LocationJdbcGatewayTest {

    @BeforeEach
    void setup() {
        final LocationGateway locationGateway = new LocationJdbcGateway(JdbcTemplateFactory.createJdbcTemplate());
        Context.locationGateway = locationGateway;
    }

    @Test
    void test_save_noAddress_saves() {
        final Location location = new Location();
        location.setName("Location 1");
        Context.locationGateway.save(location);
    }

    @Test
    void test_save_duplicateName_doesNotSave() {
        final Location location = new Location();
        location.setName("Location 1");
        Context.locationGateway.save(location);
        Assertions.assertThrows(DuplicateKeyException.class, () -> Context.locationGateway.save(location));
    }

    @Test
    void test_save_address_saves() {
        final Location location = new Location();
        location.setName("Location 1");
        location.setAddress("Address 1");
        Context.locationGateway.save(location);
    }

    @Test
    void test_findByName_missingName_empty() {
        final Optional<Location> locationOptional = Context.locationGateway.findByName("name");
        Assertions.assertFalse(locationOptional.isPresent());
    }

    @Test
    void test_findByName_name_returnsLocation() {
        final Location location = new Location();
        location.setName("Location 1");
        location.setAddress("Address 1");
        Context.locationGateway.save(location);
        final Optional<Location> locationOptional = Context.locationGateway.findByName("Location 1");
        Assertions.assertTrue(locationOptional.isPresent());
    }

    @Test
    void test_findAll_noLocations_returnsEmptyList() {
        final Iterable<Location> locations = Context.locationGateway.findAll();
        Assertions.assertFalse(locations.iterator().hasNext());
    }

    @Test
    void test_findAll_locations_returnsAllLocations() {
        final Location location1 = new Location();
        location1.setName("Location 1");
        location1.setAddress("Address 1");
        Context.locationGateway.save(location1);
        final Location location2 = new Location();
        location2.setName("Location 2");
        location2.setAddress("Address 2");
        Context.locationGateway.save(location2);
        final Iterable<Location> locations = Context.locationGateway.findAll();
        final List<Location> locationsAsList = new ArrayList<>();
        locations.forEach(l -> locationsAsList.add(l));
        Assertions.assertEquals(2, locationsAsList.size());
    }

}