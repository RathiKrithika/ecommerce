package com.bank.repository;

import com.bank.model.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionHistory, Long> {
    List<TransactionHistory> findByOccurredForOrderByTxnOccurredAtDesc(Long accountNum);

}
