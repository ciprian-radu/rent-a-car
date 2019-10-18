package ro.ciprianradu.rentacar.integration.architecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is a dummy Spring Boot Application. It is only needed for the Gradle task
 * :tests:integration:architecture:bootJar
 * <p>
 * This Gradle task expect to find a Spring Boot Application because the {@link CleanArchitecture}
 * test depends transitively on Spring Boot (in order to load classes from external interfaces).
 * </p>
 */
@SpringBootApplication
public class DummySpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(DummySpringBootApplication.class, args);
    }

}
