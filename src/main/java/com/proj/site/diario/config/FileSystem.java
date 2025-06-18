package com.proj.site.diario.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileSystem {
    public static final Path DIRETORIO_DIARIO = Paths.get(System.getProperty("user.home"), "Documents", "Diario");
    public static final Path ARQUIVO_CRIPTOGRAFADO = DIRETORIO_DIARIO.resolve("diarioLogs.drt");

    public static void criarDiretorioSeNaoExistir() throws IOException {
        Files.createDirectories(DIRETORIO_DIARIO);
    }

    public static File getArquivoCriptografado() throws IOException {
        criarDiretorioSeNaoExistir();
        return ARQUIVO_CRIPTOGRAFADO.toFile();
    }
}
