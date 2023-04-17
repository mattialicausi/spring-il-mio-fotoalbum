package com.java.springilmiofotoalbum.service;

import com.java.springilmiofotoalbum.exceptions.PhotoNotFoundException;
import com.java.springilmiofotoalbum.model.Category;
import com.java.springilmiofotoalbum.model.Contact;
import com.java.springilmiofotoalbum.model.Photo;
import com.java.springilmiofotoalbum.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public List<Contact> getAllContact() {

        return contactRepository.findAll();

    }

    public Contact getById(Integer id) {

        Optional<Contact> result = contactRepository.findById(id);

        if (result.isPresent()) {
            return result.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public Contact createContact(Contact formContact) {

        Contact contactToPersist = new Contact();

        contactToPersist.setEmail(formContact.getEmail());
        contactToPersist.setMessage(formContact.getMessage());

        return contactRepository.save(contactToPersist);

    }

}
