package com.javarush.task.task37.task3707;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        AmigoSet<Integer> amigoSet = new AmigoSet<>();
        amigoSet.add(5);
        amigoSet.add(7);
        amigoSet.add(18);

        String filename = "c:\\temp.txt";
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {

            fos = new FileOutputStream(filename);
            out = new ObjectOutputStream(fos);
            out.writeObject(amigoSet);
            out.close();

            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
            AmigoSet<Integer> newAmigoSet = (AmigoSet<Integer>) in.readObject();
            assert newAmigoSet.equals(amigoSet);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
