package com.java.springilmiofotoalbum.repository;

import com.java.springilmiofotoalbum.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {

}
