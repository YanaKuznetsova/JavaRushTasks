package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;
import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.Map;
import java.util.ResourceBundle;

class WithdrawCommand implements Command{

    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "withdraw_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        String currencyCode = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
        int amount;
//        Map<Integer, Integer> banknotes = null;
        while (true){
            try {
                ConsoleHelper.writeMessage(res.getString("specify.amount"));
                amount = Integer.parseInt(ConsoleHelper.readString());
            } catch (NumberFormatException e){
                ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                continue;
            }
            if (amount <= 0){
                ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                continue;
            }
            if (!currencyManipulator.isAmountAvailable(amount)){
                ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                continue;
            }
            try {
//                banknotes = currencyManipulator.withdrawAmount(amount);
                currencyManipulator.withdrawAmount(amount);
            } catch (NotEnoughMoneyException e) {
                ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                continue;
            }
//            for (Map.Entry<Integer, Integer> pair : banknotes.entrySet()) {
//                ConsoleHelper.writeMessage(String.format(res.getString("success.format"),
//                        pair.getKey() * pair.getValue(), currencyCode));
//            }
            ConsoleHelper.writeMessage(String.format(res.getString("success.format"), amount, currencyCode));
            break;
        }
    }

}
