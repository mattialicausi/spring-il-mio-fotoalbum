package com.java.springilmiofotoalbum.controller;

import com.java.springilmiofotoalbum.exceptions.PhotoNotFoundException;
import com.java.springilmiofotoalbum.model.AlertMessage;
import com.java.springilmiofotoalbum.model.ImageForm;
import com.java.springilmiofotoalbum.model.Photo;
import com.java.springilmiofotoalbum.model.User;
import com.java.springilmiofotoalbum.repository.UserRepository;
import com.java.springilmiofotoalbum.service.CategoryService;
import com.java.springilmiofotoalbum.service.PhotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
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

    @Autowired
    private CategoryService categoryService;

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

    @GetMapping("/{photoId}")
    public String show(@PathVariable("photoId") Integer id, Model model, Authentication authentication) {

        User loggedUser = userRepository.findByEmail(authentication.getName()).orElseThrow();
        model.addAttribute("loggedUser", loggedUser);

        try {
            Photo photo = photoService.getById(id);
            model.addAttribute("photo", photo);
            model.addAttribute("categories", photo.getCategories());

            return "/photos/show";
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Foto con id " + id + " non trovata");
        }

    }

    // controller per creare una nuova photo

    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("photo", new Photo());
        model.addAttribute("categories", categoryService.getAll());

        return "/photos/create";

    }

    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute("photo") Photo formPhoto, BindingResult bindingResult, Model model) {

        // VALIDATION
        boolean hasErrors = bindingResult.hasErrors();

        if (hasErrors) {

            model.addAttribute("categories", categoryService.getAll());

            return "/photos/create";
        }

        photoService.createPhoto(formPhoto);
        return "redirect:/photos";
    }

    // controller per modificare una photo
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {

        try {

            Photo photo = photoService.getById(id);
            model.addAttribute("photo", photo);
            model.addAttribute("categories", categoryService.getAll());

            return "/photos/edit";

        } catch (PhotoNotFoundException e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Foto con id " + id + " non trovata");

        }


    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Integer id, @Valid @ModelAttribute("photo") Photo formPhoto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("categories", categoryService.getAll());
            return "/photos/edit";

        }

        try {

            Photo updatePhoto = photoService.updatePhoto(formPhoto, id);
            return "redirect:/photos/" + Integer.toString(updatePhoto.getId());

        } catch (PhotoNotFoundException e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Foto con id " + id + "non trovata");

        }


    }

    // controller per eliminare una photo

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            boolean success = photoService.deleteById(id);
            if (success) {
                redirectAttributes.addFlashAttribute("message",
                        new AlertMessage(AlertMessage.AlertMessageType.SUCCESS, "Foto con id " + id + " cancellata"));

            } else {
        /*throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
            "Unable to delete book with id " + id);*/
                redirectAttributes.addFlashAttribute("message",
                        new AlertMessage(AlertMessage.AlertMessageType.ERROR, "Non posso cancellare la foto con id " + id));
            }

        } catch (PhotoNotFoundException e) {
            //throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            redirectAttributes.addFlashAttribute("message",
                    new AlertMessage(AlertMessage.AlertMessageType.ERROR, "Foto con id " + id + " non trovata"));
        }
        return "redirect:/photos";
    }

    @GetMapping("/{id}/cover")
    public String editCover(@PathVariable Integer id, Model model) {
        try {
            Photo photo = photoService.getById(id);
            // creo un oggetto ImageForm e lo setto come attributo del model
            ImageForm imageForm = new ImageForm();
            imageForm.setPhoto(photo);
            model.addAttribute("imageForm", imageForm);
            return "/photos/cover";
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/cover/save")
    public String doEditCover(@PathVariable Integer id, @Valid @ModelAttribute ImageForm imageForm,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "/photos/cover";
        }
        // persisto il file dell'immagine
        try {
            photoService.updateCover(id, imageForm);
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message",
                    new AlertMessage(AlertMessage.AlertMessageType.ERROR, "Impossibile aggiornare la foto"));
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/photos/" + Integer.toString(id);
    }

}
