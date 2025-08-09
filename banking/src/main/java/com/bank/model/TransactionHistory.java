package com.bank.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="transaction_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id", unique = true)
    private String transactionId;

    @Column(name = "occurred_for")
    private Long occurredFor;

    private Long participant;

    @Column(name = "occurred_on")
    private Date txnOccurredAt;

    @Column(name = "closing_balace")
    private Double closingBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "txn_type")
    private TransactionType txnType;

    private Double amount;

    //for deposit, credit and debit
public TransactionHistory(Long occurredFor, Long participant, Double closingBalance, TransactionType txnType,
Double amount) {

this.occurredFor = occurredFor;
this.participant = participant;
this.closingBalance = closingBalance;
this.txnType = txnType;
this.amount = amount;
}
    @PrePersist
    public void prePersist() {
        this.transactionId = transactionId = " " + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.txnOccurredAt = new Date();
    }
}
