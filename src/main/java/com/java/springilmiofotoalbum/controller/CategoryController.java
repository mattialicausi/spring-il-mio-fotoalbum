package com.java.springilmiofotoalbum.controller;

import com.java.springilmiofotoalbum.exceptions.CategoryNotFoundException;
import com.java.springilmiofotoalbum.exceptions.PhotoNotFoundException;
import com.java.springilmiofotoalbum.model.AlertMessage;
import com.java.springilmiofotoalbum.model.Category;
import com.java.springilmiofotoalbum.repository.CategoryRepository;
import com.java.springilmiofotoalbum.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String index(@RequestParam("id")Optional<Integer> idParam, Model model) {

        model.addAttribute("list", categoryService.getAll());

        if (idParam.isPresent()) {

            // aggiungo al model la categoria presa per id

            model.addAttribute("categoryObj", categoryService.getById(idParam.get()));
        } else {

            // aggiungo al model una categoria nuova

            model.addAttribute("categoryObj", new Category());
        }

        return "/categories/index";

    }

    @PostMapping("/save")
    public String doSave(@Valid @ModelAttribute(name = "categoryObj") Category category, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            // ricreo la view ripassando la category

            model.addAttribute("list", categoryService.getAll());
            return "/categories/index";

        }
            // salvo i dati

        if (category.getId() != null) {

            categoryService.update(category);

        } else {

            categoryService.create(category);

        }

        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            boolean success = categoryService.deleteById(id);
            if (success) {
                redirectAttributes.addFlashAttribute("message",
                        new AlertMessage(AlertMessage.AlertMessageType.SUCCESS, "Categoria con id " + id + " cancellata"));

            } else {
        /*throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
            "Unable to delete book with id " + id);*/
                redirectAttributes.addFlashAttribute("message",
                        new AlertMessage(AlertMessage.AlertMessageType.ERROR, "Non posso cancellare la categoria con id " + id));
            }

        } catch (CategoryNotFoundException e) {
            //throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            redirectAttributes.addFlashAttribute("message",
                    new AlertMessage(AlertMessage.AlertMessageType.ERROR, "Categoria con id " + id + " non trovata"));
        }
        return "redirect:/categories";
    }

}
