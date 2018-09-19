package com.javarush.task.task33.task3310.strategy;

public class FileStorageStrategy implements StorageStrategy {

    static final long DEFAULT_BUCKET_SIZE_LIMIT = 10000;
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    FileBucket[] table = new FileBucket[DEFAULT_INITIAL_CAPACITY];
    private long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT;
    long maxBucketSize = 0;
    int size;

    public FileStorageStrategy(){
        for (int i = 0; i < table.length; i++) {
            table[i] = new FileBucket();
        }
    }

    public int hash(Long k) {return (k == null) ?  0 : (k.hashCode()) ^ (k.hashCode() >>> 16);
    }

    public int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    public Entry getEntry(Long key) {
        Entry result = null;
        int hash = (key == null) ? 0 : hash(key);
        for (Entry entry = table[indexFor(hash, table.length)].getEntry(); entry != null; entry = entry.next) {
            if (entry.hash == hash &&
                    (entry.key.equals(key) || (key != null && key.equals(entry.key)))) {
                result = entry;
                break;
            }
        }
        return result;
    }

    public void resize(int newCapacity) {
        FileBucket[] newTable = new FileBucket[newCapacity];
        transfer(newTable);
        table = newTable;
        maxBucketSize = (int) Math.min(newCapacity, DEFAULT_INITIAL_CAPACITY);
    }

    public void transfer(FileBucket[] newTable) {
        int length = newTable.length;
        for (FileBucket aTable : table) {
            for (Entry entry = aTable.getEntry(); entry != null; entry = entry.next) {
                int newIndex = indexFor(entry.hash, length);
                Entry previous = newTable[newIndex].getEntry();
                newTable[newIndex].putEntry(new Entry(entry.hash, entry.key, entry.value, previous));
            }
        }
    }

    public void addEntry(int hash, Long key, String value, int bucketIndex) {
        createEntry(hash, key, value, bucketIndex);
        if (size >= bucketSizeLimit) {
            resize(2 * table.length);
        }
    }

    public void createEntry(int hash, Long key, String value, int bucketIndex) {
        Entry entry = table[bucketIndex].getEntry();
        table[bucketIndex].putEntry(new Entry(hash, key, value, entry));
        size++;
    }

    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value) {
        boolean result = false;
        if (value != null) {
            for (FileBucket aTable : table) {
                for (Entry e = aTable.getEntry(); e != null; e = e.next)
                    if (value.equals(e.value))
                        result = true;
                        break;
            }
        }
        return result;
    }

    @Override
    public void put(Long key, String value) {
        if (key == null) {
            boolean foundKey = false;
            for (Entry entry = table[0].getEntry(); entry != null; entry = entry.next){
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
            for (Entry entry = table[index].getEntry(); entry != null; entry = entry.next){
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
            for (FileBucket aTable : table) {
                for (Entry entry = aTable.getEntry(); entry != null; entry = entry.next) {
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
