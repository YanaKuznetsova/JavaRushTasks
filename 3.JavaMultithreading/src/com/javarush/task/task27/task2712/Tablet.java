package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.AdvertisementManager;
import com.javarush.task.task27.task2712.ad.NoVideoAvailableException;
import com.javarush.task.task27.task2712.kitchen.Order;

import java.io.IOException;
import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet extends Observable {

    final int number;
    static Logger logger = Logger.getLogger(Tablet.class.getName());


    public Tablet(int number) {
        this.number = number;
    }

    public Order createOrder(){
        Order newOrder = null;
        try {
            newOrder = new Order(this);
            ConsoleHelper.writeMessage(newOrder.toString());
            if (!newOrder.isEmpty()) {
                try{
                    int timeSeconds = newOrder.getTotalCookingTime() * 60;
                    new AdvertisementManager(timeSeconds).processVideos();
                } catch (NoVideoAvailableException e) {
                    logger.log(Level.INFO, "No video is available for the order " + newOrder);
                }
                setChanged();
                notifyObservers(newOrder);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        }
        return newOrder;
    }

    @Override
    public String toString() {
        return "Tablet{" + "number=" + number + '}';
    }
}
