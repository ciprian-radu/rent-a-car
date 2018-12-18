package ro.ciprianradu.rentacar.usecases.rentvehicle;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.ciprianradu.rentacar.entity.*;
import ro.ciprianradu.rentacar.usecases.Context;
import ro.ciprianradu.rentacar.usecases.gateways.InMemoryLocationGateway;
import ro.ciprianradu.rentacar.usecases.gateways.InMemoryRenterGateway;
import ro.ciprianradu.rentacar.usecases.gateways.InMemoryReservationGateway;
import ro.ciprianradu.rentacar.usecases.gateways.InMemoryVehicleGateway;

/**
 * Unit tests for {@link RentVehicleUseCase}
 */
class RentVehicleUseCaseTest {

    @BeforeEach
    void setupContext() {
        Context.renterGateway = new InMemoryRenterGateway();
        Context.vehicleGateway = new InMemoryVehicleGateway();
        Context.locationGateway = new InMemoryLocationGateway();
        Context.reservationGateway = new InMemoryReservationGateway();
    }

    @Test
    void test_rentVehicle_rentsVehicle() {
        Renter renter = new Renter();
        renter.setEmail("john.doe@email.com");
        renter.setFirstName("John");
        renter.setLastName("Doe");
        Context.renterGateway.save(renter);
        final Vehicle vehicle = createVehicle();
        Context.vehicleGateway.save(vehicle);
        Location location = new Location();
        location.setName("Sibiu");
        location.setAddress("Sibiu Airport");
        Context.locationGateway.save(location);

        RentVehicleInputData rentVehicleInputData = new RentVehicleInputData(renter.getEmail(), vehicle.getType().getType(), vehicle.getBrand().getName(),
            vehicle.getModel().getName(), ZonedDateTime.now(), "Sibiu", ZonedDateTime.now().plus(1, ChronoUnit.DAYS), "Sibiu");

        RentVehicleUseCase rentVehicleUseCase = new RentVehicleUseCase();
        rentVehicleUseCase.rentVehicle(rentVehicleInputData, rentVehicleOutputData ->
            Assertions.assertTrue(rentVehicleOutputData.isVehicleRented())
        );
    }

    private Vehicle createVehicle() {
        Vehicle vehicle = new Vehicle("id");
        final VehicleType vehicleType = new VehicleType();
        vehicleType.setType("type");
        vehicle.setType(vehicleType);
        final VehicleBrand vehicleBrand = new VehicleBrand();
        vehicleBrand.setName("brand");
        vehicle.setBrand(vehicleBrand);
        final VehicleModel vehicleModel = new VehicleModel();
        vehicleModel.setName("model");
        vehicle.setModel(vehicleModel);
        return vehicle;
    }

    @Test
    void test_registerVehicle_sameVehicleIsRegisteredOnlyOnce() {
        Renter renter = new Renter();
        renter.setEmail("john.doe@email.com");
        renter.setFirstName("John");
        renter.setLastName("Doe");
        Context.renterGateway.save(renter);
        final Vehicle vehicle = createVehicle();
        Context.vehicleGateway.save(vehicle);
        Location location = new Location();
        location.setName("Sibiu");
        location.setAddress("Sibiu Airport");
        Context.locationGateway.save(location);

        RentVehicleInputData rentVehicleInputData = new RentVehicleInputData(renter.getEmail(), vehicle.getType().getType(), vehicle.getBrand().getName(),
            vehicle.getModel().getName(), ZonedDateTime.now(), "Sibiu",
            ZonedDateTime.now().plusDays(1), "Sibiu");

        RentVehicleUseCase rentVehicleUseCase = new RentVehicleUseCase();
        rentVehicleUseCase.rentVehicle(rentVehicleInputData, rentVehicleOutputData ->
            Assertions.assertTrue(rentVehicleOutputData.isVehicleRented())
        );
        rentVehicleUseCase.rentVehicle(rentVehicleInputData, rentVehicleOutputData ->
            Assertions.assertFalse(rentVehicleOutputData.isVehicleRented())
        );
    }

