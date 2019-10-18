package ro.ciprianradu.rentacar.usecases.rentvehicle;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.ciprianradu.rentacar.entity.Location;
import ro.ciprianradu.rentacar.entity.Renter;
import ro.ciprianradu.rentacar.entity.Vehicle;
import ro.ciprianradu.rentacar.entity.VehicleBrand;
import ro.ciprianradu.rentacar.entity.VehicleModel;
import ro.ciprianradu.rentacar.entity.VehicleType;
import ro.ciprianradu.rentacar.usecases.gateways.GatewaysAccessor;
import ro.ciprianradu.rentacar.usecases.gateways.InMemoryLocationGateway;
import ro.ciprianradu.rentacar.usecases.gateways.InMemoryRenterGateway;
import ro.ciprianradu.rentacar.usecases.gateways.InMemoryReservationGateway;
import ro.ciprianradu.rentacar.usecases.gateways.InMemoryVehicleGateway;
import ro.ciprianradu.rentacar.usecases.gateways.LocationGateway;
import ro.ciprianradu.rentacar.usecases.gateways.RenterGateway;
import ro.ciprianradu.rentacar.usecases.gateways.ReservationGateway;
import ro.ciprianradu.rentacar.usecases.gateways.VehicleGateway;

/**
 * Unit tests for {@link RentVehicleUseCase}
 */
class RentVehicleUseCaseTest {

    private GatewaysAccessor gatewaysAccessor = new GatewaysAccessor();

    private RenterGateway renterGateway = new InMemoryRenterGateway();

    private VehicleGateway vehicleGateway = new InMemoryVehicleGateway();

    private LocationGateway locationGateway = new InMemoryLocationGateway();

    private ReservationGateway reservationGateway = new InMemoryReservationGateway();

    @BeforeEach
    void setupContext() {
        gatewaysAccessor.setRenterGateway(renterGateway);
        gatewaysAccessor.setVehicleGateway(vehicleGateway);
        gatewaysAccessor.setLocationGateway(locationGateway);
        gatewaysAccessor.setReservationGateway(reservationGateway);
    }

    @Test
    void test_rentVehicle_rentsVehicle() {
        Renter renter = new Renter();
        renter.setEmail("john.doe@email.com");
        renter.setFirstName("John");
        renter.setLastName("Doe");
        renterGateway.save(renter);
        final Vehicle vehicle = createVehicle();
        vehicleGateway.save(vehicle);
        Location location = new Location();
        location.setName("Sibiu");
        location.setAddress("Sibiu Airport");
        locationGateway.save(location);

        RentVehicleInputData rentVehicleInputData = new RentVehicleInputData(renter.getEmail(),
            vehicle.getType().getType(), vehicle.getBrand().getName(), vehicle.getModel().getName(),
            ZonedDateTime.now(), "Sibiu", ZonedDateTime.now().plus(1, ChronoUnit.DAYS), "Sibiu");

        RentVehicleUseCase rentVehicleUseCase = new RentVehicleUseCase(gatewaysAccessor);
        rentVehicleUseCase.rentVehicle(rentVehicleInputData, rentVehicleOutputData -> Assertions
            .assertTrue(rentVehicleOutputData.isVehicleRented()));
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
        renterGateway.save(renter);
        final Vehicle vehicle = createVehicle();
        vehicleGateway.save(vehicle);
        Location location = new Location();
        location.setName("Sibiu");
        location.setAddress("Sibiu Airport");
        locationGateway.save(location);

        RentVehicleInputData rentVehicleInputData = new RentVehicleInputData(renter.getEmail(),
            vehicle.getType().getType(), vehicle.getBrand().getName(), vehicle.getModel().getName(),
            ZonedDateTime.now(), "Sibiu", ZonedDateTime.now().plusDays(1), "Sibiu");

        RentVehicleUseCase rentVehicleUseCase = new RentVehicleUseCase(gatewaysAccessor);
        rentVehicleUseCase.rentVehicle(rentVehicleInputData, rentVehicleOutputData -> Assertions
            .assertTrue(rentVehicleOutputData.isVehicleRented()));
        rentVehicleUseCase.rentVehicle(rentVehicleInputData, rentVehicleOutputData -> Assertions
            .assertFalse(rentVehicleOutputData.isVehicleRented()));
    }

