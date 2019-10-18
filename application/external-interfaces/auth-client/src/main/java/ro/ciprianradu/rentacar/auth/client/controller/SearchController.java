package ro.ciprianradu.rentacar.auth.client.controller;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ro.ciprianradu.rentacar.auth.client.controller.dto.RentDto;
import ro.ciprianradu.rentacar.auth.client.controller.dto.SearchDto;
import ro.ciprianradu.rentacar.auth.client.controller.dto.VehicleCategoryDto;
import ro.ciprianradu.rentacar.auth.client.service.RestApiService;

@Controller
@RequestMapping("/search")
@SessionAttributes("search")
public class SearchController {

    private static final Logger log = LoggerFactory.getLogger(SearchController.class);

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
        .ofPattern("MM/dd/yyyy");

    @Autowired
    private RestApiService restApiService;

    @PostMapping
    public String searchVehicles(@ModelAttribute("search") @Valid SearchDto searchDto,
        BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        final Optional<ZonedDateTime> pickupDateOptional = validatePickupDate(searchDto, result);
        final Optional<ZonedDateTime> returnDateOptional = validateReturnDate(searchDto, result);
        validateDates(searchDto, result, pickupDateOptional, returnDateOptional);

        if (result.hasErrors()) {
            log.warn("Renting cannot be performed because there are validation errors!");

            return "redirect:/search.html";
        }

        final ZonedDateTime pickupDate = pickupDateOptional.get();
        final String pickupLocationName = searchDto.getPickupLocationName();
        final ZonedDateTime returnDate = returnDateOptional.get();
        final String returnLocationName = searchDto.getReturnLocationName();
        final List<VehicleCategoryDto> vehicles = restApiService
            .searchVehicles(pickupDate, pickupLocationName, returnDate, returnLocationName);
        redirectAttributes.addFlashAttribute("vehicles", vehicles);

        final RentDto rentDto = new RentDto();
        rentDto.setPickupDate(pickupDate);
        rentDto.setPickupLocationName(pickupLocationName);
        rentDto.setReturnDate(returnDate);
        rentDto.setReturnLocationName(returnLocationName);
        redirectAttributes.addFlashAttribute("rent", rentDto);

        return "redirect:/search-results.html";
    }

    private Optional<ZonedDateTime> validatePickupDate(final SearchDto searchDto,
        final BindingResult result) {
        ZonedDateTime pickupZonedDateTime = null;

        try {
            final LocalDate pickupLocalDate = LocalDate
                .parse(searchDto.getPickupDate(), DATE_TIME_FORMATTER);
            pickupZonedDateTime = pickupLocalDate.atStartOfDay(ZoneOffset.UTC);
        } catch (DateTimeParseException e) {
            log.error("Invalid pickup date '{}'!", searchDto.getPickupDate());
            result.rejectValue("pickupDate", "pickupDate.invalid",
                "Invalid pickup date '" + searchDto.getPickupDate() + "'!");
        }

        return Optional.ofNullable(pickupZonedDateTime);
    }

    private Optional<ZonedDateTime> validateReturnDate(final SearchDto searchDto,
        final BindingResult result) {
        ZonedDateTime returnZonedDateTime = null;

        try {
            final LocalDate returnLocalDate = LocalDate
                .parse(searchDto.getReturnDate(), DATE_TIME_FORMATTER);
            returnZonedDateTime = returnLocalDate.atTime(23, 59, 59).atZone(ZoneOffset.UTC);
        } catch (DateTimeParseException e) {
            log.error("Invalid return date '{}'!", searchDto.getReturnDate());
            result.rejectValue("returnDate", "returnDate.invalid",
                "Invalid return date '" + searchDto.getReturnDate() + "'!");
        }

        return Optional.ofNullable(returnZonedDateTime);
    }

    private void validateDates(final SearchDto searchDto, final BindingResult result,
        final Optional<ZonedDateTime> pickupDateOptional,
        final Optional<ZonedDateTime> returnDateOptional) {
        if (pickupDateOptional.isPresent() && returnDateOptional.isPresent() && returnDateOptional
            .get().isBefore(pickupDateOptional.get())) {
            log.error("Return date '{}' is before pickup date '{}'!", searchDto.getReturnDate(),
                searchDto.getPickupDate());
            result.rejectValue("returnDate", "returnDate.beforePickupDate",
                "Return date '" + searchDto.getReturnDate() + "' is before pickup date '"
                    + searchDto.getPickupDate() + "'!");
        }
    }

}
