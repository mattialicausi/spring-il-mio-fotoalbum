package com.java.springilmiofotoalbum.exceptions;

import com.java.springilmiofotoalbum.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
