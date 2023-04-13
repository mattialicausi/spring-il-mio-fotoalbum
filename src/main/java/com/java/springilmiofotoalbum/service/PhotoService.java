package com.java.springilmiofotoalbum.service;

import com.java.springilmiofotoalbum.exceptions.PhotoNotFoundException;
import com.java.springilmiofotoalbum.model.Photo;
import com.java.springilmiofotoalbum.repository.CategoryRepository;
import com.java.springilmiofotoalbum.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    // istanzio i repository per fare le query
    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    CategoryRepository categoryRepository;

    // metodi

    // prendo tutte le foto
    public List<Photo> getAllPhoto() {

        return photoRepository.findAll(Sort.by("title"));

    }

    // prendo le photo filtrate per nome

    public List<Photo> getFilteredPhoto(String keyword) {

        return photoRepository.findByTitleContainingIgnoreCase(keyword);

    }

    // prendo la singola photo per id

    public Photo getById(Integer id) throws PhotoNotFoundException {

        Optional<Photo> result = photoRepository.findById(id);

        if (result.isPresent()) {
            return result.get();
        } else {
            throw new PhotoNotFoundException(Integer.toString(id));
        }
    }


}
