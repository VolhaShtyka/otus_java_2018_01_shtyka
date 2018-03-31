package ru.otus.shtyka.nominal_slot_composite;

import ru.otus.shtyka.sum_strategy.CURRENCY;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NominalSlotsGroup implements SlotSum {

    private List<NominalSlot> slots = new ArrayList<>();

    public void addSlot(NominalSlot slot) {
        slots.add(slot);
    }

    @Override
    public BigDecimal getCashBalanceByCurrency(CURRENCY currency) {
        return slots.stream()
                .map(slot -> slot.getCashBalanceByCurrency(currency))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getCashBalanceInRub() {
        return slots.stream()
                .map(NominalSlot::getCashBalanceInRub)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void getSum(BigDecimal wantedSum, CURRENCY currency) {
        List<NominalSlot> cashedSlots = new ArrayList<>(slots);
        this.slots = slots.stream().
                filter(n -> n.getDefaultCurrency().equals(currency) && n.getBillsCount() != 0).
                sorted((n1, n2) -> Integer.compare(n2.getNominal().getValue(), n1.getNominal().getValue())).
                collect(Collectors.toList());
        System.out.println("The Sum to issue is calculated");
        for (NominalSlot slot : slots) {
            int receivedBillsCount = 0;
            BigDecimal billNominal = BigDecimal.valueOf(slot.getNominal().getValue());
            while (wantedSum.compareTo(BigDecimal.ZERO) != 0) {
                if (slot.getBillsCount() == 0 || wantedSum.compareTo(billNominal) < 0) {
                    break;
                }
                slot.setDownBillsCount(1);
                receivedBillsCount++;
                wantedSum = wantedSum.subtract(billNominal);
            }
            if (receivedBillsCount != 0) {
                System.out.println("Nominal value: " + billNominal + ", count: " + receivedBillsCount);
            }
        }
        if (wantedSum.compareTo(BigDecimal.ZERO) != 0) {
            updateSlots(cashedSlots);
            System.out.println("Sorry, there are no denominations in " + currency + " in the required amount in the ATM. \nTry to enter a different amount");
            return;
        }
    }

    private void updateSlots(final List<NominalSlot> slots) {
        this.slots = slots;
    }
}
