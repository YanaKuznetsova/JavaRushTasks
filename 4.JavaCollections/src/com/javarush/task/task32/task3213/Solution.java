package com.javarush.task.task32.task3213;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/* 
Шифр Цезаря
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        StringReader reader = new StringReader("Khoor#Dpljr#&C,₷B'3");
        System.out.println(decode(reader, -3));  //Hello Amigo #@)₴?$0
    }

    public static String decode(StringReader reader, int key) throws IOException {
        String resultString = "";
        StringWriter stringWriter = new StringWriter();
        try{
            char[] nextChar = new char[1];
            while (reader.read(nextChar) > 0) {
                stringWriter.write(nextChar[0] + key);
            }
            resultString = stringWriter.toString();
        } catch (Exception e) {}
        return resultString;
    }
}
