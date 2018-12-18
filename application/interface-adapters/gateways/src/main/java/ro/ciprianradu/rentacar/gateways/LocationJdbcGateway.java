package ro.ciprianradu.rentacar.gateways;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ro.ciprianradu.rentacar.entity.Location;
import ro.ciprianradu.rentacar.usecases.gateways.LocationGateway;

/**
 * JDBC based implementation
 */
public class LocationJdbcGateway implements LocationGateway {

    private JdbcTemplate jdbcTemplate;

    private static LocationRowMapper mapper = new LocationRowMapper();

    /**
     * Constructor
     *
     * @param jdbcTemplate the JDBC template (cannot be <code>null</code>)
     */
    public LocationJdbcGateway(final JdbcTemplate jdbcTemplate) {
        if (jdbcTemplate == null) {
            throw new IllegalArgumentException("JDBC template must be given!");
        }
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Location save(final Location location) {
        jdbcTemplate.update(
            "INSERT INTO RENT_A_CAR.LOCATION (NAME, ADDRESS) VALUES (?, ?)", location.getName(), location.getAddress());
        return location;
    }

    @Override
    public Optional<Location> findByName(final String name) {
        final List<Location> locations = jdbcTemplate.query("SELECT * FROM RENT_A_CAR.LOCATION WHERE NAME = ?", mapper, new Object[]{name});
        return Optional.ofNullable(locations.isEmpty() ? null : locations.get(0));
    }

    @Override
    public Iterable<Location> findAll() {
        return jdbcTemplate.query("SELECT * FROM RENT_A_CAR.LOCATION", mapper);
    }

    private static class LocationRowMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final Location location = new Location();
            location.setName(rs.getString("NAME"));
            location.setAddress(rs.getString("ADDRESS"));
            return location;
        }
    }

}
