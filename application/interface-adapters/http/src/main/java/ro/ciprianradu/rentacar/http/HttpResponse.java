package ro.ciprianradu.rentacar.http;

/**
 * Basic model for an HTTP response
 */
public class HttpResponse {

    private HttpStatus status;

    private String message;

    public HttpResponse(final HttpStatus status) {
        this.status = status;
        this.message = "";
    }

    public HttpResponse(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
