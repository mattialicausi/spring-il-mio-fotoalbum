package com.java.springilmiofotoalbum.controller;

import com.java.springilmiofotoalbum.model.Photo;
import com.java.springilmiofotoalbum.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/photos")
public class PhotoController {


    // istanzio i service per usare i loro metodi
    @Autowired
    private PhotoService photoService;

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

}
