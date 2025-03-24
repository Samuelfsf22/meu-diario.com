package com.proj.site.diario.config;

import java.io.*;

public class FileSystem {

    private String path;
    private String fileName;

    public void Read(){

        try (BufferedReader reader = new BufferedReader(new FileReader(path + fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Write(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path + fileName))){

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void pathChanger(String newPath){
        this.path = newPath;
    }

    public void fileNameChanger(String name){
        this.fileName = name;
    }
}
