package com.javarush.task.task33.task3310.strategy;

public class OurHashMapStorageStrategy implements StorageStrategy {

    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private Entry[] table = new Entry[DEFAULT_INITIAL_CAPACITY];
    private int size;
    private int threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
    private float loadFactor = DEFAULT_LOAD_FACTOR;

    public int hash(Long k){
        return (k == null) ?  0 : (k.hashCode()) ^ (k.hashCode() >>> 16);
    }

    public int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    public Entry getEntry(Long key) {
        Entry result = null;
        int hash = (key == null) ? 0 : hash(key);
        for (Entry entry = table[indexFor(hash, table.length)]; entry != null; entry = entry.next) {
            if (entry.hash == hash &&
                    (entry.key.equals(key) || (key != null && key.equals(entry.key)))) {
                result = entry;
                break;
            }
        }
        return result;
    }

    public void resize(int newCapacity) {
        Entry[] newTable = new Entry[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int) Math.min(newCapacity * loadFactor, (1 << 30) + 1);
    }

    public void transfer(Entry[] newTable) {
        int length = newTable.length;
        for (int i = 0; i < table.length; i++) {
            for (Entry entry = table[i]; entry != null; entry = entry.next) {
                int newIndex = indexFor(entry.hash, length);
                Entry previous = newTable[newIndex];
                newTable[newIndex] = new Entry(entry.hash, entry.key, entry.value, previous);
            }
        }
    }

    public void addEntry(int hash, Long key, String value, int bucketIndex) {
        createEntry(hash, key, value, bucketIndex);
        if (size >= threshold) {
            resize(2 * table.length);
        }
    }

    public void createEntry(int hash, Long key, String value, int bucketIndex) {
        Entry entry = table[bucketIndex];
        table[bucketIndex] = new Entry(hash, key, value, entry);
        size++;
    }

    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value) {
        boolean result = false;
        for (int i = 0; i < table.length; i++) {
            for (Entry entry = table[i]; entry != null; entry = entry.next) {
                if (value == null && entry.value == null) {
                    result = true;
                    break;
                } else if (entry.value.equals(value)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public void put(Long key, String value) {
        if (key == null) {
            boolean foundKey = false;
            for (Entry entry = table[0]; entry != null; entry = entry.next){
                entry.value = value;
                foundKey = true;
                break;
            }
            if (!foundKey){
                addEntry(0, null, value, 0);
            }
        } else {
            int hash = hash(key);
            int index = indexFor(hash, table.length);
            boolean foundKey = false;
            for (Entry entry = table[index]; entry != null; entry = entry.next){
                if (entry.hash == hash && entry.key.equals(key)) {
                    entry.value = value;
                    foundKey = true;
                    break;
                }
            } if (!foundKey) {
                addEntry(hash, key, value, index);
            }
        }
    }

    @Override
    public Long getKey(String value) {
        Long result = null;
        if (value == null) {
            result = 0L;
        } else {
            for (int i = 0; i < table.length; i++) {
                for (Entry entry = table[i]; entry != null; entry = entry.next) {
                    if (entry.value.equals(value)) {
                        result = entry.key;
                        break;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public String getValue(Long key) {
        return (getEntry(key) == null) ? null : getEntry(key).value;
    }

}