    @Test
    void test_rentVehicle_sameRentPeriod_doesNotRentVehicle() {
        Renter renter = new Renter();
        renter.setEmail("john.doe@email.com");
        renter.setFirstName("John");
        renter.setLastName("Doe");
        Context.renterGateway.save(renter);
        final Vehicle vehicle = createVehicle();
        Context.vehicleGateway.save(vehicle);
        Location location = new Location();
        location.setName("Sibiu");
        location.setAddress("Sibiu Airport");
        Context.locationGateway.save(location);

        ZonedDateTime now = ZonedDateTime.now();
        RentVehicleInputData rentVehicleInputData1 = new RentVehicleInputData(renter.getEmail(), vehicle.getType().getType(), vehicle.getBrand().getName(),
            vehicle.getModel().getName(), now, "Sibiu", now.plus(1,
            ChronoUnit.DAYS), "Sibiu");

        RentVehicleUseCase rentVehicleUseCase1 = new RentVehicleUseCase();
        rentVehicleUseCase1.rentVehicle(rentVehicleInputData1, rentVehicleOutputData ->
            Assertions.assertTrue(rentVehicleOutputData.isVehicleRented())
        );

        RentVehicleInputData rentVehicleInputData2 = new RentVehicleInputData(renter.getEmail(), vehicle.getType().getType(), vehicle.getBrand().getName(),
            vehicle.getModel().getName(), now, "Sibiu", now.plus(1,
            ChronoUnit.DAYS), "Sibiu");

        RentVehicleUseCase rentVehicleUseCase2 = new RentVehicleUseCase();
        rentVehicleUseCase2.rentVehicle(rentVehicleInputData2, rentVehicleOutputData ->
            Assertions.assertFalse(rentVehicleOutputData.isVehicleRented())
        );
    }

    @Test
    void test_rentVehicle_overlappingPastRentPeriod_doesNotRentVehicle() {
        Renter renter = new Renter();
        renter.setEmail("john.doe@email.com");
        renter.setFirstName("John");
        renter.setLastName("Doe");
        Context.renterGateway.save(renter);
        final Vehicle vehicle = createVehicle();
        Context.vehicleGateway.save(vehicle);
        Location location = new Location();
        location.setName("Sibiu");
        location.setAddress("Sibiu Airport");
        Context.locationGateway.save(location);

        ZonedDateTime now = ZonedDateTime.now();
        RentVehicleInputData rentVehicleInputData1 = new RentVehicleInputData(renter.getEmail(), vehicle.getType().getType(), vehicle.getBrand().getName(),
            vehicle.getModel().getName(), now, "Sibiu", now.plus(1,
            ChronoUnit.DAYS), "Sibiu");

        RentVehicleUseCase rentVehicleUseCase1 = new RentVehicleUseCase();
        rentVehicleUseCase1.rentVehicle(rentVehicleInputData1, rentVehicleOutputData ->
            Assertions.assertTrue(rentVehicleOutputData.isVehicleRented())
        );

        RentVehicleInputData rentVehicleInputData2 = new RentVehicleInputData(renter.getEmail(), vehicle.getType().getType(), vehicle.getBrand().getName(),
            vehicle.getModel().getName(), now.minus(1, ChronoUnit.HOURS), "Sibiu",
            now.minus(1, ChronoUnit.HOURS).plus(1,
                ChronoUnit.DAYS), "Sibiu");

        RentVehicleUseCase rentVehicleUseCase2 = new RentVehicleUseCase();
        rentVehicleUseCase2.rentVehicle(rentVehicleInputData2, rentVehicleOutputData ->
            Assertions.assertFalse(rentVehicleOutputData.isVehicleRented())
        );
    }

    @Test
    void test_rentVehicle_overlappingFutureRentPeriod_doesNotRentVehicle() {
        Renter renter = new Renter();
        renter.setEmail("john.doe@email.com");
        renter.setFirstName("John");
        renter.setLastName("Doe");
        Context.renterGateway.save(renter);
        final Vehicle vehicle = createVehicle();
        Context.vehicleGateway.save(vehicle);
        Location location = new Location();
        location.setName("Sibiu");
        location.setAddress("Sibiu Airport");
        Context.locationGateway.save(location);

        ZonedDateTime now = ZonedDateTime.now();
        RentVehicleInputData rentVehicleInputData1 = new RentVehicleInputData(renter.getEmail(), vehicle.getType().getType(), vehicle.getBrand().getName(),
            vehicle.getModel().getName(), now, "Sibiu", now.plus(1,
            ChronoUnit.DAYS), "Sibiu");

        RentVehicleUseCase rentVehicleUseCase1 = new RentVehicleUseCase();
        rentVehicleUseCase1.rentVehicle(rentVehicleInputData1, rentVehicleOutputData ->
            Assertions.assertTrue(rentVehicleOutputData.isVehicleRented())
        );

        RentVehicleInputData rentVehicleInputData2 = new RentVehicleInputData(renter.getEmail(), vehicle.getType().getType(), vehicle.getBrand().getName(),
            vehicle.getModel().getName(), now.plus(1, ChronoUnit.HOURS), "Sibiu",
            now.plus(1, ChronoUnit.HOURS).plus(1,
                ChronoUnit.DAYS), "Sibiu");

        RentVehicleUseCase rentVehicleUseCase2 = new RentVehicleUseCase();
        rentVehicleUseCase2.rentVehicle(rentVehicleInputData2, rentVehicleOutputData ->
            Assertions.assertFalse(rentVehicleOutputData.isVehicleRented())
        );
    }

}