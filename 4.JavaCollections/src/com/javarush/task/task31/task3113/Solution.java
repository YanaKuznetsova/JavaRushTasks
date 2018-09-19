package com.javarush.task.task31.task3113;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;

/* 
Что внутри папки?
*/
public class Solution {

    static int dirCounter = 0;
    static int fileCounter = 0;
    static long filesSize = 0;

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Path pathToDir = Paths.get(bufferedReader.readLine());
        if (Files.isDirectory(pathToDir)) {
            Files.walkFileTree(pathToDir, new FileTreeVisitor());
            System.out.println("Всего папок - " + (dirCounter - 1));
            System.out.println("Всего файлов - " + fileCounter);
            System.out.println("Общий размер - " + filesSize);
        } else {
            System.out.println(pathToDir.toString() + " - не папка");
        }
    }

    private static class FileTreeVisitor extends SimpleFileVisitor<Path> {

        @Override
        public FileVisitResult preVisitDirectory(Path currentPath, BasicFileAttributes attrs) throws IOException {
            dirCounter += 1;
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path currentPath, BasicFileAttributes attrs) throws IOException {
            fileCounter += 1;
            filesSize += attrs.size();
            return CONTINUE;
        }
    }
}
