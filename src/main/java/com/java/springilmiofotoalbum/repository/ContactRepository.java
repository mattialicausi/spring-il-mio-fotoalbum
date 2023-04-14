package com.java.springilmiofotoalbum.repository;

import com.java.springilmiofotoalbum.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
}
