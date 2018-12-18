package ro.ciprianradu.rentacar.rest.controllers;

import java.math.BigDecimal;
import java.net.URI;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.ciprianradu.rentacar.http.dtos.Location;
import ro.ciprianradu.rentacar.http.dtos.Renter;
import ro.ciprianradu.rentacar.http.dtos.Reservation;
import ro.ciprianradu.rentacar.http.dtos.Vehicle;
import ro.ciprianradu.rentacar.usecases.rentvehicle.RentVehicleInputData;
import ro.ciprianradu.rentacar.usecases.rentvehicle.RentVehicleInputPort;
import ro.ciprianradu.rentacar.usecases.rentvehicle.RentVehicleOutputData;
import ro.ciprianradu.rentacar.usecases.rentvehicle.RentVehicleOutputPort;

/**
 *
 */
class RentVehicleControllerTest {

    private static Renter createRenter() {
        final Renter renter = new Renter();

        renter.setFirstName("John");
        renter.setLastName("Doe");
        renter.setEmail("john.doe@email.com");

        return renter;
    }

    private static Vehicle createVehicle() {
        final Vehicle vehicle = new Vehicle();

        vehicle.setId("license-plate");
        vehicle.setType("Economy Car");
        vehicle.setBrand("BMW");
        vehicle.setModel("Series 1");
        vehicle.setRate(BigDecimal.TEN);

        return vehicle;
    }

    private static Location createLocation() {
        final Location location = new Location();

        location.setName("SBZ");

        return location;
    }

    @Test
    void test_rentVehicle_null_doesNotRent() {
        final RentVehicleController controller = new RentVehicleController(new MockRentVehicleInputPort());
        final ResponseEntity<?> responseEntity = controller.rentVehicle(null);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void test_rentVehicle_reservation_rents() {
        final RentVehicleController controller = new RentVehicleController(new MockRentVehicleInputPort());
        final Reservation reservation = createReservation();

        final ResponseEntity<?> responseEntity = controller.rentVehicle(reservation);
        Assertions.assertEquals(ResponseEntity.created(URI.create(RentVehicleController.ENDPOINT + "/" + reservation.getId())).body(reservation),
            responseEntity);
    }

    @Test
    void test_rentVehicle_reservation_returnsRentId() {
        final RentVehicleController controller = new RentVehicleController(new MockRentVehicleInputPort());
        final Reservation reservation = createReservation();
        controller.rentVehicle(reservation);
        Assertions.assertFalse(reservation.getId().isEmpty());
    }

    private Reservation createReservation() {
        final Reservation reservation = new Reservation();

        reservation.setRenterEmail(createRenter().getEmail());
        final Vehicle vehicle = createVehicle();
        reservation.setVehicleType(vehicle.getType());
        reservation.setVehicleBrand(vehicle.getBrand());
        reservation.setVehicleModel(vehicle.getModel());
        final ZonedDateTime now = ZonedDateTime.now();
        reservation.setPickupDate(now);
        reservation.setPickupLocationName(createLocation().getName());
        reservation.setReturnDate(now.plusDays(1));
        reservation.setReturnLocationName(createLocation().getName());

        return reservation;
    }

    private static class MockRentVehicleInputPort implements RentVehicleInputPort {

        @Override
        public void rentVehicle(final RentVehicleInputData inputData, final RentVehicleOutputPort outputPort) {
            outputPort.present(new RentVehicleOutputData(true, UUID.randomUUID().toString()));
        }

    }

}