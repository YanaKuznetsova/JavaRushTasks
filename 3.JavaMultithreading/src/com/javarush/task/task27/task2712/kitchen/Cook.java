package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;

import java.util.Observable;
import java.util.Observer;

public class Cook extends Observable implements Observer{

    private String name;

    public Cook(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable tablet, Object order) {
        Order currentOrder = (Order) order;
        ConsoleHelper.writeMessage("Start cooking - " + currentOrder + ", cooking time "
                + currentOrder.getTotalCookingTime() + "min");
        setChanged();
        notifyObservers(currentOrder);
    }

    @Override
    public String toString() {
        return name;
    }
}
