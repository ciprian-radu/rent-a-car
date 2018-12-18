package ro.ciprianradu.rentacar.auth.client.configuration;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

@Configuration
public class OAuth2ClientConfig {

    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    @Value("${security.oauth2.client.access-token-uri}")
    private String tokenUrl;

    @Bean
    public OAuth2RestTemplate oAuth2RestTemplate() {
        final OAuth2RestTemplate template = new OAuth2RestTemplate(clientCredentialsResourceDetails(tokenUrl), new DefaultOAuth2ClientContext(
            new DefaultAccessTokenRequest()));
        template.setAccessTokenProvider(clientAccessTokenProvider());

        return template;
    }

    @Bean
    public AccessTokenProvider clientAccessTokenProvider() {
        return new ClientCredentialsAccessTokenProvider();
    }

    @Bean
    public OAuth2ProtectedResourceDetails clientCredentialsResourceDetails(String tokenUrl) {
        final ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
        resource.setAccessTokenUri(tokenUrl);
        resource.setClientId(clientId);
        resource.setClientSecret(clientSecret);
        resource.setGrantType("client_credentials");
        resource.setScope(Arrays.asList("read", "write"));

        return resource;
    }
}
