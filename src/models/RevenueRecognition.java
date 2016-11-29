package models;

import java.time.LocalDate;

/**
 * Created by pyoung on 2016-11-27.
 */
public class RevenueRecognition extends DomainObject {
    private double amount;
    private LocalDate date;
    private Long contract_id;

    public RevenueRecognition(double amount, LocalDate date, Long contract_id) {
        this.amount = amount;
        this.date = date;
        this.contract_id = contract_id;
    }

    public double getAmount() {
        return amount;
    }



    public LocalDate getDate() {
        return date;
    }

    public Long getContractId() {
        return contract_id;
    }

    boolean isRecognizableBy(LocalDate asOf) {
        return asOf.isAfter(date) || asOf.equals(date);
    }
}
