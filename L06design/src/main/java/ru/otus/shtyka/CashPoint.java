package ru.otus.shtyka;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CashPoint {

    private static CashPoint cashpoint;
    private List<NominalSlot> slots = NominalSlot.getDefaultCountOfSlots();

    private CashPoint() {
    }

    public static CashPoint getInstance() {
        if (cashpoint == null) {
            cashpoint = new CashPoint();
        }
        return cashpoint;
    }

    public BigDecimal getCashBalance() {
        return BigDecimal.valueOf(slots.stream().mapToInt(n -> n.getNominal().getValue() * n.getBillsCount()).sum());
    }

    void getSumOfMinimumNumberOfBanknotes(final BigDecimal wantedSum) {
        if (wantedSum.compareTo(getCashBalance()) > 0 ||
                wantedSum.intValue() < 0 ||
                wantedSum.setScale(0, RoundingMode.HALF_DOWN).compareTo(wantedSum) != 0) {
            throw new IllegalArgumentException("It`s impossible to provide the requested amount: " + wantedSum);
        }
        System.out.println("Opening balance: " + getCashBalance());
        System.out.println("Requested amount: " + wantedSum + "\n");
        getSum(wantedSum);
        System.out.println("\nFinal balance: " + getCashBalance());
    }

    void depositToAccount(final NOMINAL nominal, final int count) {
        slots.stream().
                filter(n -> n.getNominal().equals(nominal)).
                findFirst().orElseThrow(() -> new IllegalArgumentException("Incorrect amount")).setUpBillsCount(count);
        System.out.println("\nNew balance: " + getCashBalance());
    }

    private void getSum(BigDecimal wantedSum) {
        slots = slots.stream().
                filter(n -> n.getBillsCount() != 0).
                sorted((n1, n2) -> Integer.compare(n2.getNominal().getValue(), n1.getNominal().getValue())).
                collect(Collectors.toList());
        List<NominalSlot> cashedSlots = new ArrayList<>(slots);
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
            throw new IllegalStateException("Sorry, there are no denominations in the required amount in the ATM. \nTry to enter a different amount");
        }
    }

    private void updateSlots(final List<NominalSlot> slots) {
        this.slots = slots;
    }
}
