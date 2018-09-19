package com.javarush.task.task38.task3812;

/* 
Обработка аннотаций
*/

import java.lang.annotation.Annotation;

public class Solution {
    public static void main(String[] args) {
        printFullyQualifiedNames(Solution.class);
        printFullyQualifiedNames(SomeTest.class);

        printValues(Solution.class);
        printValues(SomeTest.class);
    }

    public static boolean printFullyQualifiedNames(Class c) {
        boolean result = false;
        if (c.isAnnotationPresent(PrepareMyTest.class)) {
            PrepareMyTest prepareMyTest = (PrepareMyTest) c.getAnnotation(PrepareMyTest.class);
            for (String string: prepareMyTest.fullyQualifiedNames()) {
                System.out.println(string);
            }
            result = true;
        }
        return result;
    }

    public static boolean printValues(Class c) {
        boolean result = false;
        if (c.isAnnotationPresent(PrepareMyTest.class)) {
            PrepareMyTest prepareMyTest = (PrepareMyTest) c.getAnnotation(PrepareMyTest.class);
            for (Class clazz: prepareMyTest.value()) {
                System.out.println(clazz.getSimpleName());
            }
            result = true;
        }
        return result;
    }
}
