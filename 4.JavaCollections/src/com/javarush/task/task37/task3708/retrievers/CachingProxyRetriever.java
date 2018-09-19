package com.javarush.task.task37.task3708.retrievers;

import com.javarush.task.task37.task3708.cache.LRUCache;
import com.javarush.task.task37.task3708.storage.Storage;

public class CachingProxyRetriever implements Retriever {

    private OriginalRetriever originalRetriever;
    private LRUCache lruCache;

    public CachingProxyRetriever(Storage storage) {
        this.originalRetriever = new OriginalRetriever(storage);
        this.lruCache = new LRUCache<>(100);
    }

    @Override
    public Object retrieve(long id) {
        Object result = lruCache.find(id);
        if (result == null) {
            result = originalRetriever.retrieve(id);
            lruCache.set(id, result);
        }
        return result;
    }


}
