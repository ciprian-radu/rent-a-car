package ro.ciprianradu.rentacar.gateways;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ro.ciprianradu.rentacar.entity.Renter;
import ro.ciprianradu.rentacar.usecases.gateways.RenterGateway;

/**
 * JDBC based implementation
 */
public class RenterJdbcGateway implements RenterGateway {

    private JdbcTemplate jdbcTemplate;

    private RenterRowMapper mapper = new RenterRowMapper();

    /**
     * Constructor
     *
     * @param jdbcTemplate the JDBC template (cannot be <code>null</code>)
     */
    public RenterJdbcGateway(final JdbcTemplate jdbcTemplate) {
        if (jdbcTemplate == null) {
            throw new IllegalArgumentException("JDBC template must be given!");
        }
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Renter save(final Renter renter) {
        jdbcTemplate.update(
            "INSERT INTO RENT_A_CAR.RENTER (FIRST_NAME, LAST_NAME, EMAIL, TELEPHONE_NUMBER) VALUES (?, ?, ?, ?)", renter.getFirstName(), renter.getLastName(),
            renter.getEmail(), renter.getTelephoneNumber());
        return renter;
    }

    @Override
    public Optional<Renter> findByEmail(final String email) {
        final List<Renter> renters = jdbcTemplate.query("SELECT * FROM RENT_A_CAR.RENTER WHERE EMAIL = ?", mapper, new Object[]{email});
        return Optional.ofNullable(renters.isEmpty() ? null : renters.get(0));
    }

    private static class RenterRowMapper implements RowMapper<Renter> {

        @Override
        public Renter mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final Renter renter = new Renter();
            renter.setFirstName(rs.getString("FIRST_NAME"));
            renter.setLastName(rs.getString("LAST_NAME"));
            renter.setEmail(rs.getString("EMAIL"));
            renter.setTelephoneNumber(rs.getString("TELEPHONE_NUMBER"));
            return renter;
        }
    }

}
