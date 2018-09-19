package com.javarush.task.task31.task3102;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/* 
Находим все файлы
*/
public class Solution {
    public static List<String> getFileTree(String root) throws IOException {
        List<String> fileTreeList = new ArrayList<>();
        Stack<File> stack = new Stack();
        stack.push(new File(root));

        while (!stack.empty()) {
            File file = stack.pop();
            if (file.isDirectory()){
                for (File f: file.listFiles()){
                    stack.push(f);
                }
            }
            if (file.isFile()) {
                fileTreeList.add(file.getAbsolutePath());
            }
        }
        return fileTreeList;
    }

    public static void main(String[] args) {
        
    }
}
