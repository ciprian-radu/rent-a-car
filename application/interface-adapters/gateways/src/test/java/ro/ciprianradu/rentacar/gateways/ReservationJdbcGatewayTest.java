package ro.ciprianradu.rentacar.gateways;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import ro.ciprianradu.rentacar.entity.Location;
import ro.ciprianradu.rentacar.entity.Renter;
import ro.ciprianradu.rentacar.entity.Reservation;
import ro.ciprianradu.rentacar.entity.Vehicle;
import ro.ciprianradu.rentacar.entity.VehicleBrand;
import ro.ciprianradu.rentacar.entity.VehicleModel;
import ro.ciprianradu.rentacar.entity.VehicleType;
import ro.ciprianradu.rentacar.usecases.gateways.LocationGateway;
import ro.ciprianradu.rentacar.usecases.gateways.RenterGateway;
import ro.ciprianradu.rentacar.usecases.gateways.ReservationGateway;
import ro.ciprianradu.rentacar.usecases.gateways.VehicleBrandGateway;
import ro.ciprianradu.rentacar.usecases.gateways.VehicleGateway;
import ro.ciprianradu.rentacar.usecases.gateways.VehicleModelGateway;
import ro.ciprianradu.rentacar.usecases.gateways.VehicleTypeGateway;

/**
 *
 */
class ReservationJdbcGatewayTest {

    private ReservationGateway reservationGateway = new ReservationJdbcGateway(
        JdbcTemplateFactory.createJdbcTemplate());

    private RenterGateway renterGateway = new RenterJdbcGateway(
        JdbcTemplateFactory.createJdbcTemplate());

    private VehicleGateway vehicleGateway = new VehicleJdbcGateway(
        JdbcTemplateFactory.createJdbcTemplate());

    private VehicleTypeGateway vehicleTypeGateway = new VehicleTypeJdbcGateway(
        JdbcTemplateFactory.createJdbcTemplate());

    private VehicleBrandGateway vehicleBrandGateway = new VehicleBrandJdbcGateway(
        JdbcTemplateFactory.createJdbcTemplate());

    private VehicleModelGateway vehicleModelGateway = new VehicleModelJdbcGateway(
        JdbcTemplateFactory.createJdbcTemplate());

    private LocationGateway locationGateway = new LocationJdbcGateway(
        JdbcTemplateFactory.createJdbcTemplate());

    @Test
    void test_save_reservation_saves() {
        ZonedDateTime now = ZonedDateTime.now();
        final Reservation reservation = createReservation("john.doe@email.com", "vehicle id 1", now,
            now.plusDays(1));
        reservationGateway.save(reservation);
        Assertions.assertNotNull(reservation);
    }

    @Test
    void test_save_duplicate_doesNotSave() {
        ZonedDateTime now = ZonedDateTime.now();
        final Reservation reservation = createReservation("john.doe@email.com", "vehicle id 1", now,
            now.plusDays(1));
        reservationGateway.save(reservation);
        Assertions
            .assertThrows(DuplicateKeyException.class, () -> reservationGateway.save(reservation));
    }

    @Test
    void test_findAllByVehicle_returnsAllMatchingReservations() {
        ZonedDateTime now = ZonedDateTime.now();
        Reservation reservation = createReservation("john.doe@email.com", "vehicle id 1", now,
            now.plusDays(1));
        reservationGateway.save(reservation);
        reservation = createReservation("john.doe@email.com", "vehicle id 2", now, now.plusDays(1));
        reservationGateway.save(reservation);
        reservation = createReservation("jane.doe@email.com", "vehicle id 1", now, now.plusDays(1));
        reservationGateway.save(reservation);
        final Iterable<Reservation> allByVehicle = reservationGateway
            .findAllByVehicle("vehicle id 1");
        final List<Reservation> reservations = new ArrayList<>();
        for (Reservation r : allByVehicle) {
            reservations.add(r);
        }
        Assertions.assertEquals(2, reservations.size());
    }

