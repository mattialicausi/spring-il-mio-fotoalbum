package com.java.springilmiofotoalbum.service;

import com.java.springilmiofotoalbum.exceptions.PhotoNotFoundException;
import com.java.springilmiofotoalbum.model.Category;
import com.java.springilmiofotoalbum.model.Photo;
import com.java.springilmiofotoalbum.repository.CategoryRepository;
import com.java.springilmiofotoalbum.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PhotoService {

    // istanzio i repository per fare le query
    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    private CategoryRepository categoryRepository;

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

    // metodo per creare una nuova photo
    public Photo createPhoto(Photo formPhoto) {

        Photo photoToPersist = new Photo();

        photoToPersist.setTitle(formPhoto.getTitle());
        photoToPersist.setDescription(formPhoto.getDescription());
        photoToPersist.setUrl(formPhoto.getUrl());
        photoToPersist.setVisible(formPhoto.getVisible());


        Set<Category> formCategories = getPhotoCategories(formPhoto);
        photoToPersist.setCategories(formCategories);
        return photoRepository.save(photoToPersist);

    }

    // metodo per modificare una photo

    public Photo updatePhoto(Photo formPhoto, Integer id) throws PhotoNotFoundException {

        Photo photoToUpdate = getById(id);

        photoToUpdate.setTitle(formPhoto.getTitle());
        photoToUpdate.setDescription(formPhoto.getDescription());
        photoToUpdate.setUrl(formPhoto.getUrl());
        photoToUpdate.setVisible(formPhoto.getVisible());

        Set<Category> formCategories = getPhotoCategories(formPhoto);
        photoToUpdate.setCategories(formCategories);


        return photoRepository.save(photoToUpdate);

    }


    // metodo per prendere le categorie di una photo
    private Set<Category> getPhotoCategories(Photo formPhoto) {
        Set<Category> formCategories = new HashSet<>();

        if (formPhoto.getCategories() != null) {

            for (Category c : formPhoto.getCategories()) {
                formCategories.add(categoryRepository.findById(c.getId()).orElseThrow());
            }

        }

        return formCategories;
    }


}
