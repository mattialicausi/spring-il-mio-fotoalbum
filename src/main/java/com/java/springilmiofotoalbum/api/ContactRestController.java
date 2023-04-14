package com.java.springilmiofotoalbum.api;

import com.java.springilmiofotoalbum.model.Contact;
import com.java.springilmiofotoalbum.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/contacts")
public class ContactRestController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/create")
    public Contact getContactList(@Valid @ModelAttribute("contact") Contact formContact, BindingResult bindingResult, Model model) {

        return contactService.createContact(formContact);

    }

}
