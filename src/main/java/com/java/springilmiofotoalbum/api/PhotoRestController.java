package com.java.springilmiofotoalbum.api;

import com.java.springilmiofotoalbum.exceptions.PhotoNotFoundException;
import com.java.springilmiofotoalbum.model.Photo;
import com.java.springilmiofotoalbum.service.PhotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/v1/photos")
public class PhotoRestController {

    @Autowired
    private PhotoService photoService;

    // metodo per tutti le photo
    public List<Photo> list(@RequestParam(name = "q")Optional<String> search) {

        if (search.isPresent()) {
            return photoService.getPhotoVisibleTrueAndName(search.get());
        }

        return photoService.getPhotoVisibleTrue();

    }

    // metodo per la singola photo

    @GetMapping("/{id}")
    public Photo getById(@PathVariable Integer id) {
        try {
            return photoService.getById(id);
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


}
