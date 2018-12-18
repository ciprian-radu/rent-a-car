package ro.ciprianradu.rentacar.usecases.registerrenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.ciprianradu.rentacar.usecases.Context;
import ro.ciprianradu.rentacar.usecases.gateways.InMemoryRenterGateway;

/**
 * Unit tests for {@link RegisterRenterUseCase}
 */
class RegisterRenterUseCaseTest {

    @BeforeEach
    void setupContext() {
        Context.renterGateway = new InMemoryRenterGateway();
    }

    @Test
    void test_registerRenter_registersRenter() {
        final RegisterRenterUseCase registerRenterUseCase = new RegisterRenterUseCase();
        RegisterRenterInputData registerRenterInputData = new RegisterRenterInputData("John", "Doe", "john.doe@example.com");
        registerRenterUseCase.registerRenter(registerRenterInputData, registerRenterOutputData -> {
            Assertions.assertTrue(registerRenterOutputData.isRenterRegistered());
        });
    }

    @Test
    void test_registerRenter_sameRenterIsRegisteredOnlyOnce() {
        final RegisterRenterUseCase registerRenterUseCase1 = new RegisterRenterUseCase();
        RegisterRenterInputData registerRenterInputData1 = new RegisterRenterInputData("John", "Doe", "john.doe@example.com");
        registerRenterUseCase1.registerRenter(registerRenterInputData1, registerRenterOutputData -> {
            Assertions.assertTrue(registerRenterOutputData.isRenterRegistered());
        });

        final RegisterRenterUseCase registerRenterUseCase2 = new RegisterRenterUseCase();
        RegisterRenterInputData registerRenterInputData2 = new RegisterRenterInputData("John", "Doe", "john.doe@example.com");
        registerRenterUseCase2.registerRenter(registerRenterInputData2, registerRenterOutputData -> {
            Assertions.assertFalse(registerRenterOutputData.isRenterRegistered());
        });
    }

    @Test
    void test_registerRenter_twoDifferentRentersAreBothRegistered() {
        final RegisterRenterUseCase registerRenterUseCase1 = new RegisterRenterUseCase();
        RegisterRenterInputData registerRenterInputData = new RegisterRenterInputData("John", "Doe", "john.doe@example.com");
        registerRenterUseCase1.registerRenter(registerRenterInputData, registerRenterOutputData -> {
            Assertions.assertTrue(registerRenterOutputData.isRenterRegistered());
        });

        final RegisterRenterUseCase registerRenterUseCase2 = new RegisterRenterUseCase();
        registerRenterInputData = new RegisterRenterInputData("John", "Doe", "jane.doe@anotherexample.com");
        registerRenterUseCase2.registerRenter(registerRenterInputData, registerRenterOutputData -> {
            Assertions.assertTrue(registerRenterOutputData.isRenterRegistered());
        });
    }
    
}