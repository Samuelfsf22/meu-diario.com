package com.proj.site.diario.controller;

import com.proj.site.diario.model.Registro;
import com.proj.site.diario.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    FileService fileService;

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
    @PostMapping("/escrever")
    public String postarDiario(@RequestParam String titulo,
                               @RequestParam String tipoDataHora,
                               @RequestParam String campoTexto,
                               Model model) {
        System.out.println(titulo);
        System.out.println(tipoDataHora);
        if("automatico".equals(tipoDataHora)){
            System.out.println(LocalDateTime.now());
        }
        System.out.println();
        fileService.escrever(titulo, LocalDateTime.now(),campoTexto);
        model.addAttribute("conteudo", "fragments/escrever");
        return "index";
    }

    @GetMapping("/leitura")
    public String lerPage(Model model) throws Exception {

        try {
            List<Registro> registros = fileService.lerRegistros();
            model.addAttribute("registros", registros);
            for(Registro reg : registros){
                System.out.println(reg);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("conteudo", "fragments/leitura");
        return "index";
    }

    @GetMapping("/registro/{id}")
    public String lerRegistro(@PathVariable int id, Model model) throws Exception {
        try {
            List<Registro> registros = fileService.lerRegistros();
            Registro registro = registros.stream().filter(r -> r.getId() == id)
                    .findFirst().orElseThrow(() -> new IllegalArgumentException("Registro não encontrado"));
            System.out.println(registro);
            model.addAttribute("registro", registro);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("conteudo", "fragments/leituraPage");
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

    @PostMapping("/config")
    public String enviarConfig(@RequestParam String nomeString,@RequestParam String caminhoString, Model model){
        System.out.println("Texto recebido: " + nomeString + " " + caminhoString);

        System.out.println(fileService.mostrarCaminho());

        // Após processar, você pode redirecionar para outra página ou retornar o mesmo template
        // com um modelo atualizado
        model.addAttribute("conteudo", "fragments/config");
        return "index";
    };
}
