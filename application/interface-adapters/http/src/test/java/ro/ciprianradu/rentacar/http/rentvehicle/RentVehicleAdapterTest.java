package ro.ciprianradu.rentacar.http.rentvehicle;

import java.time.ZonedDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ro.ciprianradu.rentacar.http.HttpResponse;
import ro.ciprianradu.rentacar.http.HttpStatus;
import ro.ciprianradu.rentacar.http.dtos.Reservation;
import ro.ciprianradu.rentacar.usecases.rentvehicle.RentVehicleInputData;
import ro.ciprianradu.rentacar.usecases.rentvehicle.RentVehicleInputPort;
import ro.ciprianradu.rentacar.usecases.rentvehicle.RentVehicleOutputData;
import ro.ciprianradu.rentacar.usecases.rentvehicle.RentVehicleOutputPort;

/**
 * Unit tests for {@link RentVehicleAdapter}
 */
class RentVehicleAdapterTest {

    @Test
    public void test_RentVehicleAdapter_null_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RentVehicleAdapter(null);
        });
    }

    @Test
    public void test_RentVehicleAdapter_instantiates() {
        final RentVehicleAdapter rentVehicleAdapter = new RentVehicleAdapter(new MockRentVehicleInputPort());
        Assertions.assertNotNull(rentVehicleAdapter);
    }

    @Test
    public void test_rentVehicle_returnsCreated() {
        final Reservation reservation = createValidReservation();
        final RentVehicleAdapter rentVehicleAdapter = new RentVehicleAdapter(new MockRentVehicleInputPort());
        final HttpResponse httpResponse = rentVehicleAdapter.rentVehicle(reservation);
        Assertions.assertEquals(HttpStatus.CREATED, httpResponse.getStatus());
    }

    private Reservation createValidReservation() {
        final ZonedDateTime now = ZonedDateTime.now();
        final Reservation reservation = new Reservation();
        reservation.setRenterEmail("john.doe@email.com");
        reservation.setVehicleType("type");
        reservation.setVehicleBrand("brand");
        reservation.setVehicleModel("model");
        reservation.setPickupDate(now);
        reservation.setPickupLocationName("pickup-location");
        reservation.setReturnDate(now.plusHours(1));
        reservation.setReturnLocationName("return-location");
        return reservation;
    }

    @Test
    public void test_rentVehicle_sameRent_returnsConflict() {
        final Reservation reservation = createValidReservation();
        final RentVehicleAdapter rentVehicleAdapter = new RentVehicleAdapter(new MockRentVehicleInputPort());
        rentVehicleAdapter.rentVehicle(reservation);
        final HttpResponse httpResponse = rentVehicleAdapter.rentVehicle(reservation);
        Assertions.assertEquals(HttpStatus.CONFLICT, httpResponse.getStatus());
    }

    @Test
    public void test_rentVehicle_nullReservation_returnsBadRequest() {
        final RentVehicleAdapter rentVehicleAdapter = new RentVehicleAdapter(new MockRentVehicleInputPort());
        final HttpResponse httpResponse = rentVehicleAdapter.rentVehicle(null);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatus());
    }

    @Test
    public void test_getRentId_returnsRentId() {
        final RentVehicleAdapter rentVehicleAdapter = new RentVehicleAdapter(new MockRentVehicleInputPort());
        rentVehicleAdapter.rentVehicle(createValidReservation());
        Assertions.assertEquals("rent-id", rentVehicleAdapter.getRentId());
    }

    private static class MockRentVehicleInputPort implements RentVehicleInputPort {

        private boolean alreadyRented = false;

        @Override
        public void rentVehicle(final RentVehicleInputData inputData, final RentVehicleOutputPort outputPort) {
            final RentVehicleOutputData rentVehicleOutputData = new RentVehicleOutputData(!alreadyRented, "rent-id");
            alreadyRented = true;
            outputPort.present(rentVehicleOutputData);
        }

    }

}