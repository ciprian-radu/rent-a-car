package ro.ciprianradu.rentacar.gateways;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import ro.ciprianradu.rentacar.entity.*;
import ro.ciprianradu.rentacar.usecases.Context;
import ro.ciprianradu.rentacar.usecases.gateways.*;

/**
 *
 */
class ReservationJdbcGatewayTest {

    @BeforeEach
    void setup() {
        final ReservationGateway reservationGateway = new ReservationJdbcGateway(JdbcTemplateFactory.createJdbcTemplate());
        Context.reservationGateway = reservationGateway;
        final RenterGateway renterGateway = new RenterJdbcGateway(JdbcTemplateFactory.createJdbcTemplate());
        Context.renterGateway = renterGateway;
        final VehicleGateway vehicleGateway = new VehicleJdbcGateway(JdbcTemplateFactory.createJdbcTemplate());
        Context.vehicleGateway = vehicleGateway;
        final VehicleTypeGateway vehicleTypeGateway = new VehicleTypeJdbcGateway(JdbcTemplateFactory.createJdbcTemplate());
        Context.vehicleTypeGateway = vehicleTypeGateway;
        final VehicleBrandGateway vehicleBrandGateway = new VehicleBrandJdbcGateway(JdbcTemplateFactory.createJdbcTemplate());
        Context.vehicleBrandGateway = vehicleBrandGateway;
        final VehicleModelGateway vehicleModelGateway = new VehicleModelJdbcGateway(JdbcTemplateFactory.createJdbcTemplate());
        Context.vehicleModelGateway = vehicleModelGateway;
        final LocationGateway locationGateway = new LocationJdbcGateway(JdbcTemplateFactory.createJdbcTemplate());
        Context.locationGateway = locationGateway;
    }

    @Test
    void test_save_reservation_saves() {
        ZonedDateTime now = ZonedDateTime.now();
        final Reservation reservation = createReservation("john.doe@email.com", "vehicle id 1", now, now.plusDays(1));
        Context.reservationGateway.save(reservation);
    }

    @Test
    void test_save_duplicate_doesNotSave() {
        ZonedDateTime now = ZonedDateTime.now();
        final Reservation reservation = createReservation("john.doe@email.com", "vehicle id 1", now, now.plusDays(1));
        Context.reservationGateway.save(reservation);
        Assertions.assertThrows(DuplicateKeyException.class, () -> Context.reservationGateway.save(reservation));
    }

    @Test
    void test_findAllByVehicle_returnsAllMatchingReservations() {
        ZonedDateTime now = ZonedDateTime.now();
        Reservation reservation = createReservation("john.doe@email.com", "vehicle id 1", now, now.plusDays(1));
        Context.reservationGateway.save(reservation);
        reservation = createReservation("john.doe@email.com", "vehicle id 2", now, now.plusDays(1));
        Context.reservationGateway.save(reservation);
        reservation = createReservation("jane.doe@email.com", "vehicle id 1", now, now.plusDays(1));
        Context.reservationGateway.save(reservation);
        final Iterable<Reservation> allByVehicle = Context.reservationGateway.findAllByVehicle("vehicle id 1");
        final List<Reservation> reservations = new ArrayList<>();
        for (Reservation r : allByVehicle) {
            reservations.add(r);
        }
        Assertions.assertEquals(2, reservations.size());
    }

    @Test
    void test_findByOverlappingRentPeriod_sameRentPeriod_returnsReservation() {
        ZonedDateTime now = ZonedDateTime.now();
        Reservation reservation = createReservation("john.doe@email.com", "vehicle id 1", now, now.plusDays(1));
        Context.reservationGateway.save(reservation);
        reservation = createReservation("john.doe@email.com", "vehicle id 2", now, now.plusDays(1));
        Context.reservationGateway.save(reservation);
        reservation = createReservation("jane.doe@email.com", "vehicle id 1", now, now.plusDays(1));
        Context.reservationGateway.save(reservation);

        Reservation submittedReservation = createReservation("jane.doe@email.com", "vehicle id 1", now, now.plusDays(1));
        Assertions.assertTrue(Context.reservationGateway
            .findByOverlappingRentPeriod(submittedReservation.getVehicle().getId(), submittedReservation.getPickupDate(), submittedReservation.getReturnDate())
            .isPresent());
    }

    @Test
    void test_findByOverlappingRentPeriod_differentRentPeriod_returnsNoReservation() {
        ZonedDateTime now = ZonedDateTime.now();
        Reservation reservation = createReservation("john.doe@email.com", "vehicle id 1", now, now.plusDays(1));
        Context.reservationGateway.save(reservation);
        reservation = createReservation("john.doe@email.com", "vehicle id 2", now, now.plusDays(1));
        Context.reservationGateway.save(reservation);
        reservation = createReservation("jane.doe@email.com", "vehicle id 1", now, now.plusDays(1));
        Context.reservationGateway.save(reservation);

        Reservation submittedReservation = createReservation("jane.doe@email.com", "vehicle id 1", now.plusDays(1), now.plusDays(2));
        Assertions.assertFalse(Context.reservationGateway
            .findByOverlappingRentPeriod(submittedReservation.getVehicle().getId(), submittedReservation.getPickupDate(), submittedReservation.getReturnDate())
            .isPresent());
    }

    private Reservation createReservation(final String email, final String vehicleId, final ZonedDateTime pickupDate, final ZonedDateTime returnDate) {
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
        if (!Context.renterGateway.findByEmail(renter.getEmail()).isPresent()) {
            Context.renterGateway.save(renter);
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
        if (!Context.vehicleGateway.findById(id).isPresent()) {
            Context.vehicleGateway.save(vehicle);
        }
        return vehicle;
    }

    private void saveVehicleDependencies(final VehicleType vehicleType, final VehicleBrand vehicleBrand, final VehicleModel vehicleModel) {
        if (!Context.vehicleTypeGateway.findByType(vehicleType.getType()).isPresent()) {
            Context.vehicleTypeGateway.save(vehicleType);
        }
        if (!Context.vehicleBrandGateway.findByBrand(vehicleBrand.getName()).isPresent()) {
            Context.vehicleBrandGateway.save(vehicleBrand);
        }
        if (!Context.vehicleModelGateway.findByModel(vehicleModel.getName()).isPresent()) {
            Context.vehicleModelGateway.save(vehicleModel);
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
        if (!Context.locationGateway.findByName(location.getName()).isPresent()) {
            Context.locationGateway.save(location);
        }
        return location;
    }

}