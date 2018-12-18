package ro.ciprianradu.rentacar.auth.client.controller;

import java.util.Collections;
import java.util.Map;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.ciprianradu.rentacar.auth.client.controller.dto.UserRegistrationDto;
import ro.ciprianradu.rentacar.auth.client.service.RestApiService;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    private static final Logger log = LoggerFactory.getLogger(UserRegistrationController.class);

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RestApiService restApiService;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "registration.html";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
        BindingResult result) {

        if (userDetailsManager.userExists(userDto.getEmail())) {
            result.rejectValue("email", "email.exists", "There is already an account registered with that email");
        }

        if (result.hasErrors()) {
            return "registration.html";
        }

        final UserDetails userDetails = new User(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()),
            Collections.singleton(new SimpleGrantedAuthority("RENTER")));
        userDetailsManager.createUser(userDetails);

        String status = "failure";
        try {
            final ResponseEntity<Map> responseEntity = restApiService.registerRenter(userDto);
            if (HttpStatus.CREATED == responseEntity.getStatusCode()) {
                status = "success";
            } else {
                userDetailsManager.deleteUser(userDto.getEmail());
            }
        } catch (RuntimeException e) {
            log.error("Unable to register a Renter for the new user. This new user will be deleted.", e);
            userDetailsManager.deleteUser(userDto.getEmail());
            log.debug("User deleted.");
        }

        return "redirect:/registration.html?" + status;
    }

}
