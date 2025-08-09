package com.bank.dto;

import com.bank.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankStatement {
   private String txnId;
private Long txnWith;
private Date txnDate;
private TransactionType txnType;
private Double amount;
private Double closingBalance;


}