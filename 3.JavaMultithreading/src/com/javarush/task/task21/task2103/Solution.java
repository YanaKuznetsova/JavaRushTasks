package com.javarush.task.task21.task2103;

/* 
Все гениальное - просто!
*/
public class Solution {
    public static boolean calculate(boolean a, boolean b, boolean c, boolean d) {
        return c;
    }

    public static boolean calculate2(boolean a, boolean b, boolean c, boolean d) {
        return (a && b && c && !d) || (!a && c) || (!b && c) || (c && d);
    }

    public static void main(String[] args) {
        System.out.println(calculate(true, false, true, true));
        System.out.println(calculate(false, false, false, true));
        System.out.println(calculate(false, false, true, false));
        System.out.println(calculate(false, false, false, false));
        System.out.println(calculate(true, true, true, true));

        System.out.println();
        System.out.println(calculate2(true, false, true, true));
        System.out.println(calculate2(false, false, false, true));
        System.out.println(calculate2(false, false, true, false));
        System.out.println(calculate2(false, false, false, false));
        System.out.println(calculate2(true, true, true, true));

    }
}
