package ro.ciprianradu.rentacar.integration.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "ro.ciprianradu.rentacar")
public class CleanArchitectureTest {

    private static final CleanArchitecture CLEAN_ARCHITECTURE = new CleanArchitecture();

    private CleanArchitectureTest() {

    }

    @ArchTest
    static final ArchRule test_cleanArchitecture_applies = CLEAN_ARCHITECTURE
        .entities("ro.ciprianradu.rentacar.entity..") //
        .useCases("ro.ciprianradu.rentacar.usecases..") //
        .interfaceAdapter("http", "ro.ciprianradu.rentacar.http..") //
        .interfaceAdapter("gateways", "ro.ciprianradu.rentacar.gateways..") //
        .externalInterface("rest-api", "ro.ciprianradu.rentacar.rest..") //
        .externalInterface("db-initializer", "ro.ciprianradu.rentacar.db.initializer..") //
        .externalInterface("db-postgresql", "ro.ciprianradu.rentacar.db.postgresql..") //
        .externalInterface("db-h2-local", "ro.ciprianradu.rentacar.db.h2.local..") //
        .externalInterface("db-h2-embedded", "ro.ciprianradu.rentacar.db.h2.embedded..") //
        .externalInterface("auth-server", "ro.ciprianradu.rentacar.auth.server..") //
        .externalInterface("auth-client", "ro.ciprianradu.rentacar.auth.client..") //
        ;
}