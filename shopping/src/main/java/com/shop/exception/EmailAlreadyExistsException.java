package com.shop.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class EmailAlreadyExistsException extends RuntimeException {
    private String email;

}
