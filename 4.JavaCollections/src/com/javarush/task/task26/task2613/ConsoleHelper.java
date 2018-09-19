package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

public class ConsoleHelper {

    private static ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + ".common_en");

    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws InterruptOperationException {
        String result = "";
        try {
            result = bis.readLine();
            if (result.toUpperCase().equals("EXIT")) {
                throw new InterruptOperationException();
            }
        } catch (IOException ignored) {}
        return result;
    }

    public static String askCurrencyCode() throws InterruptOperationException {
        writeMessage(res.getString("choose.currency.code"));
        String result = "";
        while (true) {
            result = readString();
            if (result.length() == 3) {
                result = result.toUpperCase();
                break;
            } else {
                writeMessage(res.getString("invalid.data"));
            }
        }
        return result;
    }

    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException {
        String[] result = null;
        while (true) {
            writeMessage(res.getString("choose.denomination.and.count.format"));
            result = readString().split(" ");
            try {
                if (result.length == 2 && Integer.parseInt(result[0]) > 0 && Integer.parseInt(result[1]) > 0) {
                    break;
                } else{
                    writeMessage(res.getString("invalid.data"));
                }
            } catch(NumberFormatException e) {
                writeMessage(res.getString("invalid.data"));
            }
        }
        return result;
    }

    public static Operation askOperation() throws InterruptOperationException {
        Operation result = null;
        while(true) {
            writeMessage(res.getString("choose.operation"));
            try {
                int index = Integer.parseInt(readString());
                result = Operation.getAllowableOperationByOrdinal(index);
                break;
            } catch (IllegalArgumentException e) {
                writeMessage(res.getString("invalid.data"));
            }
        }
        return result;
    }

    public static void printExitMessage() {
        ConsoleHelper.writeMessage(res.getString("the.end"));
    }

}
