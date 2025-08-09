package com.shop.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

   /* @ExceptionHandler(value = EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity handleEmailAlreadyExistsException(final EmailAlreadyExistsException ex) {
        return  ResponseEntity. status(HttpStatus.UNAUTHORIZED).
                body(new ErrorResponse(ErrorCode.EMAIL_ALREADY_EXISTS, ex.getMessage()));
    }
*/
    @ExceptionHandler(value = PasswordMismatchException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity handlePasswordMismatchException(final PasswordMismatchException ex) {
        return  ResponseEntity. status(HttpStatus.UNAUTHORIZED).
                body(new ErrorResponse(ErrorCode.PASSWORD_MISMATCH_EXCEPTION, ex.getMessage()));
    }
    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity handleUserNotFoundException(final UserNotFoundException ex) {
        return  ResponseEntity. status(HttpStatus.NOT_FOUND).
                body(new ErrorResponse(ErrorCode.USER_NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = ex.getRootCause().getMessage();

        if (message.contains("unique_email_only")) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(ErrorCode.EMAIL_ALREADY_EXISTS,"Email already exists"));
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ErrorCode.DB_CONSTRAINT_VOILATED,"Database constraint violated"));
    }
}