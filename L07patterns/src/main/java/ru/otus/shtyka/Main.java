package ru.otus.shtyka;

import ru.otus.shtyka.sum_strategy.CURRENCY;
import ru.otus.shtyka.department.CashPoint;
import ru.otus.shtyka.department.Department;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Department department = Department.getInstance();
        CashPoint atmKurskaya = new CashPoint("Kurskaya", 300);
        CashPoint atmMira = new CashPoint("Prospekt Mira", 200);

        System.out.println("Balance all cash points in RUB: " + department.getSumAllCashPoints() + "\n");

        department.register(atmKurskaya);
        department.register(atmMira);
        System.out.println("Balance all cash points after register");
        System.out.println("Balance all cash points in RUB: " + department.getSumAllCashPoints() + "\n");

        atmKurskaya.getSumOfMinimumNumberOfBanknotes(BigDecimal.valueOf(1900), CURRENCY.RUB);
        atmMira.getSumOfMinimumNumberOfBanknotes(BigDecimal.valueOf(164), CURRENCY.USD);
        atmMira.getSumOfMinimumNumberOfBanknotes(BigDecimal.valueOf(22), CURRENCY.EURO);


        System.out.println("Balance all cash points in RUB: " + department.getSumAllCashPoints());
        department.restoreAllCashPointsFromMemento();
        System.out.println("Balance all cash points in RUB: " + department.getSumAllCashPoints());
    }
}