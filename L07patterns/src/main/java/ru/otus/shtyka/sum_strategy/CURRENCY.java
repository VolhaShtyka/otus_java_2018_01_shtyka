package ru.otus.shtyka.sum_strategy;

import java.math.BigDecimal;

public enum CURRENCY {

    RUB(BigDecimal.ONE, new AlgorithmRUB()), USD(BigDecimal.valueOf(64), new AlgorithmUSD()), EURO(BigDecimal.valueOf(75), new AlgorithmEUR());

    public final BigDecimal rate;

    public final Algorithm algorithm;

    CURRENCY(BigDecimal rateToRub, Algorithm algorithm) {
        this.rate = rateToRub;
        this.algorithm = algorithm;
    }
}
