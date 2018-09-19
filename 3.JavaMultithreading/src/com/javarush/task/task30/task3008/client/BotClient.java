package com.javarush.task.task30.task3008.client;

import com.javarush.task.task30.task3008.ConsoleHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BotClient extends Client {

    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    @Override
    protected String getUserName() {
        return "date_bot_" + (int) (Math.random() * 100);
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    public static void main(String[] args) {
        BotClient botClient = new BotClient();
        botClient.run();
    }

    public class BotSocketThread extends SocketThread {

        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Привет чатику. " +
                "Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
            if (message != null) {
                ConsoleHelper.writeMessage(message);
                if (message.contains(": ")) {
                    String[] words = message.split(": ");
                    SimpleDateFormat dateFormat = null;
                    if (words.length == 2 && words[1] != null) {
                        String userName = words[0].trim();
                        String text = words[1].trim();
                        if (text.equals("дата")) {
                            dateFormat = new SimpleDateFormat("d.MM.YYYY");
                        } else if (text.equals("день")) {
                            dateFormat = new SimpleDateFormat("d");
                        } else if (text.equals("месяц")) {
                            dateFormat = new SimpleDateFormat("MMMM");
                        } else if (text.equals("год")) {
                            dateFormat = new SimpleDateFormat("YYYY");
                        } else if (text.equals("время")) {
                            dateFormat = new SimpleDateFormat("H:mm:ss");
                        } else if (text.equals("час")) {
                            dateFormat = new SimpleDateFormat("H");
                        } else if (text.equals("минуты")) {
                            dateFormat = new SimpleDateFormat("m");
                        } else if (text.equals("секунды")) {
                            dateFormat = new SimpleDateFormat("s");
                        }

                        if (dateFormat != null) {
                            sendTextMessage(String.format("Информация для %s: %s",
                                    userName, dateFormat.format(Calendar.getInstance().getTime())));
                        }
                    }
                }
            }
        }
    }

}
