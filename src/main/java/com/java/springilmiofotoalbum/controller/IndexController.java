package com.java.springilmiofotoalbum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    // metodo che riporta sempre alla index di photo

    @GetMapping
    public String index() {
        return "redirect:/photos";
    }

}
