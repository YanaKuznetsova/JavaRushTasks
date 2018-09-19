package com.javarush.task.task31.task3106;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipInputStream;

/*
Разархивируем файл
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        String resultFileName = args[0];
        List<String> fileNameParts = new ArrayList();
        for (int i = 1; i < args.length; i++) {
            fileNameParts.add(args[i]);
        }
        Collections.sort(fileNameParts);
        List<FileInputStream> fileInputStreams = new ArrayList();
        for (String fileName: fileNameParts) {
            fileInputStreams.add(new FileInputStream(fileName));
        }

        SequenceInputStream sequenceInputStream = new SequenceInputStream(Collections.enumeration(fileInputStreams));
        ZipInputStream zipInputStream = new ZipInputStream(sequenceInputStream);
        FileOutputStream fileOutputStream = new FileOutputStream(resultFileName);

        byte[] buffer = new byte[1024 * 1024];

        while (zipInputStream.getNextEntry() != null) {
            int bytesRead;
            while ((bytesRead = zipInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
        }
        fileOutputStream.flush();

        sequenceInputStream.close();
        zipInputStream.close();
        fileOutputStream.close();
    }
}
