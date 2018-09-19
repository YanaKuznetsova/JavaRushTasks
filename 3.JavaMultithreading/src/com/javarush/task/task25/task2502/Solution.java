package com.javarush.task.task25.task2502;

import java.util.*;

/* 
Машину на СТО не повезем!
*/
public class Solution {
    public static enum Wheel {
        FRONT_LEFT,
        FRONT_RIGHT,
        BACK_LEFT,
        BACK_RIGHT
    }

    public static class Car {
        protected List<Wheel> wheels;

        public Car() {
            String[] possibleWheels = loadWheelNamesFromDB();
            if (possibleWheels.length != 4) {
                throw new IllegalArgumentException();
            }
            wheels = new ArrayList<>();
            for (String possibleWheel: possibleWheels) {
                wheels.add(Wheel.valueOf(possibleWheel));
            }
            for (Wheel wheel : Wheel.values()) {
                if (!wheels.contains(wheel)){
                    throw new IllegalArgumentException();
                }
            }
        }

        protected String[] loadWheelNamesFromDB() {
            //this method returns mock data
            return new String[]{"FRONT_LEFT", "FRONT_RIGHT", "BACK_LEFT", "BACK_RIGHT"};
        }
    }

    public static void main(String[] args) {
    }
}
