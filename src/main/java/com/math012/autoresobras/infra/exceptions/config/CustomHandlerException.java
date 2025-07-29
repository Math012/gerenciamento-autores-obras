package com.math012.autoresobras.infra.exceptions.config;

import com.math012.autoresobras.infra.exceptions.api.ConflictException;
import com.math012.autoresobras.infra.exceptions.api.CpfException;
import com.math012.autoresobras.infra.exceptions.api.InvalidFieldsException;
import com.math012.autoresobras.infra.exceptions.api.ResourceNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class CustomHandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CpfException.class)
    public ResponseEntity<StructException> handlerCpfException(Exception e, WebRequest request) {
        StructException exception = new StructException(new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StructException> handlerResourceNotFoundException(Exception e, WebRequest request) {
        StructException exception = new StructException(new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(InvalidFieldsException.class)
    public ResponseEntity<StructException> handlerInvalidFieldsException(Exception e, WebRequest request) {
        StructException exception = new StructException(new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<StructException> handlerConflictException(Exception e, WebRequest request) {
        StructException exception = new StructException(new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<StructException> handleBadCredentialsException(BadCredentialsException e, WebRequest request) {
        StructException exception = new StructException(new Date(), "Credenciais inválidas", request.getDescription(false));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception);
    }
}