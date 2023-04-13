package com.java.springilmiofotoalbum.controller;

import com.java.springilmiofotoalbum.exceptions.PhotoNotFoundException;
import com.java.springilmiofotoalbum.model.Photo;
import com.java.springilmiofotoalbum.model.User;
import com.java.springilmiofotoalbum.repository.UserRepository;
import com.java.springilmiofotoalbum.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/photos")
public class PhotoController {


    // istanzio i service per usare i loro metodi
    @Autowired
    private PhotoService photoService;

    @Autowired
    private UserRepository userRepository;

// controller per restituire alla view tutte le photo

    @GetMapping
    public String index(Model model, @RequestParam(name = "q")Optional<String> keyword) {

        List<Photo> photos;

        if (keyword.isEmpty()) {

            photos = photoService.getAllPhoto();

        } else {

            photos = photoService.getFilteredPhoto(keyword.get());
            model.addAttribute("keyword", keyword.get());

        }

        model.addAttribute("photoList", photos);
        return "/photos/index";

    }

    // controller per andare al dettaglio di una foto

    @GetMapping("/${photoId}")
    public String show(@PathVariable("photoId") Integer id, Model model, Authentication authentication) {

        User loggedUser = userRepository.findByEmail(authentication.getName()).orElseThrow();
        model.addAttribute("loggedUser", loggedUser);

        try {
            Photo photo = photoService.getById(id);
            model.addAttribute("photo", photo);
            return "/photo/show";
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Foto con id " + id + " non trovata");
        }


    }

}
