package ro.ciprianradu.rentacar.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ro.ciprianradu.rentacar.usecases.registerlocation.RegisterLocationInputPort;
import ro.ciprianradu.rentacar.usecases.registerlocation.RegisterLocationUseCase;
import ro.ciprianradu.rentacar.usecases.registerrenter.RegisterRenterInputPort;
import ro.ciprianradu.rentacar.usecases.registerrenter.RegisterRenterUseCase;
import ro.ciprianradu.rentacar.usecases.registervehicle.RegisterVehicleInputPort;
import ro.ciprianradu.rentacar.usecases.registervehicle.RegisterVehicleUseCase;
import ro.ciprianradu.rentacar.usecases.rentvehicle.RentVehicleInputPort;
import ro.ciprianradu.rentacar.usecases.rentvehicle.RentVehicleUseCase;
import ro.ciprianradu.rentacar.usecases.retrievelocations.RetrieveLocationsInputPort;
import ro.ciprianradu.rentacar.usecases.retrievelocations.RetrieveLocationsUseCase;
import ro.ciprianradu.rentacar.usecases.searchvehicles.SearchVehiclesInputPort;
import ro.ciprianradu.rentacar.usecases.searchvehicles.SearchVehiclesUseCase;

/**
 * Configuration for the REST API application
 */
@Configuration
//@EnableWebMvc
class RestApiConfiguration {

    @Bean
    public RegisterRenterInputPort configureRegisterRenterInputPort() {
        return new RegisterRenterUseCase();
    }

    @Bean
    public RegisterLocationInputPort configureRegisterLocationInputPort() {
        return new RegisterLocationUseCase();
    }

    @Bean
    public RetrieveLocationsInputPort configureRetrieveLocationsInputPort() {
        return new RetrieveLocationsUseCase();
    }

    @Bean
    public RegisterVehicleInputPort configureRegisterVehicleInputPort() {
        return new RegisterVehicleUseCase();
    }

    @Bean
    public RentVehicleInputPort configureRentVehicleInputPort() {
        return new RentVehicleUseCase();
    }

    @Bean
    public SearchVehiclesInputPort configureSearchVehiclesInputPort() {
        return new SearchVehiclesUseCase();
    }

    @Bean
    public WebMvcConfigurer configureWebMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
                configurer.favorPathExtension(true).
                    favorParameter(false).
                    ignoreAcceptHeader(false).
                    defaultContentType(MediaType.APPLICATION_JSON);
            }
        };
    }

}
