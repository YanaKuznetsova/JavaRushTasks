package com.javarush.task.task32.task3209;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class HTMLFileFilter extends FileFilter {

    @Override
    public boolean accept(File file) {
        if (file.isDirectory()){
            return true;
        }
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".html") || fileName.endsWith(".htm");
    }

    @Override
    public String getDescription() {
        return "HTML и HTM файлы";
    }
}
