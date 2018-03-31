package ru.otus.shtyka.sum_strategy;

import java.math.BigDecimal;

public class AlgorithmRUB implements Algorithm {
    @Override
    public BigDecimal calculateWithCurrency(BigDecimal cashBalance) {
        return cashBalance;
    }

    @Override
    public BigDecimal convertToRub(BigDecimal cashBalance) {
        return cashBalance;
    }
}