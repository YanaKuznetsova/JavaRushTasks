package com.javarush.task.task32.task3204;

import java.io.ByteArrayOutputStream;

/* 
Генератор паролей
*/
public class Solution {
    public static void main(String[] args) {
        ByteArrayOutputStream password = getPassword();
        System.out.println(password.toString());
    }

    public static ByteArrayOutputStream getPassword() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (int i = 0; i < 3; i++) {
            byteArrayOutputStream.write((int) (65 + Math.random()*26));
        }
        for (int i = 0; i < 2; i++) {
            byteArrayOutputStream.write((int) (48 + Math.random()*10));
        }
        for (int i = 0; i < 3; i++) {
            byteArrayOutputStream.write((int) (97 + Math.random()*26));
        }
        return byteArrayOutputStream;
    }
}