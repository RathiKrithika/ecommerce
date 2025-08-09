package com.shop.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table
@Entity(name="orders")
public class Order {
    public Order(Long userId, Date createdOn) {
        this.userId = userId;
        this.createdOn = createdOn;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "created_on")
    private Date createdOn;
}
