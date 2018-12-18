package ro.ciprianradu.rentacar.auth.resource.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 *  REST API Resource Server.
 */
@Configuration
@EnableWebSecurity
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true) // Allow method annotations like @PreAuthorize
public class ResourceConfigurer extends ResourceServerConfigurerAdapter {

    // The RemoteTokenServices is configured via application.yml.

//    private static final String OAUTH2_SERVER_URL = "http://localhost:7081";
//
//    private static final String CLIENT_ID = "account";
//
//    private static final String CLIENT_SECRET = "password";
//
//    @Bean
//    @Primary
//    public ResourceServerTokenServices tokenServices() {
//        final RemoteTokenServices tokenServices = new RemoteTokenServices();
//        tokenServices.setClientId(CLIENT_ID);
//        tokenServices.setClientSecret(CLIENT_SECRET);
//        tokenServices.setCheckTokenEndpointUrl(OAUTH2_SERVER_URL + "/oauth/check_token");
//        return tokenServices;
//    }

}
