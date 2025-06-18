package com.proj.site.diario.config;

import org.springframework.boot.web.reactive.context.StandardReactiveWebEnvironment;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;

import static com.proj.site.diario.config.FileSystem.DIRETORIO_DIARIO;

@Component
public class CriptoSystem {

    private final DateSystem dateSystem;

    public CriptoSystem(DateSystem dateSystem) {
        this.dateSystem = dateSystem;
    }

    public static final Path ARQUIVO_SENHA = DIRETORIO_DIARIO.resolve("chave.secret");

    public static void criarDiretorioSeNaoExistir() throws IOException {
        Files.createDirectories(DIRETORIO_DIARIO);
    }

    public static File getArquivoSenha() throws IOException {
        criarDiretorioSeNaoExistir();
        return ARQUIVO_SENHA.toFile();
    }

    public SecretKey gerarChaveAES() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        return keyGen.generateKey();
    }

    public void criptografarArquivo(File arquivoEntrada, File arquivoSaida, SecretKey chave) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, chave);

        byte[] inputBytes = Files.readAllBytes(arquivoEntrada.toPath());
        byte[] outputBytes = cipher.doFinal(inputBytes);

        Files.write(arquivoSaida.toPath(), outputBytes);
    }

    public void descriptografarArquivo(File arquivoEntrada, File arquivoSaida, SecretKey chave) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, chave);

        byte[] inputBytes = Files.readAllBytes(arquivoEntrada.toPath());
        byte[] outputBytes = cipher.doFinal(inputBytes);

        Files.write(arquivoSaida.toPath(), outputBytes);
    }

    public void salvarChave(SecretKey chave) throws IOException{
        byte[] chaveBytes = chave.getEncoded();
        String chaveBase64 = Base64.getEncoder().encodeToString(chaveBytes);
        Files.write(getArquivoSenha().toPath(), chaveBase64.getBytes(StandardCharsets.UTF_8));
    }

    public SecretKey carregarChave() throws IOException{
        byte[] chavesBytesBase64 = Files.readAllBytes(getArquivoSenha().toPath());
        byte[] chavesBytes = Base64.getDecoder().decode(new String(chavesBytesBase64, StandardCharsets.UTF_8));
        return new SecretKeySpec(chavesBytes, 0, chavesBytes.length, "AES");
    }

    public String lerArquivoDescriptografado(File arquivoEntrada, SecretKey chave) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, chave);

        byte[] inputBytes = Files.readAllBytes(arquivoEntrada.toPath());
        byte[] outputBytes = cipher.doFinal(inputBytes);

        return new String(outputBytes, StandardCharsets.UTF_8);
    }
}
