package ro.ciprianradu.rentacar.auth.client.controller;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ro.ciprianradu.rentacar.auth.client.controller.dto.RentDto;
import ro.ciprianradu.rentacar.auth.client.controller.dto.RentStatusDto;
import ro.ciprianradu.rentacar.auth.client.service.RestApiService;

@Controller
@RequestMapping("/rent")
public class RentController {

    private static final Logger log = LoggerFactory.getLogger(RentController.class);

    @Autowired
    private RestApiService restApiService;

    @GetMapping
    public String showRentForm(Model model) {
        return "rent.html";
    }

    @PostMapping
    public String rentVehicle(@ModelAttribute("rent") @Valid RentDto rentDto, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        log.debug("Trying to rent using {}", rentDto);

        if (result.hasErrors()) {
            log.warn("Renting cannot be performed because there are validation errors!");

            return "redirect:/search.html";
        }

        final ResponseEntity<RentDto> responseEntity = restApiService.rent(rentDto);
        final RentStatusDto rentStatusDto = new RentStatusDto();
        switch (responseEntity.getStatusCode()) {
            case CREATED:
                rentStatusDto.setRentDto(rentDto);
//                rentStatusDto.setRentId();
                rentStatusDto.setStatus(true);
                rentStatusDto.setRentDto(responseEntity.getBody());
                break;
            case CONFLICT:
                log.error("The REST API returned an HTTP Conflict. These data was used: {}", rentDto);
                rentStatusDto.setStatus(false);
                break;
            case BAD_REQUEST:
                log.error("The REST API returned an HTTP Bad Request. These data was used: {}", rentDto);
                rentStatusDto.setStatus(false);
                break;
            default:
                log.error("The REST API returned an HTTP {}. These data was used: {}", responseEntity.getStatusCode(), rentDto);
                rentStatusDto.setStatus(false);
        }
        redirectAttributes.addFlashAttribute("rentStatus", rentStatusDto);

        return "redirect:/rent.html";
    }

}
