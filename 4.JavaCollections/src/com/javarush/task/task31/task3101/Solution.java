package com.javarush.task.task31.task3101;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
Проход по дереву файлов
*/
public class Solution {
    public static void main(String[] args) {
        File path = new File(args[0]);
//        File resultFileAbsolutePath = new File(args[1]);
//        if (FileUtils.isExist(resultFileAbsolutePath)) {
//            resultFileAbsolutePath.renameTo(new File(resultFileAbsolutePath.getParent() + "/allFilesContent.txt"));
//        }

        File resultFile = new File(args[1]);
        File resultFileAbsolutePath = new File(resultFile.getParent() + "/allFilesContent.txt");
        FileUtils.renameFile(resultFile, resultFileAbsolutePath);

        try (FileOutputStream fileOutputStream =
            new FileOutputStream(resultFileAbsolutePath)){

            List<File> fileList = getFileList(path);
            fileList.sort(new FilePathComparator());


            for (File fileToWrite: fileList) {
                FileInputStream fileInputStream = new FileInputStream(fileToWrite);
                while (fileInputStream.available() > 0) {
                    fileOutputStream.write(fileInputStream.read());
                }
                //fileOutputStream.write(System.lineSeparator().getBytes());
                fileOutputStream.write("\n".getBytes());
                fileOutputStream.flush();

                fileInputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static List<File> getFileList(File path) {
        List<File> fileList = new ArrayList<>();
        for (File file : path.listFiles()) {
            if (file.isDirectory()) {
                fileList.addAll(getFileList(file));
                continue;
            }
            if (file.length() <= 50) {
                fileList.add(file);
            }
        }
        return fileList;
    }

    static class FilePathComparator implements Comparator<File>{

        @Override
        public int compare(File first, File second) {
            return first.getName().compareTo(second.getName());
        }
    }
}

