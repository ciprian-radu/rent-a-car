package ro.ciprianradu.rentacar.usecases.searchvehicles;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.ciprianradu.rentacar.entity.Location;
import ro.ciprianradu.rentacar.entity.Renter;
import ro.ciprianradu.rentacar.usecases.gateways.GatewaysAccessor;
import ro.ciprianradu.rentacar.usecases.gateways.InMemoryLocationGateway;
import ro.ciprianradu.rentacar.usecases.gateways.InMemoryRenterGateway;
import ro.ciprianradu.rentacar.usecases.gateways.InMemoryReservationGateway;
import ro.ciprianradu.rentacar.usecases.gateways.InMemoryVehicleBrandGateway;
import ro.ciprianradu.rentacar.usecases.gateways.InMemoryVehicleGateway;
import ro.ciprianradu.rentacar.usecases.gateways.InMemoryVehicleModelGateway;
import ro.ciprianradu.rentacar.usecases.gateways.InMemoryVehicleTypeGateway;
import ro.ciprianradu.rentacar.usecases.gateways.LocationGateway;
import ro.ciprianradu.rentacar.usecases.gateways.RenterGateway;
import ro.ciprianradu.rentacar.usecases.gateways.ReservationGateway;
import ro.ciprianradu.rentacar.usecases.gateways.VehicleBrandGateway;
import ro.ciprianradu.rentacar.usecases.gateways.VehicleGateway;
import ro.ciprianradu.rentacar.usecases.gateways.VehicleModelGateway;
import ro.ciprianradu.rentacar.usecases.gateways.VehicleTypeGateway;
import ro.ciprianradu.rentacar.usecases.registervehicle.RegisterVehicleInputData;
import ro.ciprianradu.rentacar.usecases.registervehicle.RegisterVehicleUseCase;
import ro.ciprianradu.rentacar.usecases.rentvehicle.RentVehicleInputData;
import ro.ciprianradu.rentacar.usecases.rentvehicle.RentVehicleUseCase;

/**
 * Unit tests for {@link SearchVehiclesUseCase}
 */
class SearchVehiclesUseCaseTest {

    private GatewaysAccessor gatewaysAccessor = new GatewaysAccessor();

    private RenterGateway renterGateway = new InMemoryRenterGateway();

    private VehicleGateway vehicleGateway = new InMemoryVehicleGateway();

    private VehicleTypeGateway vehicleTypeGateway = new InMemoryVehicleTypeGateway();

    private VehicleBrandGateway vehicleBrandGateway = new InMemoryVehicleBrandGateway();

    private VehicleModelGateway vehicleModelGateway = new InMemoryVehicleModelGateway();

    private LocationGateway locationGateway = new InMemoryLocationGateway();

    private ReservationGateway reservationGateway = new InMemoryReservationGateway();

    @BeforeEach
    void setupContext() {
        gatewaysAccessor.setRenterGateway(renterGateway);
        gatewaysAccessor.setLocationGateway(locationGateway);
        gatewaysAccessor.setReservationGateway(reservationGateway);
        gatewaysAccessor.setVehicleGateway(vehicleGateway);
        gatewaysAccessor.setVehicleTypeGateway(vehicleTypeGateway);
        gatewaysAccessor.setVehicleModelGateway(vehicleModelGateway);
        gatewaysAccessor.setVehicleBrandGateway(vehicleBrandGateway);

        Renter renter = new Renter();
        renter.setEmail("email");
        renterGateway.save(renter);

        final RegisterVehicleUseCase registerVehicleUseCase = new RegisterVehicleUseCase(
            gatewaysAccessor);
        RegisterVehicleInputData registerVehicleInputData = new RegisterVehicleInputData("1",
            "Economy Car", "Dacia", "Logan", BigDecimal.valueOf(5), "SBZ");
        registerVehicleUseCase.registerVehicle(registerVehicleInputData,
            registerVehicleOutputData -> Assertions
                .assertTrue(registerVehicleOutputData.isVehicleRegistered()));

        registerVehicleInputData = new RegisterVehicleInputData("2", "Economy Car", "Dacia",
            "Sandero", BigDecimal.valueOf(6), "SBZ");
        registerVehicleUseCase.registerVehicle(registerVehicleInputData,
            registerVehicleOutputData -> Assertions
                .assertTrue(registerVehicleOutputData.isVehicleRegistered()));

        registerVehicleInputData = new RegisterVehicleInputData("3", "Economy Car", "Ford",
            "Fiesta", BigDecimal.valueOf(5), "SBZ");
        registerVehicleUseCase.registerVehicle(registerVehicleInputData,
            registerVehicleOutputData -> Assertions
                .assertTrue(registerVehicleOutputData.isVehicleRegistered()));

        Location location = new Location();
        location.setName("SBZ");
        location.setAddress("Sibiu Airport");
        locationGateway.save(location);
    }

