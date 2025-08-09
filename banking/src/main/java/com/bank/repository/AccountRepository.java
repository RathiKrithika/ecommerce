package com.bank.repository;

import com.bank.model.AccountOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountOwner,Long> {

    AccountOwner findByAccountNumber(Long accNum);
}
