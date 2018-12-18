package ro.ciprianradu.rentacar.db.initializer;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ro.ciprianradu.rentacar.gateways.*;
import ro.ciprianradu.rentacar.usecases.Context;

/**
 * Initializes the {@link Context} by instantiating gateways using their JDBC based implementation.
 */
@Configuration
public class ContextInitializer {

    public ContextInitializer(JdbcTemplate jdbcTemplate) {
        Context.locationGateway = new LocationJdbcGateway(jdbcTemplate);
        Context.renterGateway = new RenterJdbcGateway(jdbcTemplate);
        Context.vehicleTypeGateway = new VehicleTypeJdbcGateway(jdbcTemplate);
        Context.vehicleBrandGateway = new VehicleBrandJdbcGateway(jdbcTemplate);
        Context.vehicleModelGateway = new VehicleModelJdbcGateway(jdbcTemplate);
        Context.vehicleGateway = new VehicleJdbcGateway(jdbcTemplate);
        Context.reservationGateway = new ReservationJdbcGateway(jdbcTemplate);
    }

}
