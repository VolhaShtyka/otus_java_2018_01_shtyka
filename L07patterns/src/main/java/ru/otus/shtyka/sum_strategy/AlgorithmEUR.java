package ru.otus.shtyka.sum_strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AlgorithmEUR implements Algorithm {
    @Override
    public BigDecimal calculateWithCurrency(BigDecimal cashBalance) {
        return cashBalance.divide(CURRENCY.EURO.rate, RoundingMode.HALF_DOWN);
    }

    @Override
    public BigDecimal convertToRub(BigDecimal cashBalance) {
        return cashBalance.multiply(CURRENCY.EURO.rate);
    }
}