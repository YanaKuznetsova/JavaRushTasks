package com.javarush.task.task26.task2608;

/* 
Мудрый человек думает раз, прежде чем два раза сказать
*/
public class Solution {
    int var1;
    int var2;
    int var3;
    int var4;
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public Solution(int var1, int var2, int var3, int var4) {
        this.var1 = var1;
        this.var2 = var2;
        this.var3 = var3;
        this.var4 = var4;
    }

    public int getSumOfVar1AndVar2() {
        int result;
        synchronized(lock1) {
            result = var1 + var2;
        }
        return result;
    }

    public int getSumOfVar3AndVar4() {
        int result;
        synchronized(lock2) {
            result = var3 + var4;
        }
        return result;
    }

    public static void main(String[] args) {

    }
}
