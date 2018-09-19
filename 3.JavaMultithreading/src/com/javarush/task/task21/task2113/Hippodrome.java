package com.javarush.task.task21.task2113;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 001 01.02.18.
 */
public class Hippodrome {

    static Hippodrome game;
    private List<Horse> horses;

    public Hippodrome(List<Horse> horses) {
        this.horses = horses;
    }

    public static void main(String[] args) throws InterruptedException {
        List<Horse> horses = new ArrayList<>();
        horses.add(new Horse("Sleven", 3, 0));
        horses.add(new Horse("Lucky", 3, 0));
        horses.add(new Horse("Gomer", 3, 0));
        game = new Hippodrome(horses);

        game.run();
        game.printWinner();
    }

    public List<Horse> getHorses() {
        return horses;
    }

    public void move(){
        for (Horse horse: horses){
            horse.move();
        }
    }

    public void print(){
        for (Horse horse: horses){
            horse.print();
        }
        for (int i = 0; i < 10; i++){
            System.out.println();
        }
    }

    public void run() throws InterruptedException {
        for (int i = 1; i <= 100; i++){
            move();
            print();
            Thread.sleep(200);
        }
    }

    public Horse getWinner(){
        Horse winner = horses.get(0);
        for (Horse horse: horses){
            if (winner.getDistance() < horse.getDistance()) {
                winner = horse;
            }
        }
        return winner;
    }

    public void printWinner(){
        System.out.println("Winner is " + getWinner().getName() + "!");
    }


}
