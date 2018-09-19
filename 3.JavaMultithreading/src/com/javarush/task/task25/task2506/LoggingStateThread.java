package com.javarush.task.task25.task2506;

public class LoggingStateThread extends Thread{
    private Thread target;

    LoggingStateThread(Thread target) {
        this.target = target;
    }

    @Override
    public void run(){
        State currentState = target.getState();
        System.out.println(currentState);
        while (!currentState.equals(State.TERMINATED)) {
            if (!target.getState().equals(currentState)) {
                currentState = target.getState();
                System.out.println(currentState);
            }
        }
        this.interrupt();
    }

}