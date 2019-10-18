package ro.ciprianradu.rentacar.auth.client.controller.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import ro.ciprianradu.rentacar.auth.client.constraint.FieldMatch;
import ro.ciprianradu.rentacar.auth.client.constraint.FieldMatch.List;

@List({
    @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")})
public class UserRegistrationDto {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;

    @Email
    @NotEmpty
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
