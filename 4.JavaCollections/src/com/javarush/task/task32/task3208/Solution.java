package com.javarush.task.task32.task3208;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/* 
RMI-2
*/
public class Solution {
    public static Registry registry;
    private final static String CAT_UNIC_BINDING_NAME = "server.cat";
    private final static String DOG_UNIC_BINDING_NAME = "server.dog";

    //pretend we start rmi client as CLIENT_THREAD thread
    public static Thread CLIENT_THREAD = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                for (String bindingName : registry.list()) {
                    Animal service = (Animal) registry.lookup(bindingName);
                    service.printName();
                    service.say();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        }
    });

    //pretend we start rmi server as SERVER_THREAD thread
    public static Thread SERVER_THREAD = new Thread(new Runnable() {
        @Override
        public void run() {
            //создание реестра расшареных объетов
            try {
                registry = LocateRegistry.createRegistry(2099);
                //создание объекта для удаленного доступа
                final Animal catService = new Cat("Murzik");
                final Animal dogService = new Dog("Bobik");

                //создание "заглушки" – приемника удаленных вызовов
                Remote catStub = UnicastRemoteObject.exportObject(catService, 0);
                Remote dogStub = UnicastRemoteObject.exportObject(dogService, 0);
                //регистрация "заглушки" в реестре
                registry.bind(CAT_UNIC_BINDING_NAME, catStub);
                registry.bind(DOG_UNIC_BINDING_NAME, dogStub);

            } catch (RemoteException | AlreadyBoundException e) {
                e.printStackTrace(System.err);
            }
        }
    });

    public static void main(String[] args) throws InterruptedException {
        //start rmi server thread
        SERVER_THREAD.start();
        Thread.sleep(1000);
        //start client
        CLIENT_THREAD.start();
    }
}