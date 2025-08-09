package com.shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private Long productId;
    private int quantity;
    private Double totalPrice;
    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private OrderStatus status;
    @Column(name="placed_on")
    private Date placedOn;
    public OrderItem(Long orderId, Long productId, int quantity, Double totalPrice,OrderStatus status, Date placedOn) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
        this.placedOn = placedOn;
        this.totalPrice = totalPrice;
    }

}
