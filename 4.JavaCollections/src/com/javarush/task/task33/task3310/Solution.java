package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Solution {

    public static void main(String[] args) {
        testStrategy(new HashMapStorageStrategy(), 10000);
        testStrategy(new OurHashMapStorageStrategy(), 10000);
        testStrategy(new OurHashBiMapStorageStrategy(), 10000);
        testStrategy(new HashBiMapStorageStrategy(), 10000);
        testStrategy(new DualHashBidiMapStorageStrategy(), 10000);
        System.out.println("");
        testStrategy(new HashMapStorageStrategy(), 500);
        testStrategy(new FileStorageStrategy(), 500);

    }

    public static Set<Long> getIds(Shortener shortener, Set<String> strings) {
        Set<Long> idsSet = new HashSet<>();
        for (String string: strings) {
            idsSet.add(shortener.getId(string));
        }
        return idsSet;
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys) {
        Set<String> stringsSet = new HashSet<>();
        for (Long key: keys) {
            stringsSet.add(shortener.getString(key));
        }
        return stringsSet;
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber){
        Helper.printMessage(strategy.getClass().getSimpleName());

        Set<String> testStringSet = new HashSet<>();
        for (int i = 0; i < elementsNumber; i++) {
            testStringSet.add(Helper.generateRandomString());
        }

        Shortener shortener = new Shortener(strategy);

        Long start = new Date().getTime();
        Set<Long> testIdsSet = getIds(shortener, testStringSet);
        Long end = new Date().getTime();
        Helper.printMessage(String.format("GetIDs: %d ms", end - start));

        start = new Date().getTime();
        Set<String> obtainedStringSet = getStrings(shortener, testIdsSet);
        end = new Date().getTime();
        Helper.printMessage(String.format("GetStrings: %d ms", end - start));

        if (testStringSet.size() == obtainedStringSet.size()){
            Helper.printMessage("Тест пройден.");
        } else {
            Helper.printMessage("Тест не пройден.");
        }

    }

}
