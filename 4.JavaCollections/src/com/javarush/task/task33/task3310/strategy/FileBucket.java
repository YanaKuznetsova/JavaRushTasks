package com.javarush.task.task33.task3310.strategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBucket {

    private Path path;

    public FileBucket() {
        try {
            path = Files.createTempFile("temp","");
            Files.deleteIfExists(path);
            Files.createFile(path);
            path.toFile().deleteOnExit();
        } catch (IOException e) {
        }
    }

    public long getFileSize() {
        long result = 0;
        try {
            result = Files.size(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void putEntry(Entry entry){
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(path));
            while (entry != null) {
                objectOutputStream.writeObject(entry);
                entry = entry.next;
            }
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Entry getEntry() {
        Entry entry = null;
        if (getFileSize() > 0) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(path));
                entry = (Entry) objectInputStream.readObject();
                objectInputStream.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return entry;
    }

    public void remove() {
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
