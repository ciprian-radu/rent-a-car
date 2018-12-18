package ro.ciprianradu.rentacar.gateways;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import ro.ciprianradu.rentacar.usecases.Context;
import ro.ciprianradu.rentacar.entity.Renter;
import ro.ciprianradu.rentacar.usecases.gateways.RenterGateway;

/**
 * Unit tests for {@link RenterGateway}
 */
class RenterJdbcGatewayTest {

    @BeforeEach
    void setup() {
        final RenterJdbcGateway renterGateway = new RenterJdbcGateway(JdbcTemplateFactory.createJdbcTemplate());
        Context.renterGateway = renterGateway;
    }

    @Test
    void test_save_noTelephoneNumber_saves() {
        final Renter renter = new Renter();
        renter.setFirstName("John");
        renter.setLastName("Doe");
        renter.setEmail("john.doe@email.com");
        Context.renterGateway.save(renter);
    }

    @Test
    void test_save_duplicateEmail_doesNotSave() {
        final Renter renter = new Renter();
        renter.setFirstName("John");
        renter.setLastName("Doe");
        renter.setEmail("john.doe@email.com");
        Context.renterGateway.save(renter);
        Assertions.assertThrows(DuplicateKeyException.class, () -> Context.renterGateway.save(renter));
    }

    @Test
    void test_findByEmail_noEmail_returnsNoResult() {
        final Optional<Renter> renterOptional = Context.renterGateway.findByEmail("john.doe@email.com");
        Assertions.assertFalse(renterOptional.isPresent());
    }

    @Test
    void test_findByEmail_email_returnsRenter() {
        final Renter renter = new Renter();
        renter.setFirstName("John");
        renter.setLastName("Doe");
        renter.setEmail("john.doe@email.com");
        Context.renterGateway.save(renter);
        final Optional<Renter> renterOptional = Context.renterGateway.findByEmail("john.doe@email.com");
        Assertions.assertTrue(renterOptional.isPresent());
    }

}