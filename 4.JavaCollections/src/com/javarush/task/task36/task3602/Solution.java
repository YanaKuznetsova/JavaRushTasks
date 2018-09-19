package com.javarush.task.task36.task3602;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/* 
Найти класс по описанию
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(getExpectedClass());
    }

    public static Class getExpectedClass() {
        //Class result = Collections.EMPTY_LIST.getClass();
        Class result = null;
        Class[] collectionsClasses = Collections.class.getDeclaredClasses();
        for (Class currentClass: collectionsClasses) {
            if (checkListInterface(currentClass) &
                    checkException(currentClass)) {
                result = currentClass;
                break;
            }
        }
        return result;
    }

    private static boolean checkException(Class currentClass) {
        boolean result = false;
        try {
            Constructor constructor = currentClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            List list = (List) constructor.newInstance();
            list.get(0);
        } catch (IndexOutOfBoundsException e) {
            result = true;
        } catch (NoSuchMethodException | ClassCastException| IllegalAccessException | InstantiationException | InvocationTargetException e) {

        }
        return result;
    }

    private static boolean checkListInterface(Class currentClass) {
        Class[] interfaces = currentClass.getInterfaces();
        for (Class currentInterface: interfaces) {
            if (currentInterface.toString().contains("List")) {
                return true;
            }
        }
        return false;
    }
}
