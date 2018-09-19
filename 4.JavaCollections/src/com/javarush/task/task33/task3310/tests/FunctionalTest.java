package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.*;
import org.junit.Assert;
import org.junit.Test;

public final class FunctionalTest {

    public void testStorage(Shortener shortener) {
        String testString1 = "Test string for Shortener strategy.";
        String testString2 = "Other string.";
        String testString3 = "Test string for Shortener strategy.";

        Long id1 = shortener.getId(testString1);
        Long id2 = shortener.getId(testString2);
        Long id3 = shortener.getId(testString3);

        Assert.assertNotEquals(id1, id2);
        Assert.assertNotEquals(id2, id3);
        Assert.assertEquals(id1, id3);

        String obtainedString1 = shortener.getString(id1);
        String obtainedString2 = shortener.getString(id2);
        String obtainedString3 = shortener.getString(id3);

        Assert.assertEquals(obtainedString1, testString1);
        Assert.assertEquals(obtainedString2, testString2);
        Assert.assertEquals(obtainedString3, testString3);
    }

    @Test
    public void testHashMapStorageStrategy() {
        StorageStrategy storageStrategy = new HashMapStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }

    @Test
    public void testOurHashMapStorageStrategy(){
        StorageStrategy storageStrategy = new OurHashMapStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }

    @Test
    public void testFileStorageStrategy(){
        StorageStrategy storageStrategy = new FileStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }

    @Test
    public void testHashBiMapStorageStrategy(){
        StorageStrategy storageStrategy = new HashBiMapStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }

    @Test
    public void testDualHashBidiMapStorageStrategy(){
        StorageStrategy storageStrategy = new DualHashBidiMapStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }

    @Test
    public void testOurHashBiMapStorageStrategy(){
        StorageStrategy storageStrategy = new OurHashBiMapStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }

}
