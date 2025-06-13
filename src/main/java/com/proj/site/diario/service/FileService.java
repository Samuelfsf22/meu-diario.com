package com.proj.site.diario.service;

import com.proj.site.diario.config.FileSystem;
import com.proj.site.diario.model.Registro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FileService {

    @Autowired
    FileSystem fileSystem;

    public void escrever(String titulo, LocalDateTime data, String texto){
        fileSystem.Write(titulo, data,texto);
    };

    public void ler(){
        fileSystem.Read();
    };
    public String mudarCaminho(String caminho){
        fileSystem.pathChanger(caminho);
        System.out.print("Caminho mudado para " + caminho);
      return "Caminho mudado para " + caminho;
    };

    public String mudarNome(String nome){
        fileSystem.pathChanger(nome);
        System.out.print("Nome mudado para " + nome);
        return "Nome mudado para " + nome;
    }

    public String mostrarCaminho(){
        return "Caminho: " + fileSystem.getPath() + " Nome: " + fileSystem.getFileName();
    }

    public List<Registro> lerRegistros() throws Exception{
        String texto = Files.readString(Path.of(fileSystem.getPath() + fileSystem.getFileName()));
        String[] blocos = texto.split("(?m)^-{5,}\\s*$");

        List<Registro> registros = new ArrayList<>();
        Pattern pattern = Pattern.compile(
                "Titulo:\\s*(.*?)\\R" +
                        "Data:\\s*(.*?)\\R\\R" +
                        "(.*)",
                Pattern.DOTALL
        );

        int id = 1;
        for (String bloco : blocos){
            Matcher matcher =  pattern.matcher(bloco.trim());
            if(matcher.find()){
                registros.add(new Registro(
                        id++,
                        matcher.group(1).trim(),
                        matcher.group(2).trim(),
                        matcher.group(3).trim()
                ));
            }
        }

        return registros;
    };
}