    @Test
    void test_searchVehicles_returnsAllResults() {
        SearchVehiclesUseCase useCase = new SearchVehiclesUseCase(gatewaysAccessor);
        final ZonedDateTime pickupDate = ZonedDateTime.now();
        final ZonedDateTime returnDate = pickupDate.plusDays(1);
        final SearchVehiclesInputData inputData = new SearchVehiclesInputData(pickupDate, "SBZ",
            returnDate, "SBZ");
        useCase.searchVehicles(inputData, outputData -> {
            Assertions.assertEquals(3, outputData.getVehicleCategories().size());
        });
    }

    @Test
    void test_searchVehicles_vehiclesRented_returnsNoResults() {
        RentVehicleUseCase rentVehicleUseCase = new RentVehicleUseCase(gatewaysAccessor);
        rentVehicleUseCase.rentVehicle(
            new RentVehicleInputData("email", "Economy Car", "Dacia", "Logan", ZonedDateTime.now(),
                "SBZ", ZonedDateTime.now().plus(1, ChronoUnit.DAYS), "SBZ"),
            rentVehicleOutputData -> Assertions
                .assertTrue(rentVehicleOutputData.isVehicleRented()));
        rentVehicleUseCase.rentVehicle(
            new RentVehicleInputData("email", "Economy Car", "Dacia", "Sandero",
                ZonedDateTime.now(), "SBZ", ZonedDateTime.now().plus(1, ChronoUnit.DAYS), "SBZ"),
            rentVehicleOutputData -> Assertions
                .assertTrue(rentVehicleOutputData.isVehicleRented()));
        rentVehicleUseCase.rentVehicle(
            new RentVehicleInputData("email", "Economy Car", "Ford", "Fiesta", ZonedDateTime.now(),
                "SBZ", ZonedDateTime.now().plus(1, ChronoUnit.DAYS), "SBZ"),
            rentVehicleOutputData -> Assertions
                .assertTrue(rentVehicleOutputData.isVehicleRented()));

        SearchVehiclesUseCase useCase = new SearchVehiclesUseCase(gatewaysAccessor);
        final ZonedDateTime pickupDate = ZonedDateTime.now();
        final ZonedDateTime returnDate = pickupDate.plusDays(1);
        final SearchVehiclesInputData inputData = new SearchVehiclesInputData(pickupDate, "SBZ",
            returnDate, "SBZ");
        useCase.searchVehicles(inputData, outputData -> {
            Assertions.assertEquals(0, outputData.getVehicleCategories().size());
        });
    }

    @Test
    void test_searchVehicles_oneVehicleRented_returnsResults() {
        final ZonedDateTime pickupDate = ZonedDateTime.now();
        final ZonedDateTime returnDate = pickupDate.plusDays(1);
        RentVehicleUseCase rentVehicleUseCase = new RentVehicleUseCase(gatewaysAccessor);
        rentVehicleUseCase.rentVehicle(
            new RentVehicleInputData("email", "Economy Car", "Dacia", "Logan", pickupDate, "SBZ",
                returnDate, "SBZ"), rentVehicleOutputData -> Assertions
                .assertTrue(rentVehicleOutputData.isVehicleRented()));

        SearchVehiclesUseCase useCase = new SearchVehiclesUseCase(gatewaysAccessor);
        final SearchVehiclesInputData inputData = new SearchVehiclesInputData(pickupDate, "SBZ",
            returnDate, "SBZ");
        useCase.searchVehicles(inputData, outputData -> {
            Assertions.assertEquals(2, outputData.getVehicleCategories().size());
        });
    }

}