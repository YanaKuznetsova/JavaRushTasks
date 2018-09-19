package com.javarush.task.task30.task3008;

import com.sun.javafx.binding.StringFormatter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    private static class Handler extends Thread {

        private Socket socket;

        public Handler(Socket socket){
            this.socket = socket;
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            while (true) {
                connection.send(new Message(MessageType.NAME_REQUEST));
                Message answer = connection.receive();
                if (answer.getType().equals(MessageType.USER_NAME)) {
                    String newUser = answer.getData();
                    if (!newUser.isEmpty() && !connectionMap.containsKey(newUser)) {
                        connectionMap.put(newUser, connection);
                        connection.send(new Message(MessageType.NAME_ACCEPTED));
                        return newUser;
                    }
                }
            }
        }

        private void sendListOfUsers(Connection connection, String userName) throws IOException {
            for (Map.Entry<String, Connection> entry: connectionMap.entrySet()) {
                if (!entry.getKey().equals(userName)) {
                    connection.send(new Message(MessageType.USER_ADDED, entry.getKey()));
                }
            }
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while(true) {
                Message message = connection.receive();
                if (message!= null && message.getType() == (MessageType.TEXT)) {
                    sendBroadcastMessage(new Message(MessageType.TEXT,
                            String.format("%s: %s", userName, message.getData())));
                } else {
                   ConsoleHelper.writeMessage("Ошибка.");
                }
            }
        }


        @Override
        public void run(){
            if (socket != null && socket.getRemoteSocketAddress() != null) {
                ConsoleHelper.writeMessage(
                        String.format("Установлено новое содение с адресом %s", socket.getRemoteSocketAddress()));
            }
            String newUser = null;
            try (Connection connection = new Connection(socket)) {
                newUser = serverHandshake(connection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, newUser));
                sendListOfUsers(connection, newUser);
                serverMainLoop(connection, newUser);
            } catch (IOException | ClassNotFoundException e)  {
                ConsoleHelper.writeMessage("Произошла ошибка при обмене данными с удаленным адресом.");
            }
            if (newUser != null) {
                connectionMap.remove(newUser);
                sendBroadcastMessage(new Message(MessageType.USER_REMOVED, newUser));
            }
            ConsoleHelper.writeMessage("Cоединение с удаленным адресом закрыто.");
        }

    }

    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    public static void sendBroadcastMessage(Message message) {
        for (Map.Entry<String, Connection> entry: connectionMap.entrySet()) {
            try {
                entry.getValue().send(message);
            } catch (IOException e) {
                ConsoleHelper.writeMessage("Произошла ошибка соединения, сообщение не было отправлено.");
            }
        }
    }


    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(ConsoleHelper.readInt())) {
            System.out.println("Сервер запущен...");
            while (true) {
                new Handler(serverSocket.accept()).start();
            }
        } catch (Exception e) {
            ConsoleHelper.writeMessage("Произошла ошибка соединения, сокет закрыт.");
        }

    }
}
