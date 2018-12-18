package ro.ciprianradu.rentacar.usecases.registerlocation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.ciprianradu.rentacar.usecases.gateways.InMemoryLocationGateway;
import ro.ciprianradu.rentacar.usecases.Context;

/**
 * Unit tests for {@link RegisterLocationUseCase}
 */
class RegisterLocationUseCaseTest {

    @BeforeEach
    void setupContext() {
        Context.locationGateway = new InMemoryLocationGateway();
    }

    @Test
    void test_registerLocation_registersLocation() {
        final RegisterLocationUseCase registerLocationUseCase = new RegisterLocationUseCase();
        RegisterLocationInputData registerLocationInputData = new RegisterLocationInputData("SBZ Airport", "Şoseaua Alba Iulia nr. 73, Sibiu, România");
        registerLocationUseCase.registerLocation(registerLocationInputData, registerLocationOutputData -> {
            Assertions.assertTrue(registerLocationOutputData.isLocationRegistered());
        });
    }

    @Test
    void test_registerLocation_sameLocationIsRegisteredOnlyOnce() {
        final RegisterLocationUseCase registerLocationUseCase1 = new RegisterLocationUseCase();
        RegisterLocationInputData registerLocationInputData = new RegisterLocationInputData("SBZ Airport", "Şoseaua Alba Iulia nr. 73, Sibiu, România");
        registerLocationUseCase1.registerLocation(registerLocationInputData, registerLocationOutputData -> {
            Assertions.assertTrue(registerLocationOutputData.isLocationRegistered());
        });

        final RegisterLocationUseCase registerLocationUseCase2 = new RegisterLocationUseCase();
        registerLocationUseCase2.registerLocation(registerLocationInputData, registerLocationOutputData -> {
            Assertions.assertFalse(registerLocationOutputData.isLocationRegistered());
        });
    }

    @Test
    void test_registerLocation_twoDifferentLocationsAreBothRegistered() {
        final RegisterLocationUseCase registerLocationUseCase1 = new RegisterLocationUseCase();
        RegisterLocationInputData registerLocationInputData = new RegisterLocationInputData("SBZ Airport", "Şoseaua Alba Iulia nr. 73, Sibiu, România");
        registerLocationUseCase1.registerLocation(registerLocationInputData, registerLocationOutputData -> {
            Assertions.assertTrue(registerLocationOutputData.isLocationRegistered());
        });

        final RegisterLocationUseCase registerLocationUseCase2 = new RegisterLocationUseCase();
        registerLocationInputData = new RegisterLocationInputData("OTP Airport", "Calea Bucureştilor 224E, Otopeni 075150");
        registerLocationUseCase2.registerLocation(registerLocationInputData, registerLocationOutputData -> {
            Assertions.assertTrue(registerLocationOutputData.isLocationRegistered());
        });
    }
}