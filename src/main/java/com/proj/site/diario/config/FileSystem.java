package com.proj.site.diario.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;

@Component
public class FileSystem {

    @Autowired
    DateSystem dateSystem;
    private String path = "C:\\Users\\Marcio\\Downloads\\ideaIC-2024.3.3.win\\projetos\\diario\\src\\main\\java\\com\\proj\\site\\diario\\arquivoTeste";
    private String fileName = "\\teste.txt";

    public static void criarArquivoSeNaoExistir(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("Arquivo criado: " + file.getAbsolutePath());
                } else {
                    System.out.println("Não foi possível criar o arquivo.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Erro ao criar o arquivo.");
            }
        }
    }

    public void Read(){
        criarArquivoSeNaoExistir(path + fileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(path + fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Write(String title, LocalDateTime date, String text){
        criarArquivoSeNaoExistir(path + fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path + fileName, true))){
            writer.write("Titulo: " + title);
            writer.newLine();
            writer.write("Data: " + dateSystem.DateConverter(date));
            writer.newLine();
            writer.write("Conteúdo: " + text);
            writer.newLine();
            writer.write("");
            writer.newLine();
            writer.write("------");
            writer.newLine();
            System.out.println("Conteúdo escrito no arquivo.");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String getPath() {
        return path;
    }

    public String getFileName() {
        return fileName;
    }

    public void pathChanger(String newPath){
        this.path = newPath;
    }

    public void fileNameChanger(String name){
        this.fileName = name;
    }
}
