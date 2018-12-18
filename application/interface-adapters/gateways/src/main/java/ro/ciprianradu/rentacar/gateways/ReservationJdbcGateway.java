package ro.ciprianradu.rentacar.gateways;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ro.ciprianradu.rentacar.entity.*;
import ro.ciprianradu.rentacar.usecases.gateways.ReservationGateway;

/**
 * JDBC based implementation
 */
public class ReservationJdbcGateway implements ReservationGateway {

    private JdbcTemplate jdbcTemplate;

    private ReservationRowMapper mapper = new ReservationRowMapper();

    /**
     * Constructor
     *
     * @param jdbcTemplate the JDBC template (cannot be <code>null</code>)
     */
    public ReservationJdbcGateway(final JdbcTemplate jdbcTemplate) {
        if (jdbcTemplate == null) {
            throw new IllegalArgumentException("JDBC template must be given!");
        }
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Saves a reservation. Note that Renter, Vehicle pickup and return location must already exist in the database.
     *
     * @param reservation the reservation (assumed not <code>null</code>)
     * @return the saved reservation
     */
    @Override
    public Reservation save(final Reservation reservation) {
        jdbcTemplate.update(
            "INSERT INTO RENT_A_CAR.RESERVATION (RENTER, VEHICLE, PICKUP_DATE, PICKUP_LOCATION, RETURN_DATE, RETURN_LOCATION) VALUES (?, ?, ?, ?, ?, ?)",
            reservation.getRenter().getEmail(), reservation.getVehicle().getId(),
            Timestamp.valueOf(reservation.getPickupDate().withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()), reservation.getPickupLocation().getName(),
            Timestamp.valueOf(reservation.getReturnDate().withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()), reservation.getReturnLocation().getName());
        return reservation;
    }

    @Override
    public Iterable<Reservation> findAllByVehicle(final String vehicleId) {
        final List<Reservation> reservations = jdbcTemplate
            .query(
                "SELECT RT.FIRST_NAME AS RENTER_FIRST_NAME, RT.LAST_NAME AS RENTER_LAST_NAME, RT.EMAIL AS RENTER_EMAIL, RT.TELEPHONE_NUMBER AS RENTER_PHONE, "
                    + "V.ID AS VEHICLE_ID, VT.TYPE AS VEHICLE_TYPE, VB.NAME AS VEHICLE_BRAND, VM.NAME AS VEHICLE_MODEL, VM.AC AS VEHICLE_AC, VM.AUTOMATIC_TRANSMISSION AS VEHICLE_AUTOMATIC_TRANSMISSION, VM.SEATS AS VEHICLE_SEATS, V.RATE AS VEHICLE_RATE, "
                    + "R.PICKUP_DATE, PL.NAME AS PICKUP_LOCATION_NAME, PL.ADDRESS AS PICKUP_LOCATION_ADDRESS, "
                    + "R.RETURN_DATE, RL.NAME AS RETURN_LOCATION_NAME, RL.ADDRESS AS RETURN_LOCATION_ADDRESS "
                    + "FROM RENT_A_CAR.RESERVATION R "
                    + "JOIN RENT_A_CAR.RENTER RT ON R.RENTER = RT.EMAIL "
                    + "JOIN RENT_A_CAR.VEHICLE V ON R.VEHICLE = V.ID "
                    + "JOIN RENT_A_CAR.VEHICLE_TYPE VT ON V.TYPE = VT.TYPE "
                    + "JOIN RENT_A_CAR.VEHICLE_BRAND VB ON V.BRAND = VB.NAME "
                    + "JOIN RENT_A_CAR.VEHICLE_MODEL VM ON V.MODEL = VM.NAME "
                    + "JOIN RENT_A_CAR.LOCATION PL ON R.PICKUP_LOCATION = PL.NAME "
                    + "JOIN RENT_A_CAR.LOCATION RL ON R.RETURN_LOCATION = RL.NAME "
                    + "WHERE R.VEHICLE = ?", mapper, new Object[]{vehicleId});
        return reservations;
    }

    @Override
    public Optional<Reservation> findByOverlappingRentPeriod(final String vehicleId, final ZonedDateTime pickupDate, final ZonedDateTime returnDate) {
        final List<Reservation> reservations = jdbcTemplate
            .query(
                "SELECT RT.FIRST_NAME AS RENTER_FIRST_NAME, RT.LAST_NAME AS RENTER_LAST_NAME, RT.EMAIL AS RENTER_EMAIL, RT.TELEPHONE_NUMBER AS RENTER_PHONE, "
                    + "V.ID AS VEHICLE_ID, VT.TYPE AS VEHICLE_TYPE, VB.NAME AS VEHICLE_BRAND, VM.NAME AS VEHICLE_MODEL, VM.AC AS VEHICLE_AC, VM.AUTOMATIC_TRANSMISSION AS VEHICLE_AUTOMATIC_TRANSMISSION, VM.SEATS AS VEHICLE_SEATS, V.RATE AS VEHICLE_RATE, "
                    + "R.PICKUP_DATE, PL.NAME AS PICKUP_LOCATION_NAME, PL.ADDRESS AS PICKUP_LOCATION_ADDRESS, "
                    + "R.RETURN_DATE, RL.NAME AS RETURN_LOCATION_NAME, RL.ADDRESS AS RETURN_LOCATION_ADDRESS "
                    + "FROM RENT_A_CAR.RESERVATION R "
                    + "JOIN RENT_A_CAR.RENTER RT ON R.RENTER = RT.EMAIL "
                    + "JOIN RENT_A_CAR.VEHICLE V ON R.VEHICLE = V.ID "
                    + "JOIN RENT_A_CAR.VEHICLE_TYPE VT ON V.TYPE = VT.TYPE "
                    + "JOIN RENT_A_CAR.VEHICLE_BRAND VB ON V.BRAND = VB.NAME "
                    + "JOIN RENT_A_CAR.VEHICLE_MODEL VM ON V.MODEL = VM.NAME "
                    + "JOIN RENT_A_CAR.LOCATION PL ON R.PICKUP_LOCATION = PL.NAME "
                    + "JOIN RENT_A_CAR.LOCATION RL ON R.RETURN_LOCATION = RL.NAME "
                    + "WHERE R.VEHICLE = ? AND R.PICKUP_DATE >= ? AND R.PICKUP_DATE <= ?", mapper,
                new Object[]{vehicleId,
                    Timestamp.valueOf(pickupDate.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()),
                    Timestamp.valueOf(returnDate.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime())});
        return Optional.ofNullable(reservations.isEmpty() ? null : reservations.get(0));
    }

    private static class ReservationRowMapper implements RowMapper<Reservation> {

        @Override
        public Reservation mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final Reservation reservation = new Reservation();
            mapRenter(rs, reservation);
            mapVehicle(rs);
            reservation.setPickupDate(rs.getTimestamp("PICKUP_DATE").toLocalDateTime().atZone(ZoneOffset.UTC));
            reservation.setPickupLocation(mapPickupLocation(rs));
            reservation.setReturnDate(rs.getTimestamp("RETURN_DATE").toLocalDateTime().atZone(ZoneOffset.UTC));
            reservation.setReturnLocation(mapReturnLocation(rs));
            return reservation;
        }

        private void mapRenter(final ResultSet rs, final Reservation reservation) throws SQLException {
            Renter renter = new Renter();
            renter.setFirstName(rs.getString("RENTER_FIRST_NAME"));
            renter.setLastName(rs.getString("RENTER_LAST_NAME"));
            renter.setEmail(rs.getString("RENTER_EMAIL"));
            renter.setTelephoneNumber(rs.getString("RENTER_PHONE"));
            reservation.setRenter(renter);
        }

        private void mapVehicle(final ResultSet rs) throws SQLException {
            Vehicle vehicle = new Vehicle(rs.getString("VEHICLE_ID"));
            mapVehicleType(rs, vehicle);
            mapVehicleBrand(rs, vehicle);
            mapVehicleModel(rs, vehicle);
            vehicle.setRate(rs.getBigDecimal("VEHICLE_RATE"));
        }

        private void mapVehicleType(final ResultSet rs, final Vehicle vehicle) throws SQLException {
            final VehicleType vehicleType = new VehicleType();
            vehicleType.setType(rs.getString("VEHICLE_TYPE"));
            vehicle.setType(vehicleType);
        }

        private void mapVehicleBrand(final ResultSet rs, final Vehicle vehicle) throws SQLException {
            final VehicleBrand vehicleBrand = new VehicleBrand();
            vehicleBrand.setName(rs.getString("VEHICLE_BRAND"));
            vehicle.setBrand(vehicleBrand);
        }

        private void mapVehicleModel(final ResultSet rs, final Vehicle vehicle) throws SQLException {
            final VehicleModel vehicleModel = new VehicleModel();
            vehicleModel.setName(rs.getString("VEHICLE_MODEL"));
            vehicleModel.setAc(rs.getBoolean("VEHICLE_AC"));
            vehicleModel.setAutomaticTransmission(rs.getBoolean("VEHICLE_AUTOMATIC_TRANSMISSION"));
            vehicleModel.setSeats(rs.getInt("VEHICLE_SEATS"));
            vehicle.setModel(vehicleModel);
        }

        private Location mapPickupLocation(final ResultSet rs) throws SQLException {
            final Location location = new Location();
            location.setName(rs.getString("PICKUP_LOCATION_NAME"));
            location.setAddress(rs.getString("PICKUP_LOCATION_ADDRESS"));
            return location;
        }

        private Location mapReturnLocation(final ResultSet rs) throws SQLException {
            final Location location = new Location();
            location.setName(rs.getString("RETURN_LOCATION_NAME"));
            location.setAddress(rs.getString("RETURN_LOCATION_ADDRESS"));
            return location;
        }
    }

}
