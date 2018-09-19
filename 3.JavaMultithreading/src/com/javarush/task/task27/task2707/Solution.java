package com.javarush.task.task27.task2707;

/* 
Определяем порядок захвата монитора
*/
public class Solution {
    public void someMethodWithSynchronizedBlocks(Object obj1, Object obj2) {
        synchronized (obj1) {
            synchronized (obj2) {
                System.out.println(obj1 + " " + obj2);
            }
        }
    }

    public static boolean isNormalLockOrder(final Solution solution, final Object o1, final Object o2) throws Exception {
        Thread thread1 = new Thread() {
            @Override
            public void run(){
                solution.someMethodWithSynchronizedBlocks(o1, o2);
            }
        };

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                synchronized (o2) {
                    Thread.currentThread().interrupt();
                }
            }
        };


        synchronized (o1) {
            thread1.start();
            while (true) {
                if (thread1.getState() == Thread.State.BLOCKED) {
                    break;
                }
            }

            thread2.start();
            while (!thread2.isInterrupted()) {
                Thread.State state = thread2.getState();
                if (state != Thread.State.RUNNABLE) {
                    if (state == Thread.State.BLOCKED) {
                        return false;
                    }
                    return true;
                }
            }
        }
        return true;
    }


    public static void main(String[] args) throws Exception {
        final Solution solution = new Solution();
        final Object o1 = new Object();
        final Object o2 = new Object();

        System.out.println(isNormalLockOrder(solution, o1, o2));
    }
}
