package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.*;

public class CurrencyManipulator {

    private String currencyCode;
    private Map<Integer, Integer> denominations = new TreeMap<>(Collections.reverseOrder());

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
    }
    public boolean hasMoney() {
        return !denominations.isEmpty();
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void addAmount(int denomination, int count) {
        int result = count;
        if (denominations.containsKey(denomination)) {
            result += denominations.get(denomination);
        }
        denominations.put(denomination, result);
    }

    public int getTotalAmount(){
        int result = 0;
        for (Map.Entry<Integer, Integer> entry : denominations.entrySet()) {
            result += entry.getKey() * entry.getValue();
        }
        return result;
    }

    public boolean isAmountAvailable(int expectedAmount){
        return expectedAmount <= getTotalAmount();
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
        TreeMap<Integer, Integer> banknotesUsed = new TreeMap<>();
        TreeMap<Integer, Integer> banknotesAvailable = new TreeMap<>(Collections.reverseOrder());
        banknotesAvailable.putAll(denominations);
        boolean success = false;

        TreeSet<Integer> nominalSet = new TreeSet<>(banknotesAvailable.keySet());
        nominalSet = (TreeSet<Integer>) nominalSet.descendingSet();
        Iterator<Integer> iterator = nominalSet.iterator();
        int maxNominal;

        while (!success && iterator.hasNext()){
            maxNominal = iterator.next();
            banknotesAvailable.putAll(banknotesUsed);
            banknotesUsed.clear();
            int leftSum = expectedAmount;
            Iterator<Integer> it = nominalSet.iterator();
            int currentNominal = it.next();
            while (currentNominal != maxNominal) {
                currentNominal = it.next();
            }
            boolean notFirst = false;

            while (it.hasNext()){
                if (notFirst) {
                    currentNominal = it.next();
                }
                if (currentNominal <= leftSum) {
                    int numOfBanknotes = Math.min(leftSum / currentNominal, banknotesAvailable.get(currentNominal));
                    if (numOfBanknotes > 0) {
                        banknotesUsed.put(currentNominal, numOfBanknotes);
                        leftSum -= numOfBanknotes * currentNominal;
                        banknotesAvailable.put(currentNominal, banknotesAvailable.remove(currentNominal) - numOfBanknotes);
                    }
                }
                if (leftSum == 0) {
                    success = true;
                    break;
                }
                notFirst = true;
            }
        }
        if (success) {
            for (Map.Entry<Integer, Integer> entry: banknotesUsed.entrySet()) {
                int newAmount = denominations.get(entry.getKey()) - entry.getValue();
                denominations.put(entry.getKey(), newAmount);
            }
        } else {
            throw new NotEnoughMoneyException();
        }
        return banknotesUsed;
    }

}
