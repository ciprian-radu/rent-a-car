package ro.ciprianradu.rentacar.gateways;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ro.ciprianradu.rentacar.entity.VehicleType;
import ro.ciprianradu.rentacar.usecases.gateways.VehicleTypeGateway;

/**
 * JDBC based implementation
 */
public class VehicleTypeJdbcGateway implements VehicleTypeGateway {

    private JdbcTemplate jdbcTemplate;

    private static VehicleTypeRowMapper mapper = new VehicleTypeRowMapper();

    /**
     * Constructor
     *
     * @param jdbcTemplate the JDBC template (cannot be <code>null</code>)
     */
    public VehicleTypeJdbcGateway(final JdbcTemplate jdbcTemplate) {
        if (jdbcTemplate == null) {
            throw new IllegalArgumentException("JDBC template must be given!");
        }
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public VehicleType save(final VehicleType vehicleType) {
        jdbcTemplate
            .update("INSERT INTO RENT_A_CAR.VEHICLE_TYPE (TYPE) VALUES (?)", vehicleType.getType());
        return vehicleType;
    }

    @Override
    public Optional<VehicleType> findByType(final String type) {
        final List<VehicleType> vehicleTypes = jdbcTemplate
            .query("SELECT * FROM RENT_A_CAR.VEHICLE_TYPE WHERE TYPE = ?", mapper, type);
        return Optional.ofNullable(vehicleTypes.isEmpty() ? null : vehicleTypes.get(0));
    }

    private static class VehicleTypeRowMapper implements RowMapper<VehicleType> {

        @Override
        public VehicleType mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final VehicleType vehicleType = new VehicleType();
            vehicleType.setType(rs.getString("TYPE"));
            return vehicleType;
        }
    }

}
