package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;

import java.util.Collection;
import java.util.Map;
import java.util.ResourceBundle;

class InfoCommand implements Command {

    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "info_en");

    @Override
    public void execute() {
        Collection<CurrencyManipulator> allCurrencyManipulators =
                CurrencyManipulatorFactory.getAllCurrencyManipulators();
        ConsoleHelper.writeMessage(res.getString("before"));
        boolean noMoneyInCashMachine = true;
        for (CurrencyManipulator currencyManipulator : allCurrencyManipulators) {
            if (currencyManipulator.hasMoney() && currencyManipulator.getTotalAmount() > 0) {
                ConsoleHelper.writeMessage(String.format("%s - %d", currencyManipulator.getCurrencyCode(),
                        currencyManipulator.getTotalAmount()));
                noMoneyInCashMachine = false;
            }
        }
        if (noMoneyInCashMachine) {
            ConsoleHelper.writeMessage(res.getString("no.money"));
        }
    }

}
