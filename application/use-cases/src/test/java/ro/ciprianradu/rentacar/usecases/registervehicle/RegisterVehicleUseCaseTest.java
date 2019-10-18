package ro.ciprianradu.rentacar.usecases.registervehicle;

import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.ciprianradu.rentacar.usecases.gateways.GatewaysAccessor;
import ro.ciprianradu.rentacar.usecases.gateways.InMemoryVehicleBrandGateway;
import ro.ciprianradu.rentacar.usecases.gateways.InMemoryVehicleGateway;
import ro.ciprianradu.rentacar.usecases.gateways.InMemoryVehicleModelGateway;
import ro.ciprianradu.rentacar.usecases.gateways.InMemoryVehicleTypeGateway;
import ro.ciprianradu.rentacar.usecases.gateways.VehicleBrandGateway;
import ro.ciprianradu.rentacar.usecases.gateways.VehicleGateway;
import ro.ciprianradu.rentacar.usecases.gateways.VehicleModelGateway;
import ro.ciprianradu.rentacar.usecases.gateways.VehicleTypeGateway;

/**
 * Unit tests for {@link RegisterVehicleUseCase}
 */
class RegisterVehicleUseCaseTest {

    private GatewaysAccessor gatewaysAccessor = new GatewaysAccessor();

    private VehicleGateway vehicleGateway = new InMemoryVehicleGateway();

    private VehicleTypeGateway vehicleTypeGateway = new InMemoryVehicleTypeGateway();

    private VehicleBrandGateway vehicleBrandGateway = new InMemoryVehicleBrandGateway();

    private VehicleModelGateway vehicleModelGateway = new InMemoryVehicleModelGateway();

    @BeforeEach
    void setupContext() {
        gatewaysAccessor.setVehicleGateway(vehicleGateway);
        gatewaysAccessor.setVehicleTypeGateway(vehicleTypeGateway);
        gatewaysAccessor.setVehicleBrandGateway(vehicleBrandGateway);
        gatewaysAccessor.setVehicleModelGateway(vehicleModelGateway);
    }

    @Test
    void test_registerVehicle_registersVehicle() {
        final RegisterVehicleUseCase registerVehicleUseCase = new RegisterVehicleUseCase(
            gatewaysAccessor);
        RegisterVehicleInputData registerVehicleInputData = new RegisterVehicleInputData("id",
            "Economy Car", "Dacia", "Logan", BigDecimal.valueOf(5), "SBZ");
        registerVehicleUseCase
            .registerVehicle(registerVehicleInputData, registerVehicleOutputData -> {
                Assertions.assertTrue(registerVehicleOutputData.isVehicleRegistered());
            });
    }

    @Test
    void test_registerVehicle_twoVehicles_sameType_sameTypeIsUsed() {
        final RegisterVehicleUseCase registerVehicleUseCase = new RegisterVehicleUseCase(
            gatewaysAccessor);
        RegisterVehicleInputData registerVehicleInputData = new RegisterVehicleInputData("id1",
            "Economy Car", "Dacia", "Logan", BigDecimal.valueOf(5), "SBZ");
        registerVehicleUseCase
            .registerVehicle(registerVehicleInputData, registerVehicleOutputData -> {
                Assertions.assertTrue(registerVehicleOutputData.isVehicleRegistered());
            });

        registerVehicleInputData = new RegisterVehicleInputData("id2", "Economy Car", "Opel",
            "Astra", BigDecimal.valueOf(5.5), "SBZ");
        registerVehicleUseCase
            .registerVehicle(registerVehicleInputData, registerVehicleOutputData -> {
                Assertions.assertTrue(registerVehicleOutputData.isVehicleRegistered());
            });

        Assertions.assertEquals(1, ((InMemoryVehicleTypeGateway) vehicleTypeGateway).count());
    }

    @Test
    void test_registerVehicle_twoVehicles_sameBrand_sameBrandIsUsed() {
        final RegisterVehicleUseCase registerVehicleUseCase = new RegisterVehicleUseCase(
            gatewaysAccessor);
        RegisterVehicleInputData registerVehicleInputData = new RegisterVehicleInputData("id1",
            "Economy Car", "Dacia", "Logan", BigDecimal.valueOf(5), "SBZ");
        registerVehicleUseCase
            .registerVehicle(registerVehicleInputData, registerVehicleOutputData -> {
                Assertions.assertTrue(registerVehicleOutputData.isVehicleRegistered());
            });

        registerVehicleInputData = new RegisterVehicleInputData("id2", "Family Car", "Dacia",
            "Lodgy", BigDecimal.valueOf(5.5), "SBZ");
        registerVehicleUseCase
            .registerVehicle(registerVehicleInputData, registerVehicleOutputData -> {
                Assertions.assertTrue(registerVehicleOutputData.isVehicleRegistered());
            });

        Assertions.assertEquals(1, ((InMemoryVehicleBrandGateway) vehicleBrandGateway).count());
    }

    @Test
    void test_registerVehicle_twoVehicles_sameModel_sameModelIsUsed() {
        final RegisterVehicleUseCase registerVehicleUseCase = new RegisterVehicleUseCase(
            gatewaysAccessor);
        RegisterVehicleInputData registerVehicleInputData = new RegisterVehicleInputData("id1",
            "Economy Car", "Dacia", "Logan", BigDecimal.valueOf(5), "SBZ");
        registerVehicleUseCase
            .registerVehicle(registerVehicleInputData, registerVehicleOutputData -> {
                Assertions.assertTrue(registerVehicleOutputData.isVehicleRegistered());
            });

        registerVehicleInputData = new RegisterVehicleInputData("id2", "Economy Car", "Dacia",
            "Logan", BigDecimal.valueOf(6), "SBZ");
        registerVehicleUseCase
            .registerVehicle(registerVehicleInputData, registerVehicleOutputData -> {
                Assertions.assertTrue(registerVehicleOutputData.isVehicleRegistered());
            });

        Assertions.assertEquals(1, ((InMemoryVehicleModelGateway) vehicleModelGateway).count());
    }

}