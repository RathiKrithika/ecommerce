package com.shop.client;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.shop.exception.EmailAlreadyExistsException;
import com.shop.exception.PasswordMismatchException;
import com.shop.exception.UserNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class CustomErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        /*try (InputStream body = response.body().asInputStream()) {

            ErrorResponse errorResponse = objectMapper.readValue(body, ErrorResponse.class);
            int code = errorResponse.getCode();

            switch (code) {
                case ErrorCode.MINIMUM_BALANCE:
                    return new MinimumBalanceException(errorResponse.getMessage());

                case ErrorCode.BANK_ACCOUNT_NOT_FOUND:
                    return new AccountNotFoundException(errorResponse.getMessage());

                case ErrorCode.INSUFFICIENT_BALANCE:
                    return new InsufficientBalanceException(errorResponse.getMessage());

                case ErrorCode.EMAIL_ALREADY_EXISTS:
                    return new EmailAlreadyExistsException(errorResponse.getMessage());

                case ErrorCode.PASSWORD_MISMATCH_EXCEPTION:
                    return new PasswordMismatchException(errorResponse.getMessage());

                case ErrorCode.USER_NOT_FOUND:
                    return new UserNotFoundException(errorResponse.getMessage());

                default:
                    return new RuntimeException("Unhandled error code: " + code + ", message: " + errorResponse.getMessage());
            }

        } catch (IOException e) {
            return new RuntimeException("Error reading response body", e);
        }*/
        return null;
    }
}