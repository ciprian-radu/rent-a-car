package ro.ciprianradu.rentacar.gateways;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ro.ciprianradu.rentacar.entity.Vehicle;
import ro.ciprianradu.rentacar.entity.VehicleBrand;
import ro.ciprianradu.rentacar.entity.VehicleModel;
import ro.ciprianradu.rentacar.entity.VehicleType;
import ro.ciprianradu.rentacar.usecases.gateways.VehicleGateway;

/**
 * JDBC based implementation
 */
public class VehicleJdbcGateway implements VehicleGateway {

    private static VehicleRowMapper mapper = new VehicleRowMapper();

    private JdbcTemplate jdbcTemplate;

    /**
     * Constructor
     *
     * @param jdbcTemplate the JDBC template (cannot be <code>null</code>)
     */
    public VehicleJdbcGateway(final JdbcTemplate jdbcTemplate) {
        if (jdbcTemplate == null) {
            throw new IllegalArgumentException("JDBC template must be given!");
        }
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Saves a given vehicle. Note that the vehicle type, brand and model must already exist in the database.
     *
     * @param vehicle the vehicle to save (assumed not <code>null</code>)
     * @return the saved vehicle
     */
    @Override
    public Vehicle save(final Vehicle vehicle) {
        jdbcTemplate.update(
            "INSERT INTO RENT_A_CAR.VEHICLE (ID, TYPE, BRAND, MODEL, RATE, LOCATION) VALUES (?, ?, ?, ?, ?, ?)", vehicle.getId(), vehicle.getType().getType(),
            vehicle.getBrand().getName(), vehicle.getModel().getName(), vehicle.getRate(), vehicle.getLocation());
        return vehicle;
    }

    @Override
    public Optional<Vehicle> findById(final String id) {
        final List<Vehicle> vehicles = jdbcTemplate
            .query("SELECT * FROM RENT_A_CAR.VEHICLE V "
                + "JOIN RENT_A_CAR.VEHICLE_TYPE VT ON V.TYPE = VT.TYPE "
                + "JOIN RENT_A_CAR.VEHICLE_BRAND VB ON V.BRAND = VB.NAME "
                + "JOIN RENT_A_CAR.VEHICLE_MODEL VM ON V.MODEL = VM.NAME "
                + "JOIN RENT_A_CAR.LOCATION L ON V.LOCATION = L.NAME "
                + "WHERE ID = ?", mapper, new Object[]{id});
        return Optional.ofNullable(vehicles.isEmpty() ? null : vehicles.get(0));
    }

    @Override
    public List<Vehicle> search(final String type, final String brand, final String model, final BigDecimal maximumRate) {
        final List<Object> searchParameters = new ArrayList<>();
        String whereClause = new String();
        if (type != null && !type.isEmpty()) {
            searchParameters.add(type);
            whereClause = addToWhereClause(whereClause, "V.TYPE");
        }
        if (brand != null && !brand.isEmpty()) {
            searchParameters.add(brand);
            whereClause = addToWhereClause(whereClause, "V.BRAND");
        }
        if (model != null && !model.isEmpty()) {
            searchParameters.add(model);
            whereClause = addToWhereClause(whereClause, "V.MODEL");
        }
        if (maximumRate != null) {
            searchParameters.add(maximumRate);
            if (!whereClause.isEmpty()) {
                whereClause = whereClause + "AND ";
            }
            whereClause = whereClause + "V.RATE <= ? ";
        }
        if (!whereClause.isEmpty()) {
            whereClause = "WHERE " + whereClause;
        }
        final List<Vehicle> vehicles = jdbcTemplate
            .query("SELECT * FROM RENT_A_CAR.VEHICLE V "
                + "JOIN RENT_A_CAR.VEHICLE_TYPE VT ON V.TYPE = VT.TYPE "
                + "JOIN RENT_A_CAR.VEHICLE_BRAND VB ON V.BRAND = VB.NAME "
                + "JOIN RENT_A_CAR.VEHICLE_MODEL VM ON V.MODEL = VM.NAME "
                + "JOIN RENT_A_CAR.LOCATION L ON V.LOCATION = L.NAME "
                + whereClause, mapper, searchParameters.toArray());
        return vehicles;
    }

    private String addToWhereClause(String whereClause, String parameter) {
        if (!whereClause.isEmpty()) {
            whereClause = whereClause + "AND ";
        }
        whereClause = whereClause + parameter + " = ? ";
        return whereClause;
    }

    private static class VehicleRowMapper implements RowMapper<Vehicle> {

        @Override
        public Vehicle mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final Vehicle vehicle = new Vehicle(rs.getString("ID"));

            mapVehicleType(rs, vehicle);
            mapVehicleBrand(rs, vehicle);
            mapVehicleModel(rs, vehicle);
            vehicle.setRate(rs.getBigDecimal("RATE"));
            vehicle.setLocation(rs.getString("LOCATION"));

            return vehicle;
        }

        private void mapVehicleType(final ResultSet rs, final Vehicle vehicle) throws SQLException {
            final VehicleType vehicleType = new VehicleType();
            vehicleType.setType(rs.getString("TYPE"));
            vehicle.setType(vehicleType);
        }

        private void mapVehicleBrand(final ResultSet rs, final Vehicle vehicle) throws SQLException {
            final VehicleBrand vehicleBrand = new VehicleBrand();
            vehicleBrand.setName(rs.getString("BRAND"));
            vehicle.setBrand(vehicleBrand);
        }

        private void mapVehicleModel(final ResultSet rs, final Vehicle vehicle) throws SQLException {
            final VehicleModel vehicleModel = new VehicleModel();
            vehicleModel.setName(rs.getString("MODEL"));
            vehicleModel.setAc(rs.getBoolean("AC"));
            vehicleModel.setAutomaticTransmission(rs.getBoolean("AUTOMATIC_TRANSMISSION"));
            vehicleModel.setSeats(rs.getInt("SEATS"));
            vehicle.setModel(vehicleModel);
        }


    }

}
