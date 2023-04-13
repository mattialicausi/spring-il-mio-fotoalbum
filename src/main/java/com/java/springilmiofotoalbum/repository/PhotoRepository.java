package com.java.springilmiofotoalbum.repository;

import com.java.springilmiofotoalbum.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {

    // query

    // query che filtra per nome
    public List<Photo> findByTitleContainingIgnoreCase(String title);

}
