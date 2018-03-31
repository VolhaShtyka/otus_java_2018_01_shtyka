package ru.otus.shtyka.nominal_slot_composite;

import ru.otus.shtyka.sum_strategy.CURRENCY;
import ru.otus.shtyka.sum_strategy.Converter;

import java.math.BigDecimal;

abstract class NominalSlot implements SlotSum {

    private NOMINAL nominal;
    private int billsCount;


    public NOMINAL getNominal() {
        return nominal;
    }

    public int getBillsCount() {
        return billsCount;
    }

    public void setDownBillsCount(int downCount) {
        this.billsCount = this.billsCount - downCount;
    }

    NominalSlot(NOMINAL nominal, int billsCount) {
        this.nominal = nominal;
        this.billsCount = billsCount;
    }

    public abstract CURRENCY getDefaultCurrency();

    public BigDecimal getCashBalanceInRub() {
        return getCashBalanceByCurrency(CURRENCY.RUB);
    }

    public BigDecimal getCashBalanceByCurrency(CURRENCY currency) {
        BigDecimal balanceCash = BigDecimal.valueOf(getNominal().getValue() * getBillsCount());
        if (currency.equals(getDefaultCurrency())) {
            return balanceCash;
        }
        BigDecimal balanceCashRub = new Converter(getDefaultCurrency()).convertToRub(balanceCash);
        return new Converter(currency).calculateWithCurrency(balanceCashRub);
    }
}
