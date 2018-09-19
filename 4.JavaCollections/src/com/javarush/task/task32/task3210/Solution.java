package com.javarush.task.task32.task3210;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/* 
Используем RandomAccessFile
*/

public class Solution {
    public static void main(String... args) throws IOException {
        String filename = args[0];
        int number = Integer.parseInt(args[1]);
        String text = args[2];
        int textLength = text.length();

        boolean sameTextFlag = false;
        RandomAccessFile randomAccessFile = new RandomAccessFile(filename, "rw");
        if (randomAccessFile.length() >= number + textLength) {
            byte[] readBytes = new byte[textLength];
            randomAccessFile.seek(number);
            randomAccessFile.read(readBytes, 0, textLength);
            String readText = new String(readBytes);
            if (readText.equals(text)) {
                sameTextFlag = true;
            }
        }

        randomAccessFile.seek(randomAccessFile.length());
        if (sameTextFlag) {
            randomAccessFile.write("true".getBytes());
        } else {
            randomAccessFile.write("false".getBytes());
        }
    }
}
