package ru.otus.shtyka.nominal_slot_composite;

import ru.otus.shtyka.sum_strategy.CURRENCY;

public class NominalSlotUSD extends NominalSlot {
    private CURRENCY defaultCurrency = CURRENCY.USD;

    public NominalSlotUSD(NOMINAL nominal, int billsCount) {
        super(nominal, billsCount);
    }

    @Override
    public CURRENCY getDefaultCurrency() {
        return defaultCurrency;
    }

}
