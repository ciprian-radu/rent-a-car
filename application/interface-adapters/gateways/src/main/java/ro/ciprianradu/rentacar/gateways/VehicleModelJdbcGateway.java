package ro.ciprianradu.rentacar.gateways;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ro.ciprianradu.rentacar.entity.VehicleModel;
import ro.ciprianradu.rentacar.usecases.gateways.VehicleModelGateway;

/**
 * JDBC based implementation
 */
public class VehicleModelJdbcGateway implements VehicleModelGateway {

    private static VehicleModelRowMapper mapper = new VehicleModelRowMapper();

    private JdbcTemplate jdbcTemplate;

    /**
     * Constructor
     *
     * @param jdbcTemplate the JDBC template (cannot be <code>null</code>)
     */
    public VehicleModelJdbcGateway(final JdbcTemplate jdbcTemplate) {
        if (jdbcTemplate == null) {
            throw new IllegalArgumentException("JDBC template must be given!");
        }
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public VehicleModel save(final VehicleModel vehicleModel) {
        jdbcTemplate.update(
            "INSERT INTO RENT_A_CAR.VEHICLE_MODEL (NAME, AC, AUTOMATIC_TRANSMISSION, SEATS) VALUES (?, ?, ?, ?)", vehicleModel.getName(), vehicleModel.isAc(),
            vehicleModel.isAutomaticTransmission(), vehicleModel.getSeats());
        return vehicleModel;
    }

    @Override
    public Optional<VehicleModel> findByModel(final String model) {
        final List<VehicleModel> vehicleModels = jdbcTemplate.query("SELECT * FROM RENT_A_CAR.VEHICLE_MODEL WHERE NAME = ?", mapper, new Object[]{model});
        return Optional.ofNullable(vehicleModels.isEmpty() ? null : vehicleModels.get(0));
    }

    private static class VehicleModelRowMapper implements RowMapper<VehicleModel> {

        @Override
        public VehicleModel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final VehicleModel vehicleModel = new VehicleModel();
            vehicleModel.setName(rs.getString("NAME"));
            vehicleModel.setAc(rs.getBoolean("AC"));
            vehicleModel.setAutomaticTransmission(rs.getBoolean("AUTOMATIC_TRANSMISSION"));
            vehicleModel.setSeats(rs.getInt("SEATS"));
            return vehicleModel;
        }
    }

}
