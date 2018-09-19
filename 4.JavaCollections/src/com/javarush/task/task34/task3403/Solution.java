package com.javarush.task.task34.task3403;

/* 
Разложение на множители с помощью рекурсии
*/
public class Solution {

    public static void main(String[] args) {
        //recursion(132, 2);
        //recursion(132);
    }

//    public void recursion(int n, int div) {
//        if (n % div == 0) {
//            System.out.print(div + " ");
//            if (n > div) {
//                recursion(n / div, div);
//            }
//        } else {
//            recursion(n, div + 1);
//        }
//    }
    public void recursion(int n) {
        int div = 2;
        while (div <= n) {
            if (n % div == 0) {
                System.out.print(div + " ");
                recursion(n / div);
                break;
            } else {
                div++;
            }
        }
    }
}
