package com.shop.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {

    @Override
    public String convertToDatabaseColumn(OrderStatus orderStatus) {
        return orderStatus == null? null: orderStatus.name().toLowerCase();
    }

    @Override
    public OrderStatus convertToEntityAttribute(String s) {
        return s==null?null:OrderStatus.valueOf(s.toLowerCase());
    }
}
