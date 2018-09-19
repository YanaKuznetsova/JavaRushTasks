package com.javarush.task.task38.task3802;

/* 
Проверяемые исключения (checked exception)
*/

import java.io.*;

public class VeryComplexClass {
    public void veryComplexMethod() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("a:\\b//c.txt")));
    }

    public static void main(String[] args) {

    }
}
