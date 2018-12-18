package ro.ciprianradu.rentacar.auth.client.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

/**
 * Unit tests for {@link ErrorController}
 */
class ErrorControllerTest {

    private Model model = new BindingAwareModelMap();

    private ErrorController errorController = new ErrorController();

    @Test
    void test_exception_NullThrowable_returnsError() {
        Assertions.assertEquals("error", errorController.exception(null, model));
    }

    @Test
    void test_exception_NullThrowable_putsUnknownErrorInTheModel() {
        errorController.exception(null, model);
        Assertions.assertEquals("Unknown error", model.asMap().get("errorMessage"));
    }

    @Test
    void test_exception_throwable_putsExceptionMessageInTheModel() {
        errorController.exception(new Exception("exception message"), model);
        Assertions.assertEquals("exception message", model.asMap().get("errorMessage"));
    }

}