    @Test
    void test_findByOverlappingRentPeriod_sameRentPeriod_returnsReservation() {
        ZonedDateTime now = ZonedDateTime.now();
        Reservation reservation = createReservation("john.doe@email.com", "vehicle id 1", now,
            now.plusDays(1));
        reservationGateway.save(reservation);
        reservation = createReservation("john.doe@email.com", "vehicle id 2", now, now.plusDays(1));
        reservationGateway.save(reservation);
        reservation = createReservation("jane.doe@email.com", "vehicle id 1", now, now.plusDays(1));
        reservationGateway.save(reservation);

        Reservation submittedReservation = createReservation("jane.doe@email.com", "vehicle id 1",
            now, now.plusDays(1));
        Assertions.assertTrue(reservationGateway
            .findByOverlappingRentPeriod(submittedReservation.getVehicle().getId(),
                submittedReservation.getPickupDate(), submittedReservation.getReturnDate())
            .isPresent());
    }

    @Test
    void test_findByOverlappingRentPeriod_differentRentPeriod_returnsNoReservation() {
        ZonedDateTime now = ZonedDateTime.now();
        Reservation reservation = createReservation("john.doe@email.com", "vehicle id 1", now,
            now.plusDays(1));
        reservationGateway.save(reservation);
        reservation = createReservation("john.doe@email.com", "vehicle id 2", now, now.plusDays(1));
        reservationGateway.save(reservation);
        reservation = createReservation("jane.doe@email.com", "vehicle id 1", now, now.plusDays(1));
        reservationGateway.save(reservation);

        Reservation submittedReservation = createReservation("jane.doe@email.com", "vehicle id 1",
            now.plusDays(1).plusSeconds(1), now.plusDays(2));
        Assertions.assertFalse(reservationGateway
            .findByOverlappingRentPeriod(submittedReservation.getVehicle().getId(),
                submittedReservation.getPickupDate(), submittedReservation.getReturnDate())
            .isPresent());
    }

