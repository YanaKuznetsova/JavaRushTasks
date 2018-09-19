package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public final class SpeedTest {


    private static Set<Long> getIds(Shortener shortener, Set<String> strings) {
        Set<Long> idsSet = new HashSet<>();
        for (String string: strings) {
            idsSet.add(shortener.getId(string));
        }
        return idsSet;
    }

    private static Set<String> getStrings(Shortener shortener, Set<Long> keys) {
        Set<String> stringsSet = new HashSet<>();
        for (Long key: keys) {
            stringsSet.add(shortener.getString(key));
        }
        return stringsSet;
    }

    public long getTimeForGettingIds(Shortener shortener, Set<String> strings, Set<Long> ids){
        Long start = new Date().getTime();
        ids = getIds(shortener, strings);
        Long end = new Date().getTime();
        return end - start;
    }

    public long getTimeForGettingStrings(Shortener shortener, Set<Long> ids, Set<String> strings) {
        Long start = new Date().getTime();
        strings = getStrings(shortener, ids);
        Long end = new Date().getTime();
        return end - start;
    }

    @Test
    public void testHashMapStorage(){
        Shortener hashMapShortener = new Shortener(new HashMapStorageStrategy());
        Shortener hashBiMapShortener = new Shortener(new HashBiMapStorageStrategy());

        Set<String> origStrings = new HashSet<>();
        for (int i = 0; i < 10000; i ++) {
            origStrings.add(Helper.generateRandomString());
        }

        Set<Long> idsHashMap = new HashSet<>();
        long timeIdsHashMap = getTimeForGettingIds(hashMapShortener, origStrings, idsHashMap);
        Set<Long> idsHashBiMap = new HashSet<>();
        long timeIdsHashBiMap = getTimeForGettingIds(hashBiMapShortener, origStrings, idsHashBiMap);

        Assert.assertThat(timeIdsHashMap, greaterThan(timeIdsHashBiMap));

        Set<String> stringsHashMap = new HashSet<>();
        long timeStringsHashMap = getTimeForGettingStrings(hashMapShortener, idsHashMap, stringsHashMap);
        Set<String> stringsHashBiMap = new HashSet<>();
        long timeStringsHashBiMap = getTimeForGettingStrings(hashBiMapShortener, idsHashBiMap, stringsHashBiMap);

        Assert.assertEquals(timeStringsHashBiMap, timeStringsHashMap, 30);


    }

}
