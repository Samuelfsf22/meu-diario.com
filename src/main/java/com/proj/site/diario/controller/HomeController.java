package com.proj.site.diario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {return "home";}

    @GetMapping("/escrever")
    public String escreverPage(Model model) {
        model.addAttribute("conteudo", "fragments/escrever");
        return "index";
    }

    @GetMapping("/leitura")
    public String lerPage(Model model) {
        model.addAttribute("conteudo", "fragments/leitura");
        return "index";
    }

    @GetMapping("/info")
    public String infoPage(Model model) {
        model.addAttribute("conteudo", "fragments/info");
        return "index";
    }

    @GetMapping("/config")
    public String configPage(Model model) {
        model.addAttribute("conteudo", "fragments/config");
        return "index";
    }
}
