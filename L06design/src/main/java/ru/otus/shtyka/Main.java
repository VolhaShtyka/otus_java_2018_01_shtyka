package ru.otus.shtyka;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        CashPoint atm = CashPoint.getInstance();
        atm.getSumOfMinimumNumberOfBanknotes(BigDecimal.valueOf(165));

        atm.depositToAccount(NOMINAL.FIFE, 8);
        System.out.println("ATM contains " + atm.getCashBalance() + " RUB");

        atm.getSumOfMinimumNumberOfBanknotes(BigDecimal.valueOf(10));
        System.out.println("ATM contains " + atm.getCashBalance() + " RUB");
    }
}
