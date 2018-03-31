package ru.otus.shtyka.department;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Department {
    private static Department department;
    private List<CashPoint> cashPoints = new ArrayList<>();

    private Department() {
    }

    public static Department getInstance() {
        if (department == null) {
            department = new Department();
        }
        return department;
    }

    public void register(CashPoint atm) {
        cashPoints.add(atm);
    }

    public void restoreAllCashPointsFromMemento() {
        cashPoints.forEach(CashPoint::restoreFromMemento);
    }

    public BigDecimal getSumAllCashPoints() {
        return cashPoints.stream().map(CashPoint::getCashBalance).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