    @Test
    void test_rentVehicle_sameRentPeriod_doesNotRentVehicle() {
        Renter renter = new Renter();
        renter.setEmail("john.doe@email.com");
        renter.setFirstName("John");
        renter.setLastName("Doe");
        renterGateway.save(renter);
        final Vehicle vehicle = createVehicle();
        vehicleGateway.save(vehicle);
        Location location = new Location();
        location.setName("Sibiu");
        location.setAddress("Sibiu Airport");
        locationGateway.save(location);

        ZonedDateTime now = ZonedDateTime.now();
        RentVehicleInputData rentVehicleInputData1 = new RentVehicleInputData(renter.getEmail(),
            vehicle.getType().getType(), vehicle.getBrand().getName(), vehicle.getModel().getName(),
            now, "Sibiu", now.plus(1, ChronoUnit.DAYS), "Sibiu");

        RentVehicleUseCase rentVehicleUseCase1 = new RentVehicleUseCase(gatewaysAccessor);
        rentVehicleUseCase1.rentVehicle(rentVehicleInputData1, rentVehicleOutputData -> Assertions
            .assertTrue(rentVehicleOutputData.isVehicleRented()));

        RentVehicleInputData rentVehicleInputData2 = new RentVehicleInputData(renter.getEmail(),
            vehicle.getType().getType(), vehicle.getBrand().getName(), vehicle.getModel().getName(),
            now, "Sibiu", now.plus(1, ChronoUnit.DAYS), "Sibiu");

        RentVehicleUseCase rentVehicleUseCase2 = new RentVehicleUseCase(gatewaysAccessor);
        rentVehicleUseCase2.rentVehicle(rentVehicleInputData2, rentVehicleOutputData -> Assertions
            .assertFalse(rentVehicleOutputData.isVehicleRented()));
    }

    @Test
    void test_rentVehicle_overlappingPastRentPeriod_doesNotRentVehicle() {
        Renter renter = new Renter();
        renter.setEmail("john.doe@email.com");
        renter.setFirstName("John");
        renter.setLastName("Doe");
        renterGateway.save(renter);
        final Vehicle vehicle = createVehicle();
        vehicleGateway.save(vehicle);
        Location location = new Location();
        location.setName("Sibiu");
        location.setAddress("Sibiu Airport");
        locationGateway.save(location);

        ZonedDateTime now = ZonedDateTime.now();
        RentVehicleInputData rentVehicleInputData1 = new RentVehicleInputData(renter.getEmail(),
            vehicle.getType().getType(), vehicle.getBrand().getName(), vehicle.getModel().getName(),
            now, "Sibiu", now.plus(1, ChronoUnit.DAYS), "Sibiu");

        RentVehicleUseCase rentVehicleUseCase1 = new RentVehicleUseCase(gatewaysAccessor);
        rentVehicleUseCase1.rentVehicle(rentVehicleInputData1, rentVehicleOutputData -> Assertions
            .assertTrue(rentVehicleOutputData.isVehicleRented()));

        RentVehicleInputData rentVehicleInputData2 = new RentVehicleInputData(renter.getEmail(),
            vehicle.getType().getType(), vehicle.getBrand().getName(), vehicle.getModel().getName(),
            now.minus(1, ChronoUnit.HOURS), "Sibiu",
            now.minus(1, ChronoUnit.HOURS).plus(1, ChronoUnit.DAYS), "Sibiu");

        RentVehicleUseCase rentVehicleUseCase2 = new RentVehicleUseCase(gatewaysAccessor);
        rentVehicleUseCase2.rentVehicle(rentVehicleInputData2, rentVehicleOutputData -> Assertions
            .assertFalse(rentVehicleOutputData.isVehicleRented()));
    }

    @Test
    void test_rentVehicle_overlappingFutureRentPeriod_doesNotRentVehicle() {
        Renter renter = new Renter();
        renter.setEmail("john.doe@email.com");
        renter.setFirstName("John");
        renter.setLastName("Doe");
        renterGateway.save(renter);
        final Vehicle vehicle = createVehicle();
        vehicleGateway.save(vehicle);
        Location location = new Location();
        location.setName("Sibiu");
        location.setAddress("Sibiu Airport");
        locationGateway.save(location);

        ZonedDateTime now = ZonedDateTime.now();
        RentVehicleInputData rentVehicleInputData1 = new RentVehicleInputData(renter.getEmail(),
            vehicle.getType().getType(), vehicle.getBrand().getName(), vehicle.getModel().getName(),
            now, "Sibiu", now.plus(1, ChronoUnit.DAYS), "Sibiu");

        RentVehicleUseCase rentVehicleUseCase1 = new RentVehicleUseCase(gatewaysAccessor);
        rentVehicleUseCase1.rentVehicle(rentVehicleInputData1, rentVehicleOutputData -> Assertions
            .assertTrue(rentVehicleOutputData.isVehicleRented()));

        RentVehicleInputData rentVehicleInputData2 = new RentVehicleInputData(renter.getEmail(),
            vehicle.getType().getType(), vehicle.getBrand().getName(), vehicle.getModel().getName(),
            now.plus(1, ChronoUnit.HOURS), "Sibiu",
            now.plus(1, ChronoUnit.HOURS).plus(1, ChronoUnit.DAYS), "Sibiu");

        RentVehicleUseCase rentVehicleUseCase2 = new RentVehicleUseCase(gatewaysAccessor);
        rentVehicleUseCase2.rentVehicle(rentVehicleInputData2, rentVehicleOutputData -> Assertions
            .assertFalse(rentVehicleOutputData.isVehicleRented()));
    }

}