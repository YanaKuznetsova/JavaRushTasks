package com.javarush.task.task22.task2211;

import java.io.*;
import java.nio.charset.Charset;

/* 
Смена кодировки
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        String fileNameWin = args[0];
        String fileNameUTF = args[1];

        InputStream reader = new FileInputStream(fileNameWin);
        OutputStream writer = new FileOutputStream(fileNameUTF);

        byte[] buffer = new byte[1000];
        while (reader.available() > 0 ) {
            reader.read(buffer);
            String s = new String(buffer, Charset.forName("Windows-1251"));
            buffer = s.getBytes(Charset.forName("UTF-8"));
            writer.write(buffer);
        }

        reader.close();
        writer.close();
    }
}
