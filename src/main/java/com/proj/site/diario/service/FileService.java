package com.proj.site.diario.service;

import com.proj.site.diario.config.CriptoSystem;
import com.proj.site.diario.config.FileSystem;
import com.proj.site.diario.model.Registro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.proj.site.diario.config.CriptoSystem.getArquivoSenha;
import static com.proj.site.diario.config.FileSystem.getArquivoCriptografado;

@Service
public class FileService {

    @Autowired
    FileSystem fileSystem;
    @Autowired
    CriptoSystem criptoSystem;

    public void escrever(String titulo, LocalDateTime data, String texto) throws Exception {
        SecretKey chave;
        File arquivoCriptografado = getArquivoCriptografado();
        File arquivoSenha = getArquivoSenha();

        // Carrega ou gera a chave AES
        if (arquivoSenha.exists()) {
            chave = criptoSystem.carregarChave();
        } else {
            chave = criptoSystem.gerarChaveAES();
            criptoSystem.salvarChave(chave);
        }

        // Monta novo conteúdo
        String novoRegistro = "Titulo: " + titulo + "\n" +
                "Data: " + data.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + "\n" +
                "Conteúdo: " + texto + "\n\n------\n";

        String conteudoExistente = "";

        // Se o arquivo já existir, descriptografa e lê o conteúdo anterior
        if (arquivoCriptografado.exists()) {
            conteudoExistente = criptoSystem.lerArquivoDescriptografado(arquivoCriptografado, chave);
        }

        // Junta o conteúdo antigo com o novo
        String conteudoFinal = conteudoExistente + novoRegistro;

        // Cria temporário com o conteúdo combinado
        Path tempPath = Paths.get("temp.txt");
        Files.writeString(tempPath, conteudoFinal, StandardCharsets.UTF_8);

        // Criptografa e salva no arquivo final
        criptoSystem.criptografarArquivo(tempPath.toFile(), arquivoCriptografado, chave);

        Files.deleteIfExists(tempPath); // apaga o temporário, opcional

        System.out.println("Conteúdo salvo criptografado.");
    }

    public List<Registro> lerRegistros() throws Exception{
        File arquivoCriptografado = getArquivoCriptografado();
        SecretKey chave = criptoSystem.carregarChave();

        String conteudo = criptoSystem.lerArquivoDescriptografado(arquivoCriptografado, chave);
        System.out.println("CONTEÚDO DO ARQUIVO DESCRIPTOGRAFADO:\n" + conteudo);

        String[] blocos = conteudo.split("(?m)^-{5,}\\s*$");

        List<Registro> registros = new ArrayList<>();
        Pattern pattern = Pattern.compile(
                "Titulo:\\s*(.*?)\\R" +
                        "Data:\\s*(.*?)\\R+" +
                        "Conteúdo:\\s*(.*)",
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
        System.out.println("");
        System.out.println(registros.size());
        return registros;
    };

    public Registro editar(int id, String titulo, String texto) throws Exception{
        try {
            List<Registro> registros = lerRegistros();

            System.out.println("");
            System.out.println("Antes da edição:");
            for(Registro reg : registros){
                System.out.println(reg);
            }
            System.out.println("registros é nulo? " + (registros == null));
            System.out.println("tamanho da lista: " + registros.size());
            for(Registro r : registros){
                if(r.getId() == id){
                    r.setTitulo(titulo);
                    r.setConteudo(texto);
                    System.out.println(r);
                    salvarMudanca(registros);
                    return r;
                }
            }
            System.out.println("");
            System.out.println("Depois da edição:");
            for(Registro reg : registros){
                System.out.println(reg);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void apagar(int id) throws Exception {
        try {
            List<Registro> registros = lerRegistros();

            System.out.println("");
            System.out.println("Antes do delete:");
            for(Registro reg : registros){
                System.out.println(reg);
            }
            registros.removeIf(reg -> reg.getId() == id);
            salvarMudanca(registros);
            System.out.println("");
            System.out.println("Depois do delete:");
            for(Registro reg : registros){
                System.out.println(reg);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void salvarMudanca(List<Registro> registros){
        try{
            Path tempPath = Path.of("temp.txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempPath.toFile()))) {

                for (Registro reg : registros) {
                    writer.write("Titulo: " + reg.getTitulo());
                    writer.newLine();
                    writer.write("Data: " + reg.getData());
                    writer.newLine();
                    writer.write("Conteúdo: " + reg.getConteudo());
                    writer.newLine();
                    writer.write("");
                    writer.newLine();
                    writer.write("------");
                    writer.newLine();
                }
            }
            File arquivoTemporario = tempPath.toFile();
            File arquivoCriptografado = getArquivoCriptografado();
            SecretKey chave = criptoSystem.carregarChave();

            criptoSystem.criptografarArquivo(arquivoTemporario, arquivoCriptografado, chave);

            // Remove o arquivo texto plano
            Files.deleteIfExists(tempPath);

            System.out.println("Mudanças salvas e arquivo criptografado com sucesso.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    };
}
