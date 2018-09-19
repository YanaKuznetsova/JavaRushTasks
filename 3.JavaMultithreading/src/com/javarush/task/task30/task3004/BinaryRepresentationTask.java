package com.javarush.task.task30.task3004;

import java.util.concurrent.RecursiveTask;

public class BinaryRepresentationTask extends RecursiveTask<String>{
    private int number;

    public BinaryRepresentationTask(int number) {
        this.number = number;
    }

    @Override
    protected String compute() {
        int a = number % 2;
        int b = number / 2;
        String result = String.valueOf(a);

        if (b > 0) {
            BinaryRepresentationTask task =  new BinaryRepresentationTask(b);
            task.fork();
            return task.join() + result;
        }
        return result;


    }
}
