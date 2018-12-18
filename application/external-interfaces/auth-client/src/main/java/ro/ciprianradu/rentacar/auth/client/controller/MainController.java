package ro.ciprianradu.rentacar.auth.client.controller;

import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import ro.ciprianradu.rentacar.auth.client.controller.dto.*;
import ro.ciprianradu.rentacar.auth.client.service.RestApiService;

@Controller
@SessionAttributes(value = {"search", "vehicles", "rent"})
public class MainController {

    @Autowired
    private RestApiService restApiService;

    @GetMapping(value = {"/", "/index.html"})
    public String index(Model model) {
        initializeSession(model);

        return "index.html";
    }

    private void initializeSession(final Model model) {
        initializeSearch(model);
        initializeVehicles(model);
        initializeRent(model);
    }

    private void initializeSearch(final Model model) {
        // Holds the input of the search operation, which is stored on the HTTP Session.
        model.addAttribute("search", new SearchDto());
    }

    private void initializeVehicles(final Model model) {
        // Holds the results of the search operation.
        // This empty list is needed only if the user tries to directly access search-results.html.
        // Otherwise, SearchController will populate this list.
        final List<VehicleCategoryDto> vehicles = Collections.emptyList();
        model.addAttribute("vehicles", vehicles);
    }

    private void initializeRent(final Model model) {
        // Holds the input of the rent operation, which is stored on the HTTP Session.
        model.addAttribute("rent", new RentDto());
    }

    @GetMapping("/about.html")
    public String about(Model model) {
        return "about.html";
    }

    @GetMapping("/cars.html")
    public String cars(Model model) {
        return "cars.html";
    }

    @GetMapping("/service.html")
    public String service(Model model) {
        return "service.html";
    }

    @GetMapping("/contact.html")
    public String contact(Model model) {
        return "contact.html";
    }

    @GetMapping("/login.html")
    public String login(Model model) {
        return "login.html";
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping("/registration.html")
    public String registration(Model model) {
        return "registration.html";
    }

    @GetMapping("/search.html")
    public String search(@ModelAttribute("search") SearchDto searchDto, Model model) {
        // "search" is available on the HTTP Session.
        final List<LocationDto> allLocations = restApiService.getAllLocations();
        model.addAttribute("locations", allLocations);

        return "search.html";
    }

    @GetMapping("/search-results.html")
    public String searchResults(@ModelAttribute("rent") RentDto rent, @ModelAttribute("vehicles") @Valid List<VehicleCategoryDto> vehicles, Model model) {
        // "rent" is available on the HTTP Session.
        // "vehicles" is available on the HTTP Session.
        return "search-results.html";
    }

    @GetMapping("/rent.html")
    public String rent(Model model) {
        return "rent.html";
    }

}
