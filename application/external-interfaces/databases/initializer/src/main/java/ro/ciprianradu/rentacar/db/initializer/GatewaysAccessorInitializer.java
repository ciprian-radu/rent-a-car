package ro.ciprianradu.rentacar.db.initializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ro.ciprianradu.rentacar.gateways.LocationJdbcGateway;
import ro.ciprianradu.rentacar.gateways.RenterJdbcGateway;
import ro.ciprianradu.rentacar.gateways.ReservationJdbcGateway;
import ro.ciprianradu.rentacar.gateways.VehicleBrandJdbcGateway;
import ro.ciprianradu.rentacar.gateways.VehicleJdbcGateway;
import ro.ciprianradu.rentacar.gateways.VehicleModelJdbcGateway;
import ro.ciprianradu.rentacar.gateways.VehicleTypeJdbcGateway;
import ro.ciprianradu.rentacar.usecases.gateways.GatewaysAccessor;

/**
 * Creates a {@link ro.ciprianradu.rentacar.usecases.gateways.GatewaysAccessor}
 */
@Configuration
public class GatewaysAccessorInitializer {

    @Bean
    public GatewaysAccessor configureGatewaysAccessor(JdbcTemplate jdbcTemplate) {
        final GatewaysAccessor gatewaysAccessor = new GatewaysAccessor();

        gatewaysAccessor.setLocationGateway(new LocationJdbcGateway(jdbcTemplate));
        gatewaysAccessor.setRenterGateway(new RenterJdbcGateway(jdbcTemplate));
        gatewaysAccessor.setReservationGateway(new ReservationJdbcGateway(jdbcTemplate));
        gatewaysAccessor.setVehicleGateway(new VehicleJdbcGateway(jdbcTemplate));
        gatewaysAccessor.setVehicleBrandGateway(new VehicleBrandJdbcGateway(jdbcTemplate));
        gatewaysAccessor.setVehicleModelGateway(new VehicleModelJdbcGateway(jdbcTemplate));
        gatewaysAccessor.setVehicleTypeGateway(new VehicleTypeJdbcGateway(jdbcTemplate));

        return gatewaysAccessor;
    }

}