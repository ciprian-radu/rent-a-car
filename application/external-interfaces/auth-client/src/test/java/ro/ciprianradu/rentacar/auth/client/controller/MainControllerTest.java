package ro.ciprianradu.rentacar.auth.client.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.ciprianradu.rentacar.auth.client.controller.dto.RentDto;
import ro.ciprianradu.rentacar.auth.client.controller.dto.SearchDto;
import ro.ciprianradu.rentacar.auth.client.controller.dto.VehicleCategoryDto;
import ro.ciprianradu.rentacar.auth.client.service.RestApiService;

/**
 * Unit tests for {@link MainController}
 */
@ExtendWith(SpringExtension.class)
class MainControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RestApiService restApiService;

    @InjectMocks
    private MainController mainController = new MainController();

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(mainController).setViewResolvers(new StandaloneMvcTestViewResolver()).build();
    }

    @Test
    public void test_root_initializesSessionDtos() throws Exception {
        mockMvc.perform(get("/")).andDo(print())
            .andExpect(MockMvcResultMatchers.model().attributeExists("search", "vehicles", "rent"));
    }

    @Test
    public void test_root_returnsIndexHtml() throws Exception {
        Assertions.assertEquals("index.html", mockMvc.perform(get("/")).andDo(print())
            .andReturn().getModelAndView().getViewName());
    }

    @Test
    public void test_about_returnsAboutHtml() throws Exception {
        mockMvc.perform(get("/about.html")).andExpect(view().name("about.html"));
    }

    @Test
    public void test_cars_returnsCarsHtml() throws Exception {
        mockMvc.perform(get("/cars.html")).andExpect(view().name("cars.html"));
    }

    @Test
    public void test_service_returnsServicesHtml() throws Exception {
        mockMvc.perform(get("/service.html")).andExpect(view().name("service.html"));
    }

    @Test
    public void test_contact_returnsContactHtml() throws Exception {
        mockMvc.perform(get("/contact.html")).andExpect(view().name("contact.html"));
    }

    @Test
    public void test_login_returnsLoginHtml() throws Exception {
        mockMvc.perform(get("/login.html")).andExpect(view().name("login.html"));
    }

    @Test
    public void test_registration_returnsRegistrationHtml() throws Exception {
        mockMvc.perform(get("/registration.html")).andExpect(view().name("registration.html"));
    }

    @Test
    public void test_search_returnsSearchHtml() throws Exception {
        mockMvc.perform(get("/search.html").sessionAttr("search", new SearchDto())).andExpect(view().name("search.html"));
    }

    @Test
    public void test_searchResults_returnsSearchResultsHtml() throws Exception {
        mockMvc.perform(
            get("/search-results.html").sessionAttr("rent", new RentDto()).sessionAttr("vehicles", Collections.singletonList(VehicleCategoryDto.class)))
            .andExpect(view().name("search-results.html"));
    }

    @Test
    public void test_rent_returnsRentHtml() throws Exception {
        mockMvc.perform(get("/rent.html")).andExpect(view().name("rent.html"));
    }

}