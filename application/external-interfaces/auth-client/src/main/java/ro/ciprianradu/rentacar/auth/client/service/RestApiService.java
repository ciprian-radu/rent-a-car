package ro.ciprianradu.rentacar.auth.client.service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import ro.ciprianradu.rentacar.auth.client.controller.dto.*;

/**
 * Provides access to the REST API by exposing all its capabilities.
 */
@Service
public class RestApiService {

    @Value("${rest-api-url}")
    private String restApiUrl;

    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired
    private OAuth2RestOperations restTemplate;

    public List<VehicleCategoryDto> searchVehicles(final ZonedDateTime pickupDate, final String pickupLocation, final ZonedDateTime returnDate,
        final String returnLocation) {

        final UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(restApiUrl + "/vehicles")
            .queryParam("pickupDate", pickupDate)
            .queryParam("pickupLocation", pickupLocation)
            .queryParam("returnDate", returnDate)
            .queryParam("returnLocation", returnLocation);

        final ResponseEntity<List<VehicleCategoryDto>> vehicles = oAuth2RestTemplate
            .exchange(builder.buildAndExpand().toUri(), HttpMethod.GET, null, new ParameterizedTypeReference<List<VehicleCategoryDto>>() {
            });

        return vehicles.getBody();
    }

    public List<LocationDto> getAllLocations() {
        final ResponseEntity<List<LocationDto>> locations = oAuth2RestTemplate
            .exchange(restApiUrl + "/locations", HttpMethod.GET, null, new ParameterizedTypeReference<List<LocationDto>>() {
            });

        return locations.getBody();
    }

    public ResponseEntity<Map> registerRenter(final UserRegistrationDto userDto) {
        final RenterDto renter = new RenterDto();
        renter.setFirstName(userDto.getFirstName());
        renter.setLastName(userDto.getLastName());
        renter.setEmail(userDto.getEmail());

        return oAuth2RestTemplate.postForEntity(restApiUrl + "/renters", renter, Map.class, restTemplate);
    }

    public ResponseEntity<RentDto> rent(final RentDto rentDto) {
        return oAuth2RestTemplate.postForEntity(restApiUrl + "/reservations", rentDto, RentDto.class, restTemplate);
    }

}
