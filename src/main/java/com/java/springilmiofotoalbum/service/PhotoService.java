package com.java.springilmiofotoalbum.service;

import com.java.springilmiofotoalbum.exceptions.PhotoNotFoundException;
import com.java.springilmiofotoalbum.model.Category;
import com.java.springilmiofotoalbum.model.Image;
import com.java.springilmiofotoalbum.model.ImageForm;
import com.java.springilmiofotoalbum.model.Photo;
import com.java.springilmiofotoalbum.repository.CategoryRepository;
import com.java.springilmiofotoalbum.repository.ImageRepository;
import com.java.springilmiofotoalbum.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    @Autowired
    ImageRepository imageRepository;

    // metodi

    // prendo tutte le foto
    public List<Photo> getAllPhoto() {

        return photoRepository.findAll(Sort.by("title"));

    }

    // prendo le photo filtrate per nome

    public List<Photo> getFilteredPhoto(String keyword) {

        return photoRepository.findByTitleContainingIgnoreCase(keyword);

    }

    // prendo le photo filtrate per visibilità e nome
    public List<Photo> getPhotoVisibleTrueAndName(String search) {

        return photoRepository.findByTitleContainingIgnoreCaseAndVisible(search, true);

    }

    // prendo le photo filtrate per visibilità
    public List<Photo> getPhotoVisibleTrue() {
        return photoRepository.findByVisible(true);
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

    // metodo per cancellare tramite l'id una photo
    public boolean deleteById(Integer id) throws PhotoNotFoundException {
        photoRepository.findById(id).orElseThrow(() -> new PhotoNotFoundException(Integer.toString(id)));
        try {
            photoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
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

    // metodo per aggiornare img

    public Image updateCover(Integer photoId, ImageForm imageForm) throws PhotoNotFoundException, IOException {

        Photo photo = getById(photoId);

        // delete old image if exists
        Image oldImage = photo.getUrl();

        if (oldImage != null) {
            photo.setUrl(null); // disconnect image from book
            oldImage.setPhoto(null); // disconnect book from image
            imageRepository.delete(oldImage);
        }

        // create new image
        Image newImage = new Image();
        newImage.setPhoto(photo);
        newImage.setContent(imageForm.getMultipartFile().getBytes());
        // persist
        return imageRepository.save(newImage);
    }


}
