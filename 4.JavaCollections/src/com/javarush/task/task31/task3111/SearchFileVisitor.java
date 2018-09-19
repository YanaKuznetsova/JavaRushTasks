package com.javarush.task.task31.task3111;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.FileVisitResult.*;

public class SearchFileVisitor extends SimpleFileVisitor<Path> {

    private String partOfName;
    private String partOfContent;
    private int minSize;
    private int maxSize;

    private boolean partOfNameCheck;
    private boolean partOfContentCheck;
    private boolean minSizeCheck;
    private boolean maxSizeCheck;

    private List<Path> foundFiles = new ArrayList<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//        byte[] content = Files.readAllBytes(file); // размер файла: content.length
//        return super.visitFile(file, attrs);
        if (! attrs.isRegularFile()) {
            return CONTINUE;
        }
        if (partOfNameCheck && file.getFileName().toString().indexOf(this.partOfName) == -1) {
            return CONTINUE;
        }
        if (partOfContentCheck && new String(Files.readAllBytes(file)).indexOf(partOfContent) == -1) {
            return CONTINUE;
        }
        if (minSizeCheck && attrs.size() < minSize) {
            return CONTINUE;
        }
        if (maxSizeCheck && attrs.size() > maxSize) {
            return CONTINUE;
        }
        foundFiles.add(file);
        return CONTINUE;
    }

    public void setPartOfName(String partOfName) {
        this.partOfName = partOfName;
        this.partOfNameCheck = true;
    }

    public void setPartOfContent(String partOfContent) {
        this.partOfContent = partOfContent;
        this.partOfContentCheck = true;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
        this.minSizeCheck = true;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        this.maxSizeCheck = true;
    }

    public List<Path> getFoundFiles() {
        return foundFiles;
    }
}
