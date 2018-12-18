package ro.ciprianradu.rentacar.gateways;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ro.ciprianradu.rentacar.entity.VehicleBrand;
import ro.ciprianradu.rentacar.usecases.gateways.VehicleBrandGateway;

/**
 * JDBC based implementation
 */
public class VehicleBrandJdbcGateway implements VehicleBrandGateway {

    private JdbcTemplate jdbcTemplate;

    private static VehicleBrandRowMapper mapper = new VehicleBrandRowMapper();

    /**
     * Constructor
     *
     * @param jdbcTemplate the JDBC template (cannot be <code>null</code>)
     */
    public VehicleBrandJdbcGateway(final JdbcTemplate jdbcTemplate) {
        if (jdbcTemplate == null) {
            throw new IllegalArgumentException("JDBC template must be given!");
        }
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public VehicleBrand save(final VehicleBrand vehicleBrand) {
        jdbcTemplate.update(
            "INSERT INTO RENT_A_CAR.VEHICLE_BRAND (NAME) VALUES (?)", vehicleBrand.getName());
        return vehicleBrand;
    }

    @Override
    public Optional<VehicleBrand> findByBrand(final String brand) {
        final List<VehicleBrand> vehicleBrands = jdbcTemplate.query("SELECT * FROM RENT_A_CAR.VEHICLE_BRAND WHERE NAME = ?", mapper, new Object[]{brand});
        return Optional.ofNullable(vehicleBrands.isEmpty() ? null : vehicleBrands.get(0));
    }

    private static class VehicleBrandRowMapper implements RowMapper<VehicleBrand> {

        @Override
        public VehicleBrand mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final VehicleBrand vehicleBrand = new VehicleBrand();
            vehicleBrand.setName(rs.getString("NAME"));
            return vehicleBrand;
        }
    }

}
