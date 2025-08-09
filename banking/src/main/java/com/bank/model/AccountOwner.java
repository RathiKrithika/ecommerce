package com.bank.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="account_holder")
public class AccountOwner {
    public AccountOwner(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name="account_num")
    private Long accountNumber;
    @Column(name="created_at")
    private Date createdAt ;
    @Column(name="phone_number", unique = true)
    private String phoneNumber;


    @PrePersist
    public void prePersist() {
            createdAt = new Date();
            String timeBased = String.valueOf(System.currentTimeMillis());
        accountNumber = Long.parseLong(timeBased.substring(timeBased.length() - 10));

    }
}
