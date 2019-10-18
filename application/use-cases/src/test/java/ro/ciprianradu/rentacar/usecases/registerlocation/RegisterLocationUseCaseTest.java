package ro.ciprianradu.rentacar.usecases.registerlocation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.ciprianradu.rentacar.usecases.gateways.GatewaysAccessor;
import ro.ciprianradu.rentacar.usecases.gateways.InMemoryLocationGateway;
import ro.ciprianradu.rentacar.usecases.gateways.LocationGateway;

/**
 * Unit tests for {@link RegisterLocationUseCase}
 */
class RegisterLocationUseCaseTest {

    private GatewaysAccessor gatewaysAccessor = new GatewaysAccessor();

    private LocationGateway locationGateway = new InMemoryLocationGateway();

    @BeforeEach
    void setupContext() {
        gatewaysAccessor.setLocationGateway(locationGateway);
    }

    @Test
    void test_registerLocation_registersLocation() {
        final RegisterLocationUseCase registerLocationUseCase = new RegisterLocationUseCase(
            gatewaysAccessor);
        RegisterLocationInputData registerLocationInputData = new RegisterLocationInputData(
            "SBZ Airport", "Şoseaua Alba Iulia nr. 73, Sibiu, România");
        registerLocationUseCase
            .registerLocation(registerLocationInputData, registerLocationOutputData -> {
                Assertions.assertTrue(registerLocationOutputData.isLocationRegistered());
            });
    }

    @Test
    void test_registerLocation_sameLocationIsRegisteredOnlyOnce() {
        final RegisterLocationUseCase registerLocationUseCase1 = new RegisterLocationUseCase(
            gatewaysAccessor);
        RegisterLocationInputData registerLocationInputData = new RegisterLocationInputData(
            "SBZ Airport", "Şoseaua Alba Iulia nr. 73, Sibiu, România");
        registerLocationUseCase1
            .registerLocation(registerLocationInputData, registerLocationOutputData -> {
                Assertions.assertTrue(registerLocationOutputData.isLocationRegistered());
            });

        final RegisterLocationUseCase registerLocationUseCase2 = new RegisterLocationUseCase(
            gatewaysAccessor);
        registerLocationUseCase2
            .registerLocation(registerLocationInputData, registerLocationOutputData -> {
                Assertions.assertFalse(registerLocationOutputData.isLocationRegistered());
            });
    }

    @Test
    void test_registerLocation_twoDifferentLocationsAreBothRegistered() {
        final RegisterLocationUseCase registerLocationUseCase1 = new RegisterLocationUseCase(
            gatewaysAccessor);
        RegisterLocationInputData registerLocationInputData = new RegisterLocationInputData(
            "SBZ Airport", "Şoseaua Alba Iulia nr. 73, Sibiu, România");
        registerLocationUseCase1
            .registerLocation(registerLocationInputData, registerLocationOutputData -> {
                Assertions.assertTrue(registerLocationOutputData.isLocationRegistered());
            });

        final RegisterLocationUseCase registerLocationUseCase2 = new RegisterLocationUseCase(
            gatewaysAccessor);
        registerLocationInputData = new RegisterLocationInputData("OTP Airport",
            "Calea Bucureştilor 224E, Otopeni 075150");
        registerLocationUseCase2
            .registerLocation(registerLocationInputData, registerLocationOutputData -> {
                Assertions.assertTrue(registerLocationOutputData.isLocationRegistered());
            });
    }
}