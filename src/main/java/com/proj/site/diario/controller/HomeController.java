package com.proj.site.diario.controller;

import com.proj.site.diario.model.Registro;
import com.proj.site.diario.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
                               @RequestParam(required = false) String dataManual,
                               @RequestParam(required = false) String horaManual,
                               @RequestParam(required = false) String minutoManual,
                               Model model) throws Exception {
        if("automatico".equals(tipoDataHora)){
            fileService.escrever(titulo, LocalDateTime.now(),campoTexto);
        }else{
            String horaFormatada = String.format("%02d", Integer.parseInt(horaManual));
            String minutoFormatado = String.format("%02d", Integer.parseInt(minutoManual));

            String stringDataManual = dataManual + " " + horaFormatada + ":" + minutoFormatado;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime data = LocalDateTime.parse(stringDataManual, formatter);
            fileService.escrever(titulo, data,campoTexto);
        }
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

    @GetMapping("/registro/editar/{id}")
    public String acessarRegistroParaEdit(@PathVariable int id, Model model) throws Exception {
        try {
            List<Registro> registros = fileService.lerRegistros();
            Registro registro = registros.stream().filter(r -> r.getId() == id)
                    .findFirst().orElseThrow(() -> new IllegalArgumentException("Registro não encontrado"));
            System.out.println(registro);
            model.addAttribute("registro", registro);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("conteudo", "fragments/editarPage");
        return "index";
    }
    @PostMapping("/registro/editar/{id}")
    public String editar(@PathVariable int id,
                         @RequestParam String titulo,
                         @RequestParam String campoTexto,
                         Model model) throws Exception{
        Registro registro = fileService.editar(id,titulo,campoTexto);
        return "redirect:/leitura";
    };
    @DeleteMapping("/registro/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) throws Exception {
        fileService.apagar(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/info")
    public String infoPage(Model model) {
        model.addAttribute("conteudo", "fragments/info");
        return "index";
    }
}
