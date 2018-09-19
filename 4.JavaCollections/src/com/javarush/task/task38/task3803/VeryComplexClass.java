package com.javarush.task.task38.task3803;

/* 
Runtime исключения (unchecked exception)
*/

import java.util.ArrayList;
import java.util.List;

public class VeryComplexClass {
    public void methodThrowsClassCastException() {
        Object i = Integer.valueOf(5);
        String s = (String) i;
    }

    public void methodThrowsNullPointerException() {
        List<String> list = null;
        list.get(0);
    }

    public static void main(String[] args) {

    }
}
