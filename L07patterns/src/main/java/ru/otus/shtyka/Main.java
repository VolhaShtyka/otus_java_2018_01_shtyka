package ru.otus.shtyka;

import ru.otus.shtyka.department.ATMRub;
import ru.otus.shtyka.department.ATMUsd;
import ru.otus.shtyka.department.CURRENCY;
import ru.otus.shtyka.department.Department;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        Department department = Department.getInstance();
        ATMRub atmKurskaya = new ATMRub("Kurskaya", BigDecimal.valueOf(300));
        ATMRub atmMira = new ATMRub("Prospekt Mira", BigDecimal.valueOf(200));
        ATMUsd atmUsdMira = new ATMUsd("Prospekt Mira", BigDecimal.valueOf(1200));

        System.out.println("Balance all cash points in USD: " + department.getSumAllCashPointsByCurrency(CURRENCY.USD));
        System.out.println("Balance all cash points in EURO: " + department.getSumAllCashPointsByCurrency(CURRENCY.EURO));
        System.out.println("Balance all cash points in RUB: " + department.getSumAllCashPointsByCurrency(CURRENCY.RUB) + "\n");

        department.register(atmKurskaya);
        department.register(atmMira);
        department.register(atmUsdMira);

        System.out.println("Balance all cash points after register");
        System.out.println("Balance all cash points in USD: " + department.getSumAllCashPointsByCurrency(CURRENCY.USD));
        System.out.println("Balance all cash points in EURO: " + department.getSumAllCashPointsByCurrency(CURRENCY.EURO));
        System.out.println("Balance all cash points in RUB: " + department.getSumAllCashPointsByCurrency(CURRENCY.RUB) + "\n");

        atmKurskaya.increaseCashBalance(BigDecimal.valueOf(1900));
        atmKurskaya.decreaseCashBalance(BigDecimal.valueOf(19));
        atmMira.decreaseCashBalance(BigDecimal.valueOf(30));
        atmUsdMira.decreaseCashBalance(BigDecimal.valueOf(155));

        System.out.println("Balance all cash points in USD: " + department.getSumAllCashPointsByCurrency(CURRENCY.USD) + "\n");
        department.restoreAllCashPointsFromMemento();
        System.out.println("Balance all cash points in USD: " + department.getSumAllCashPointsByCurrency(CURRENCY.USD));
        System.out.println("Balance all cash points in EURO: " + department.getSumAllCashPointsByCurrency(CURRENCY.EURO));
        System.out.println("Balance all cash points in RUB: " + department.getSumAllCashPointsByCurrency(CURRENCY.RUB));
    }
}