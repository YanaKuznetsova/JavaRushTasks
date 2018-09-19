package com.javarush.task.task34.task3413;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class    SoftCache {
    private Map<Long, SoftReference<AnyObject>> cacheMap = new ConcurrentHashMap<>();

    public AnyObject get(Long key) {
        SoftReference<AnyObject> softReference = cacheMap.get(key);

        //напишите тут ваш код
        AnyObject result = null;
        if (softReference != null) {
            result =  softReference.get();
        }
        return result;
    }

    public AnyObject put(Long key, AnyObject value) {
        //SoftReference<AnyObject> softReference = cacheMap.put(key, new SoftReference<>(value));

        AnyObject result = null;
        SoftReference<AnyObject> softReference = cacheMap.get(key);
        if (softReference != null) {
            result = softReference.get();
            softReference.clear();
        }
        cacheMap.put(key, new SoftReference<>(value));
        return result;
    }

    public AnyObject remove(Long key) {
        //SoftReference<AnyObject> softReference = cacheMap.remove(key);

        //напишите тут ваш код
        AnyObject result = null;
        SoftReference<AnyObject> softReference = cacheMap.get(key);
        if (softReference != null) {
            result = cacheMap.get(key).get();
            cacheMap.get(key).clear();
            cacheMap.remove(key);
        }
        return result;
    }
}