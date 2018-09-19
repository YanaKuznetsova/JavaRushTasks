package com.javarush.task.task30.task3001;

import java.math.BigInteger;

/*
Конвертер систем счислений
*/
public class Solution {
    public static void main(String[] args) {
        Number number = new Number(NumerationSystemType._10, "6");
        Number result = convertNumberToOtherNumerationSystem(number, NumerationSystemType._2);
        System.out.println(result);    //expected 110

        number = new Number(NumerationSystemType._16, "6df");
        result = convertNumberToOtherNumerationSystem(number, NumerationSystemType._8);
        System.out.println(result);    //expected 3337

        number = new Number(NumerationSystemType._8, "2017");
        result = convertNumberToOtherNumerationSystem(number, NumerationSystemType._12);
        System.out.println(result);    //expected 727

        number = new Number(NumerationSystemType._7, "201115");
        result = convertNumberToOtherNumerationSystem(number, NumerationSystemType._9);
        System.out.println(result);    //expected 51587
    }

    public static Number convertNumberToOtherNumerationSystem(Number number, NumerationSystem expectedNumerationSystem) {
        BigInteger bigInt = new BigInteger(number.getDigit(), number.getNumerationSystem().getNumerationSystemIntValue());
        return new Number(expectedNumerationSystem, bigInt.toString(expectedNumerationSystem.getNumerationSystemIntValue()));
    }


    public static Number MineСonvertNumberToOtherNumerationSystem(Number number, NumerationSystem expectedNumerationSystem) {
        int type = number.getNumerationSystem().getNumerationSystemIntValue();
        int expectedType = expectedNumerationSystem.getNumerationSystemIntValue();
        String digits = number.getDigit().toLowerCase();
        if (type <= 10) {
            for (int i = 0; i < digits.length(); i++) {
                if (digits.charAt(i) - 48 > type - 1) {
                    throw new NumberFormatException();
                }
            }
        } else {
            if ((type == 12) && !digits.matches("[0-9ab]*"))
                throw new NumberFormatException();
            if ((type == 16) && !digits.matches("[0-9abcdef]*"))
                throw new NumberFormatException();
        }

        String decimalResult;
        if (type == 10) {
            decimalResult = digits;
        } else {
            decimalResult = parseToDecimal(digits, type);
        }

        String result = "";
        switch (expectedType) {
            case 2:
                result = Integer.toBinaryString(Integer.parseInt(decimalResult));
                break;
            case 8:
                result = Integer.toOctalString(Integer.parseInt(decimalResult));
                break;
            case 10:
                result = decimalResult;
                break;
            case 16:
                result = Integer.toHexString(Integer.parseInt(decimalResult));
                break;
            default:
                result = parseFromDecimal(decimalResult, expectedType);
        }

        return new Number(expectedNumerationSystem, result);
    }

    private static String parseToDecimal(String digits, int type) {
        int result = 0;
        int pow = 1;
        for (int i = digits.length() - 2; i >= 0;  i--) {
            if (digits.charAt(i) < 58) {
                result += (digits.charAt(i) - 48) * type * pow;
                pow *= type;
            } else {
                result += (digits.charAt(i) - 87) * type * pow;
                pow *= type;
            }
        }
        if (digits.charAt(digits.length() - 1) < 58) {
            result += digits.charAt(digits.length() - 1) - 48;
        } else {
            result += digits.charAt(digits.length() - 1) - 87;
        }
        return String.valueOf(result);
    }

    private static String parseFromDecimal(String decimalString, int expectedType) {
        StringBuilder stringBuilder = new StringBuilder();
        int ratio = Integer.parseInt(decimalString);
        while (ratio > expectedType) {
            int residual = ratio % expectedType;
            if (residual > 9) {
                stringBuilder.append((char)(residual + 87));
            } else {
                stringBuilder.append(residual);
            }
            ratio /= expectedType;
        }
        stringBuilder.append(ratio);
        return stringBuilder.reverse().toString();
    }

}
