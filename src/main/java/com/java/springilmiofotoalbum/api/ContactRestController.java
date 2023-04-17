package com.java.springilmiofotoalbum.api;

import com.java.springilmiofotoalbum.model.Contact;
import com.java.springilmiofotoalbum.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/contacts")
public class ContactRestController {

    @Autowired
    private ContactService contactService;

    @PostMapping
    public Contact getContactList(@Valid @RequestBody Contact contact) {

        try {
            return contactService.createContact(contact);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

}
