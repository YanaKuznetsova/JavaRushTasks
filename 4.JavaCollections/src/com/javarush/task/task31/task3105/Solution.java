package com.javarush.task.task31.task3105;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/* 
Добавление файла в архив
*/
public class Solution {
    //    public static void main(String[] args) throws IOException {
//        Path pathToFile = Paths.get(args[0]);
//        String fileName = pathToFile.getFileName().toString();
//        String pathToZip = args[1];
//
//        List<ZipEntry> zipEntryList = new ArrayList<>();
//        List<byte[]> zipContentList = new ArrayList<>();
//        boolean fileNotFound = true;
//
//        try(ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(pathToZip))) {
//            ZipEntry currentEntry;
//            while ((currentEntry = zipInputStream.getNextEntry()) != null) {
//                long entryLength = currentEntry.getSize();
//                if (new File(currentEntry.getName()).getName().equals(fileName)) {
//                    fileNotFound = false;
//                    zipEntryList.add(new ZipEntry(currentEntry.getName()));
//                    zipContentList.add(Files.readAllBytes(pathToFile));
//                } else {
//                    zipEntryList.add(currentEntry);
//                    if (currentEntry.isDirectory()) {
//                        zipContentList.add(null);
//                    } else {
//                        zipContentList.add(readZip(zipInputStream, entryLength));
//                    }
//                }
//                zipInputStream.closeEntry();
//            }
//        }
//        //zipInputStream.close();
//
//        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(pathToZip))) {
//            for (int i = 0; i < zipEntryList.size(); i++) {
//                zipOutputStream.putNextEntry(zipEntryList.get(i));
//                if (zipContentList.get(i) != null) {
//                    zipOutputStream.write(zipContentList.get(i));
//                }
//                zipOutputStream.closeEntry();
//            }
//            if (fileNotFound) {
//                zipOutputStream.putNextEntry(new ZipEntry("new/"));
//                zipOutputStream.closeEntry();
//
//                zipOutputStream.putNextEntry(new ZipEntry("new/" + fileName));
//                File file = new File(pathToFile.toString());
//                Files.copy(file.toPath(), zipOutputStream);
//                zipOutputStream.closeEntry();
//            }
//        }
//        //zipOutputStream.close();
//    }
//
//    private static byte[] readZip(InputStream inputStream, long fileLength) throws IOException {
//        byte[] buffer = new byte[(int) fileLength];
//        inputStream.read(buffer);
//        return buffer;
//    }
    public static void main(String[] args) throws IOException {
        String fileName = args[0];
        String zipFileName = args[1];
        File file = new File(fileName);

        Map<String, ByteArrayOutputStream> archivedFiles = new HashMap<>();
        try (ZipInputStream zipReader = new ZipInputStream(new FileInputStream(zipFileName))) {
            ZipEntry entry;
            while ((entry = zipReader.getNextEntry()) != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int count = 0;
                while ((count = zipReader.read(buffer)) != -1)
                    byteArrayOutputStream.write(buffer, 0, count);

                archivedFiles.put(entry.getName(), byteArrayOutputStream);
            }
        }

        try (ZipOutputStream zipWriter = new ZipOutputStream(new FileOutputStream(zipFileName))) {
            for (Map.Entry<String, ByteArrayOutputStream> pair : archivedFiles.entrySet()) {
                if (pair.getKey().substring(pair.getKey().lastIndexOf("/") + 1).equals(file.getName())) continue;
                zipWriter.putNextEntry(new ZipEntry(pair.getKey()));
                zipWriter.write(pair.getValue().toByteArray());
            }

            ZipEntry zipEntry = new ZipEntry("new/" + file.getName());
            zipWriter.putNextEntry(zipEntry);
            Files.copy(file.toPath(), zipWriter);
        }


    }
}
