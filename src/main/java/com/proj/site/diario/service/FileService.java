package com.proj.site.diario.service;

import com.proj.site.diario.config.FileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    @Autowired
    FileSystem fileSystem;

    public void escrever(String texto){
        fileSystem.Write(texto);
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
}
