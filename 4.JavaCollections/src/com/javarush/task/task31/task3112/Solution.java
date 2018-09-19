package com.javarush.task.task31.task3112;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/* 
Загрузчик файлов
*/
public class Solution {

    public static void main(String[] args) throws IOException {
        Path passwords = downloadFile("https://javarush.ru/testdata/secretPasswords.txt",
                Paths.get("C:/Tmpa"));

        for (String line : Files.readAllLines(passwords)) {
            System.out.println(line);
        }
    }

    public static Path downloadFile(String urlString, Path downloadDirectory) throws IOException {
        // implement this method
        String fileName = urlString.substring(urlString.lastIndexOf("/")+1);
        Path downloadFile = Paths.get(downloadDirectory.toString() + "/" + fileName);

        URL url = new URL(urlString);

        InputStream inputStream = url.openStream();
        Path tmpFile = Files.createTempFile("tmp-",".tmp");

        Files.copy(inputStream, tmpFile.toAbsolutePath(), REPLACE_EXISTING);
        inputStream.close();

        if (Files.notExists(downloadDirectory)){
            Files.createDirectories(downloadDirectory);
        }
        Files.move(tmpFile, downloadFile);
        return downloadFile;
    }
}
