package ro.ciprianradu.rentacar.auth.resource.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.ciprianradu.rentacar.auth.resource.model.Person;

@RestController
public class PersonInfoController {

    @PreAuthorize("#oauth2.hasScope('read')")
    @GetMapping("/person")
    public Person personInfo() {
        return new Person("Ciprian Radu", "Male");
    }
}
