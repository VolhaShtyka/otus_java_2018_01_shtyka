package ru.otus.shtyka.department;

import java.math.BigDecimal;

public enum CURRENCY {

    RUB(BigDecimal.ONE), USD(BigDecimal.valueOf(64)), EURO(BigDecimal.valueOf(75));

    public final BigDecimal rate;

    CURRENCY(BigDecimal rateToRub) {
        this.rate = rateToRub;
    }
}