    @Test
    void test_findByOverlappingRentPeriod_overlappingRentPeriod_returnsReservation() {
        ZonedDateTime reservationPickupDate = ZonedDateTime
            .of(2019, 8, 14, 0, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime reservationReturnDate = ZonedDateTime
            .of(2019, 8, 15, 23, 59, 59, 999, ZoneId.systemDefault());
        Reservation reservation = createReservation("john.doe@email.com", "vehicle id 1",
            reservationPickupDate, reservationReturnDate);
        reservationGateway.save(reservation);

        ZonedDateTime submittedReservationPickupDate = ZonedDateTime
            .of(2019, 8, 15, 0, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime submittedReservationReturnDate = ZonedDateTime
            .of(2019, 8, 16, 23, 59, 59, 999, ZoneId.systemDefault());

        Reservation submittedReservation = createReservation("jane.doe@email.com", "vehicle id 1",
            submittedReservationPickupDate, submittedReservationReturnDate);
        Assertions.assertTrue(reservationGateway
            .findByOverlappingRentPeriod(submittedReservation.getVehicle().getId(),
                submittedReservation.getPickupDate(), submittedReservation.getReturnDate())
            .isPresent());
    }

    @Test
    void test_findByOverlappingRentPeriod_differentVehicle_returnsNoReservation() {
        ZonedDateTime reservationPickupDate = ZonedDateTime
            .of(2019, 9, 25, 0, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime reservationReturnDate = ZonedDateTime
            .of(2019, 9, 26, 23, 59, 59, 999, ZoneId.systemDefault());
        Reservation reservation = createReservation("john.doe@email.com", "SB-04-AAA",
            reservationPickupDate, reservationReturnDate);
        reservationGateway.save(reservation);
        // We reserved vehicle SB-04-AAA. We want to reserve another vehicle (SB-03-AAA), during a period that overlaps with the period of SB-04-AAA.
        // The vehicles are obviously different, so there is no problem with making such reservation.
        // If we would have hd the same vehicle, the reservation would not be possible.

        ZonedDateTime submittedReservationPickupDate = ZonedDateTime
            .of(2019, 9, 26, 0, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime submittedReservationReturnDate = ZonedDateTime
            .of(2019, 9, 27, 23, 59, 59, 999, ZoneId.systemDefault());

        Reservation submittedReservation = createReservation("john.doe@email.com", "SB-03-AAA",
            submittedReservationPickupDate, submittedReservationReturnDate);
        Assertions.assertFalse(reservationGateway
            .findByOverlappingRentPeriod(submittedReservation.getVehicle().getId(),
                submittedReservation.getPickupDate(), submittedReservation.getReturnDate())
            .isPresent());
    }

    private Reservation createReservation(final String email, final String vehicleId,
        final ZonedDateTime pickupDate, final ZonedDateTime returnDate) {
        final Reservation reservation = new Reservation();
        reservation.setRenter(setupRenter(email));
        final Location location = setupLocation();
        reservation.setVehicle(setupVehicle(vehicleId, BigDecimal.ONE, location));
        reservation.setPickupDate(pickupDate);
        reservation.setPickupLocation(location);
        reservation.setReturnDate(returnDate);
        reservation.setReturnLocation(location);

        return reservation;
    }

    private Renter setupRenter(final String email) {
        final Renter renter = new Renter();
        renter.setFirstName("John");
        renter.setLastName("Doe");
        renter.setEmail(email);
        if (!renterGateway.findByEmail(renter.getEmail()).isPresent()) {
            renterGateway.save(renter);
        }
        return renter;
    }

    private Vehicle setupVehicle(final String id, final BigDecimal rate, final Location location) {
        final Vehicle vehicle = new Vehicle(id);
        final VehicleType vehicleType = createVehicleType();
        vehicle.setType(vehicleType);
        VehicleBrand vehicleBrand = createVehicleBrand();
        vehicle.setBrand(vehicleBrand);
        VehicleModel vehicleModel = createVehicleModel();
        vehicle.setModel(vehicleModel);
        saveVehicleDependencies(vehicleType, vehicleBrand, vehicleModel);
        vehicle.setRate(rate);
        vehicle.setLocation(location.getName());
        if (!vehicleGateway.findById(id).isPresent()) {
            vehicleGateway.save(vehicle);
        }
        return vehicle;
    }

    private void saveVehicleDependencies(final VehicleType vehicleType,
        final VehicleBrand vehicleBrand, final VehicleModel vehicleModel) {
        if (!vehicleTypeGateway.findByType(vehicleType.getType()).isPresent()) {
            vehicleTypeGateway.save(vehicleType);
        }
        if (!vehicleBrandGateway.findByBrand(vehicleBrand.getName()).isPresent()) {
            vehicleBrandGateway.save(vehicleBrand);
        }
        if (!vehicleModelGateway.findByModel(vehicleModel.getName()).isPresent()) {
            vehicleModelGateway.save(vehicleModel);
        }
    }

    private VehicleType createVehicleType() {
        final VehicleType vehicleType = new VehicleType();
        vehicleType.setType("type");
        return vehicleType;
    }

    private VehicleBrand createVehicleBrand() {
        final VehicleBrand vehicleBrand = new VehicleBrand();
        vehicleBrand.setName("brand");
        return vehicleBrand;
    }

    private VehicleModel createVehicleModel() {
        final VehicleModel vehicleModel = new VehicleModel();
        vehicleModel.setName("model");
        vehicleModel.setAutomaticTransmission(true);
        vehicleModel.setSeats(5);
        return vehicleModel;
    }

    private Location setupLocation() {
        final Location location = new Location();
        location.setName("SBZ");
        location.setAddress("Sibiu Airport");
        if (!locationGateway.findByName(location.getName()).isPresent()) {
            locationGateway.save(location);
        }
        return location;
    }

}